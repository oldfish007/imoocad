package com.imooc.ad.index.keyword;

import com.imooc.ad.index.IndexWare;
import com.imooc.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author
 * @description索引维护
 * @date 2019/4/23
 * 使用倒排索引
 */
@Slf4j
@Component
public class UnitKeywordIndex implements IndexWare<String, Set<Long>> {//保存unitId的set集合
//关键词  推广单元ID集合
    private static Map<String,Set<Long>> keywordUnitMap;
//推广单元ID  关键词集合
    private static Map<Long,Set<String>>  unitKeywordMap; //正序索引 一个unitid 也可以对应多个关键字限制
    static{
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }
    @Override
    public Set<Long> get(String key) {
        return null;
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitKeyWordIndex,before add:{}",unitKeywordMap);
        //我们传进来的这个key有可能在keywordUnitMap还没有
        //如果不存在就把
        Set<Long> unitIdSet  = CommonUtils.getorCreate(
                key,keywordUnitMap,
                ConcurrentSkipListSet::new
        );
        //这个方法能够实现当map中的key不存在的时候帮助这个key区new一个新的value出来
        unitIdSet.addAll(value);
        for (Long unitId:value) {
            Set<String> keywordSet = CommonUtils.getorCreate(
                    unitId,unitKeywordMap,
                    ConcurrentSkipListSet::new
            );
            keywordSet.add(key);
        }
        log.info("UnitKeywordIndex,after add:{}",unitKeywordMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("keyword index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {

    }

    //因为这些限制最终会实现对这个推广单元实现匹配 实现匹配方法也很正常
    //匹配过程是传递进来一个推广单元 传递进来许多关键词 去匹配某一个推广单元 是否包含着这些关键词
}
