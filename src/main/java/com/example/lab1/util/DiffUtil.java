package com.example.lab1.util;
import java.lang.reflect.Field;
import java.util.*;

public class DiffUtil {
    public static Map<String,Object[]> diff(Object oldObj,Object newObj){
        Map<String,Object[]> m=new LinkedHashMap<>();
        try{
            for(Field f: oldObj.getClass().getDeclaredFields()){
                f.setAccessible(true);
                Object ov=f.get(oldObj), nv=f.get(newObj);
                if(!Objects.equals(ov,nv)) m.put(f.getName(),new Object[]{ov,nv});
            }
        }catch(Exception ignored){}
        return m;
    }
}
