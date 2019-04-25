package com.imooc.ad.index;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author
 * @description
 * @date 2019/4/25
 */
@Component
@DependsOn("dataTable")//他会依赖于这个dataTable bean
public class IndexFileLoader {

    /*
   也就是IndexFileLoader被spring加载之后呢
   init方法就被执行
   先是第二层级 再是第三层级 最后第四层级
   2.adPlan adCreative adUnit
   3.adCreativeUnit
   4.unitIt unitKeyword unitdistrict
   @PostConstruct说明
   被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，
   类似于Serclet的inti()方法。
被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
     */
    //加载全量索引
    @PostConstruct
    public void init(){

    }






}
