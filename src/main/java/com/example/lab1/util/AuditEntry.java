package com.example.lab1.util;
import com.example.lab1.entity.Audit;
import com.example.lab1.entity.Signature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class AuditEntry {
    private static final ObjectMapper M = new ObjectMapper();
    private static Audit base(UUID id,String by,String type,String json){
        return Audit.builder().signatureId(id).changedBy(by).changeType(type)
                .changedAt(LocalDateTime.now()).fieldsChanged(json).build();
    }
    public static Audit created(Signature s,String by){ return base(s.getId(),by,"CREATED","{}"); }
    public static Audit deleted(UUID id,String by){ return base(id,by,"DELETED","{}"); }
    public static Audit corrupted(UUID id){ return base(id,"SYSTEM","CORRUPTED","{}"); }
    public static Audit updated(UUID id,String by, Map<String,Object[]> diff){
        try{ return base(id,by,"UPDATED",M.writeValueAsString(diff)); }
        catch(Exception e){ return base(id,by,"UPDATED","{}"); }
    }
}
