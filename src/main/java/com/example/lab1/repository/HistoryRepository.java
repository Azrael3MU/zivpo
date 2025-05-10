package com.example.lab1.repository;

import com.example.lab1.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    /**
     * 1) По сигнатуре (для GET /api/history/entity/signature/{signatureId})
     */
    List<History> findBySignatureId(UUID signatureId);

    /**
     * 2) Все версии, созданные после given‐date (для GET /api/history/since)
     */
    List<History> findByVersionCreatedAtAfter(LocalDateTime since);

    /**
     * 3) Универсальный «по сущности», если вы действительно хотите
     *    принимать entityName — делаем джпкьюри по signatureId,
     *    а entityName ныне игнорируем
     */
    @Query("SELECT h FROM History h WHERE h.signatureId = :entityId")
    List<History> findByEntityNameAndEntityId(
            @Param("entityId")   UUID   entityId
    );
}
