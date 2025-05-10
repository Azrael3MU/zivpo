package com.example.lab1.service;

import com.example.lab1.dto.SignatureDto;
import com.example.lab1.entity.Signature;
import com.example.lab1.repository.AuditRepository;
import com.example.lab1.repository.HistoryRepository;
import com.example.lab1.repository.SignatureRepository;
import com.example.lab1.util.AuditEntry;
import com.example.lab1.util.DiffUtil;
import com.example.lab1.util.HistoryEntry;
import com.example.lab1.util.SignatureCrypto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignatureService {
    private final SignatureRepository repo;
    private final HistoryRepository historyRepo;
    private final AuditRepository auditRepo;
    private final SignatureCrypto crypto;

    /**
     * Создаёт новую сигнатуру, подписывает её и логирует в audit.
     */
    @Transactional
    public UUID create(SignatureDto dto, String user) {
        // Формируем новую сущность с актуальным статусом и временем
        Signature s = toEntity(dto);
        s.setStatus(Signature.Status.ACTUAL);
        s.setUpdatedAt(LocalDateTime.now());

        // Подписание данных
        try {
            s.setDigitalSignature(crypto.sign(bytesForSign(s)));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Ошибка при формировании цифровой подписи", e);
        }

        // Сохраняем запись
        repo.save(s);

        // Записываем событие в аудит (передаём entity, а не id)
        auditRepo.save(AuditEntry.created(s, user));

        return s.getId();
    }

    /**
     * Обновляет существующую сигнатуру, сохраняет старую версию в history и логирует изменения.
     */
    @Transactional
    public void update(UUID id, SignatureDto dto, String user) {
        // Загружаем текущую версию и сохраняем её в history
        Signature current = repo.findById(id).orElseThrow();
        historyRepo.save(HistoryEntry.from(current));

        // Создаём временную сущность для сравнения
        Signature fresh = toEntity(dto);
        Map<String, Object[]> diff = DiffUtil.diff(current, fresh);

        // Обновляем поля
        current.updateFrom(fresh);
        current.setUpdatedAt(LocalDateTime.now());

        // Пересчитываем подпись
        try {
            current.setDigitalSignature(crypto.sign(bytesForSign(current)));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Ошибка при обновлении цифровой подписи", e);
        }

        // Сохраняем и логируем изменения
        repo.save(current);
        auditRepo.save(AuditEntry.updated(current.getId(), user, diff));
    }

    /**
     * Мягко удаляет запись, сохраняет старую версию в history и логирует удаление.
     */
    @Transactional
    public void softDelete(UUID id, String user) {
        Signature s = repo.findById(id).orElseThrow();
        historyRepo.save(HistoryEntry.from(s));

        s.setStatus(Signature.Status.DELETED);
        s.setUpdatedAt(LocalDateTime.now());

        // Обновляем подпись после изменения статуса
        try {
            s.setDigitalSignature(crypto.sign(bytesForSign(s)));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Ошибка при пересчёте подписи при удалении", e);
        }

        repo.save(s);
        auditRepo.save(AuditEntry.deleted(s.getId(), user));
    }

    // ----- Вспомогательные методы -----

    /**
     * Подготавливает данные для подписи.
     */
    private byte[] bytesForSign(Signature s) {
        String payload = String.join("|",
                s.getThreatName(),
                s.getFirstBytes(),
                s.getRemainderHash(),
                String.valueOf(s.getRemainderLength()),
                s.getFileType(),
                String.valueOf(s.getOffsetStart()),
                String.valueOf(s.getOffsetEnd())
        );
        return payload.getBytes();
    }

    /**
     * Переводит DTO в сущность с базовыми данными.
     */
    private Signature toEntity(SignatureDto d) {
        return Signature.builder()
                .threatName(d.getThreatName())
                .firstBytes(d.getFirstBytes())
                .remainderHash(d.getRemainderHash())
                .remainderLength(d.getRemainderLength())
                .fileType(d.getFileType())
                .offsetStart(d.getOffsetStart())
                .offsetEnd(d.getOffsetEnd())
                .build();
    }
}
