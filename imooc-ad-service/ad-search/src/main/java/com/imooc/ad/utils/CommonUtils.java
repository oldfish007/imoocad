package com.imooc.ad.utils;
import java.util.function.Supplier;
import java.util.Map;
/**
 * @author
 * @description工具类
 * @date 2019/4/23
 */
public class CommonUtils {
    public static <K,V> V getorCreate(K key,Map<K,V> map,
                                      Supplier<V> factory){
        //如果这个map的key不存在的情况下呢 我们使用factory返回一个新的对象是V
        return map.computeIfAbsent(key,k->factory.get());
    }
}
