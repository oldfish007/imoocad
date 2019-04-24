package com.imooc.ad.index.creative;

import com.imooc.ad.index.IndexWare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @description广告创意
 * @date 2019/4/24
 */
@Slf4j
@Component
public class CreativeIndex implements IndexWare<Long,CreativeObject> {

    private static Map<Long,CreativeObject> objectMap;
    static{
        objectMap = new ConcurrentHashMap<>();
    }
    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
            log.info("before creativeIndex:add->{}",objectMap);
            objectMap.put(key,value);
            log.info("after creativeIndex:add->{}",objectMap);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("before CreativeIndex:update->{}",objectMap);
        CreativeObject creativeObject = objectMap.get(key);
        if(null==creativeObject){
            objectMap.put(key,value);
        }else{
            creativeObject.update(value);
        }
        log.info("after CreativeIndex:update->{}",objectMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("before Creativeindex:delete->{}",value);
        objectMap.remove(key);
        log.info("after CreativeIndex:delete->{}",value);
    }
}
