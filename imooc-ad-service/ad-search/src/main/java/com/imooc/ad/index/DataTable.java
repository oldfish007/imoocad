package com.imooc.ad.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @description
 * @date 2019/4/24
 * ApplicationContext 应用程序上下文 得到当前系统中spring已经帮我们初始化的各个组件
 * PriorityOrdered这是一个优先级排序 spring初始化的时候会初始化在应用中定义的所有bean
 * 各个bean @Component @Service @Controller。。这些Bean 初始化所有bean
 * 初始化javaBean你可以初始化javabean的顺序
 * 如果你使用到了PriorityOrdered spring会把事先排好序这些个bean给你先初始化再去初始化其他的
 * dataTabler 是缓存的一个javabean 需要在缓存出现前就已经有了 那它去保存所有的index 去记录
 * 所有的缓存服务 当给的order值越小 越先被初始化
 * 这种做法也是对于一类服务比较多的情况下 注入比较麻烦 一大推 就可以采用这种方式把服务都缓存起来只要注入
 * 这个dataTable 使用一个目录的形式把它保存下来  如果想去使用的时候
 * 我们直接从这个dataTable里面去拿到相关多的服务对象
 */
@Component
@Slf4j
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;
    //T - 由此 Class 对象建模的类的类型。例如， String.class 的类型是 Class<String>。如果将被建模的类未知，则使用 Class<?>。
    public static final Map<Class,Object> dataTableMap = new ConcurrentHashMap<>();
    @Override
    //这里的applicationContext 是有applicationContextWare注入的
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext  = applicationContext;
    }
    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }
    //从dataTableMap里面获取根据clazz 获取(T)bean
    public static <T> T of(Class<T> clazz){
        T instance = (T) dataTableMap.get(clazz);
        if(null!=instance){
            return instance;
        }
        dataTableMap.put(clazz,bean(clazz));
        return (T)dataTableMap.get(clazz);
    }
    @SuppressWarnings("all")
    private static <T> T bean(String beanName){
        return (T)applicationContext.getBean(beanName);
    }
    @SuppressWarnings("all")
    private static <T> T bean(Class clazz){
        return (T)applicationContext.getBean(clazz);
    }

}
