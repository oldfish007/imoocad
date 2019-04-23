package com.imooc.ad.index;

/**
 * 索引的建和值
 * @param <K>
 * @param <V>
 *     desciption:注意注意并不是表的所有字段都需要创建索引的 比如plan_name 就不需要暴露出去
 */
public interface IndexWare<K,V> {

    V get(K key);
    void add(K key,V value);
    void update(K key,V value);
    void delete(K key,V value);
}
