package com.imooc.ad.index;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.dump.DConstant;
import com.imooc.ad.dump.table.*;
import com.imooc.ad.handler.AdLevelDataHandler;
import com.imooc.ad.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
   2.adPlan adCreative
   3.adCreativeUnit adUnit
   4.unitIt unitKeyword unitdistrict
   @PostConstruct说明
   被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，
   类似于Serclet的inti()方法。
被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
     */
    //加载全量索引的加载
    //检索系统启动的时候 就要完成加载
    //IndexFileLoader加载spring容器之后呢 init()方法就应该执行
    @PostConstruct
    public void init(){
//加载顺序是不能改变的 2 3 4
//从数据文件里面加载数据 推广计划实现了全量数据的加载
        List<String> adPlanString = LoadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR,
                                                                DConstant.AD_PLAN));
        adPlanString.forEach(p-> AdLevelDataHandler.handleLevel2(
                //从数据库读取的时候就已经转变成了adPlanTable
                JSON.parseObject(p, AdPlanTable.class),
                OpType.ADD
        ));
        List<String> adCreativeString = LoadDumpData(String.format("%s%s",DConstant.DATA_ROOT_DIR,
                                                            DConstant.AD_CREATIVE));
        adCreativeString.forEach(c->AdLevelDataHandler.handleLevel2(
                JSON.parseObject(c, AdCreativeTable.class),
                OpType.ADD
        ));
        List<String> adUnitString = LoadDumpData(String.format("%s%s",DConstant.DATA_ROOT_DIR,
                                                    DConstant.AD_UNIT));
        adUnitString.forEach(au->AdLevelDataHandler.handleLevel3(
                JSON.parseObject(au, AdUnitTable.class),
                OpType.ADD
        ));
        List<String> adCreativeUnit = LoadDumpData(String.format("%s%s",DConstant.DATA_ROOT_DIR,
                                                        DConstant.AD_CREATIVE_UNIT));
        adCreativeString.forEach(ac->AdLevelDataHandler.handlerLevel3(
                JSON.parseObject(ac, AdCreativeUnitTable.class),
                OpType.ADD
        ));
        List<String> adUnitDistrictStrings = LoadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(d -> AdLevelDataHandler.handlerLevel4(
                JSON.parseObject(d, AdDistrictTable.class),
                OpType.ADD
        ));

        List<String> adUnitItStrings = LoadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_IT)
        );
        adUnitItStrings.forEach(i -> AdLevelDataHandler.handlerLevel4(
                JSON.parseObject(i, AdUnitItTable.class),
                OpType.ADD
        ));

        List<String> adUnitKeywordStrings = LoadDumpData(
                String.format("%s%s",
                        DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_KEYWORD)
        );
        adUnitKeywordStrings.forEach(k -> AdLevelDataHandler.handlerLevel4(
                JSON.parseObject(k, AdUnitKeywordTable.class),
                OpType.ADD
        ));
    }

    public List<String> LoadDumpData(String fileName){
        try(BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName)
        )){
            return br.lines().collect(Collectors.toList());
        }catch(IOException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
