package com.imooc.ad.index.district;

import com.imooc.ad.index.IndexWare;
import com.imooc.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author
 * @description广告单元
 * @date 2019/4/24
 *  <String, Set<Long>>
 *      province-city
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexWare<String , Set<Long>> {
//province-city String     unitId Set<Long>
    private static Map<String,Set<Long>> districtUnitMap;
// unitId Long   province-city Set<String>
    private static Map<Long,Set<String>> unitDistrictMap;
    static{
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitDistrictIndex,before add:{}",unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);
        for (Long unitId:
                value) {
            Set<String> districts = CommonUtils.getorCreate(
                    unitId, unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            districts.add(key);
        }
        log.info("UnitDistrictIndex,after add:{}",unitDistrictMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("district index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitDistrictIndex:delete->{}",districtUnitMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, districtUnitMap,
                ConcurrentSkipListSet::new
        );
        //只删除部分
        unitIds.removeAll(value);
        for (Long unitId:
                value ) {
            //province-city
            Set<String> districts = CommonUtils.getorCreate(
                    unitId, unitDistrictMap,
                    ConcurrentSkipListSet::new);
            districts.remove(key);
        }
        log.info("DistrictIndex:update->{}",districtUnitMap);
    }
}
