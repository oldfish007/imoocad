package com.imooc.ad.index.adunit;

import com.imooc.ad.index.IndexWare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @description推广单元索引维护
 * @date 2019/4/23
 */
@Slf4j
@Component
public class AdUnitIndex implements IndexWare<Long,AdUnitObject> {

    //需要一个线程安全的map
    private static Map<Long,AdUnitObject> objectMap;
    static{
        objectMap = new ConcurrentHashMap<>();
    }
    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }
    @Override
    public void add(Long key, AdUnitObject value) {
        log.info("befor add:{}",objectMap);
        objectMap.put(key,value);
        log.info("after add:{}",objectMap);
    }

    @Override
    public void update(Long key, AdUnitObject value) {
        log.info("before update:{}",objectMap);
        AdUnitObject oldObject = objectMap.get(key);
        if(null == oldObject){
            objectMap.put(key,value);
        }else{
            oldObject.update(value);
        }
        log.info("after update:{}",objectMap);
    }

    @Override
    public void delete(Long key, AdUnitObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete:{}",objectMap);
    }
}
