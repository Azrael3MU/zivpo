package com.example.lab1.controller;

import com.example.lab1.entity.History;
import com.example.lab1.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryRepository historyRepo;

    /** 1. Все записи истории */
    @GetMapping
    public List<History> getAll() {
        return historyRepo.findAll();
    }

    /** 2. История по сущности (например, "signature") и её ID */
    @GetMapping("/entity/{entityName}/{entityId}")
    public List<History> getByEntity(
            @PathVariable UUID entityId
    ) {
        return historyRepo.findByEntityNameAndEntityId(entityId);
    }

    /** 3. История изменений после указанного времени */
    @GetMapping("/since")
    public List<History> getSince(
            @RequestParam("since")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime since
    ) {
        return historyRepo.findByVersionCreatedAtAfter(since);
    }
}
