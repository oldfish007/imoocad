package com.imooc.ad.index.interest;

import com.imooc.ad.index.IndexWare;
import com.imooc.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author
 * @description推广单元限制索引
 * @date 2019/4/24
 */
@Slf4j
@Component
public class UnitItIndex implements IndexWare<String, Set<Long>> {
    // <itTag, adUnitId set>
    private static Map<String,Set<Long>> itUnitMap;
    // <unitId, itTag set>
    private static Map<Long,Set<String>> unitItMap;
    static{
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }
    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("before unitItIndex:add->{}",itUnitMap);
        Set<Long> unitSet = itUnitMap.get(key);
        if(CollectionUtils.isEmpty(unitSet)){
            //创建一个新的集合
            unitSet = new ConcurrentSkipListSet<>();
        }
        unitSet.addAll(value);
        //<unitId, itTag set>
        for (Long unitId:
                value) {
            Set<String> itTagSet = CommonUtils.getorCreate(
                    unitId, unitItMap,
                    ConcurrentSkipListSet::new
            );
            itTagSet.add(key);
        }
        log.info("after unitItIndex:add->{}",itUnitMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("UnitItIndex not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("before unitit update:delete->{}",itUnitMap);
        Set<Long> unitSet = CommonUtils.getorCreate(
                key, itUnitMap,
                ConcurrentSkipListSet::new
        );
        unitSet.removeAll(value);
        for (Long unitId:
             value) {
            Set<String> itTagSet = CommonUtils.getorCreate(
                    unitId, unitItMap,
                    ConcurrentSkipListSet::new
            );
            itTagSet.remove(key);
        }
        log.info("after unititIndex:delete->{}",itUnitMap);
    }

    public boolean match(Long unitId, List<String> itTags){
        if(unitItMap.containsKey(unitId)
            &&
           CollectionUtils.isNotEmpty(unitItMap.get(unitId))
        ){
            Set<String>  unitTagSet =  unitItMap.get(unitId);
            return   CollectionUtils.isSubCollection(itTags,unitTagSet);
        }
        return false;
    }
}
