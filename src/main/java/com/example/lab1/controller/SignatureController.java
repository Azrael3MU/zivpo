package com.example.lab1.controller;

import com.example.lab1.dto.SignatureDto;
import com.example.lab1.entity.Signature;
import com.example.lab1.repository.SignatureRepository;
import com.example.lab1.service.SignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/signatures")
@RequiredArgsConstructor
public class SignatureController {
    private final SignatureService service;
    private final SignatureRepository repo;

    private void checkAdmin(String role){
        if(role==null || !role.equalsIgnoreCase("ADMIN"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Admin role required");
    }

    @GetMapping public List<SignatureDto> all(){
        return repo.findByStatus(Signature.Status.ACTUAL).stream().map(this::toDto).toList();
    }
    @GetMapping("/diff")
    public List<SignatureDto> diff(@RequestParam("since") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime since){
        return repo.findByUpdatedAtAfter(since).stream().map(this::toDto).toList();
    }
    @PostMapping("/by-ids")
    public List<SignatureDto> byIds(@RequestBody List<UUID> ids){
        return repo.findByIdIn(ids).stream().map(this::toDto).toList();
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody SignatureDto dto,
                       @RequestHeader("X-User-Id") String user,
                       @RequestHeader("X-Role") String role){
        checkAdmin(role); return service.create(dto,user);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable UUID id,@RequestBody SignatureDto dto,
                       @RequestHeader("X-User-Id") String user,
                       @RequestHeader("X-Role") String role){
        checkAdmin(role); service.update(id,dto,user);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id,
                       @RequestHeader("X-User-Id") String user,
                       @RequestHeader("X-Role") String role){
        checkAdmin(role); service.softDelete(id,user);
    }

    private SignatureDto toDto(Signature s){
        return SignatureDto.builder()
            .id(s.getId()).threatName(s.getThreatName()).firstBytes(s.getFirstBytes())
            .remainderHash(s.getRemainderHash()).remainderLength(s.getRemainderLength())
            .fileType(s.getFileType()).offsetStart(s.getOffsetStart()).offsetEnd(s.getOffsetEnd()).build();
    }
}
