package com.imooc.ad.index.creativeunit;

import com.imooc.ad.index.IndexWare;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author
 * @description广告创意关联对象 索引
 * @date 2019/4/24
 * adId-unitId
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexWare<String,CreativeUnitObject> {
    // <adId-unitId, CreativeUnitObject>
    private static Map<String,CreativeUnitObject> objectMap;
    //<adId, unitId Set>
    private static Map<Long, Set<Long>> creativeUnitMap;
    //<unitId,adId set>
    private static Map<Long,Set<Long>> unitCreativeMap;
    static{
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }
    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }
    @Override
    public void add(String key, CreativeUnitObject value) {
       log.info("before creativeUnitIndex:add->{}",objectMap);
        objectMap.put(key,value);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if(CollectionUtils.isEmpty(unitSet)){
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(),unitSet);
        }
        unitSet.add(value.getUnitId());
        //unitCreativeMap unitId  adId Set<Long>
        Set<Long> creatvieSet = unitCreativeMap.get(value.getUnitId());
        if(CollectionUtils.isEmpty(creatvieSet)){
            creatvieSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(),creatvieSet);
        }
        creatvieSet.add(value.getAdId());
        log.info("after creativeIndex:add->{}",objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("before creativeindex:delete->{}",objectMap);
        objectMap.remove(key);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if(CollectionUtils.isNotEmpty(unitSet)){
            unitSet.remove(value.getUnitId());
        }
        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if(CollectionUtils.isNotEmpty(creativeSet)){
            creativeSet.remove(value.getAdId());
        }
        log.info("after creativeIndex:delete->{}",objectMap);
    }


}
