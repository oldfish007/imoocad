package com.imooc.ad.index.adplan;

import com.imooc.ad.index.IndexWare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @description实现索引的增删改
 * @date 2019/4/23
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexWare<Long,AdPlanObject> {
//定义一个索引存在的map 索引都会以一个MAP的方式存在
   //在更新这个map的过程中不会由于多线程的操作覆盖原来的MAP内容
    private static Map<Long,AdPlanObject> objectMap;
    static{
        objectMap = new ConcurrentHashMap<>();//线程安全的map
    }
    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }
    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("before add:{}",objectMap);
        objectMap.put(key,value);
        log.info("after add:{}",objectMap);
    }
    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("before update:{}",objectMap);
        AdPlanObject oldObject = objectMap.get(key);
        if(null == oldObject){
            objectMap.put(key,value);
        }else{
            oldObject.update(value);
        }
        log.info("after update:{}",objectMap);
    }
    @Override
    public void delete(Long key, AdPlanObject value) {
        log.info("before delete:{}",objectMap);
        objectMap.remove(key);
        log.info("after delete:{}",objectMap);
    }
}
