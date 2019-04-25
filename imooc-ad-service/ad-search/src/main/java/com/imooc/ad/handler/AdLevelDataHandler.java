package com.imooc.ad.handler;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.dump.table.*;
import com.imooc.ad.index.DataTable;
import com.imooc.ad.index.IndexWare;
import com.imooc.ad.index.adplan.AdPlanIndex;
import com.imooc.ad.index.adplan.AdPlanObject;
import com.imooc.ad.index.adunit.AdUnitIndex;
import com.imooc.ad.index.adunit.AdUnitObject;
import com.imooc.ad.index.creative.CreativeIndex;
import com.imooc.ad.index.creative.CreativeObject;
import com.imooc.ad.index.creativeunit.CreativeUnitIndex;
import com.imooc.ad.index.creativeunit.CreativeUnitObject;
import com.imooc.ad.index.district.UnitDistrictIndex;
import com.imooc.ad.index.district.UnitDistrictObject;
import com.imooc.ad.index.interest.UnitItIndex;
import com.imooc.ad.index.interest.UnitItObject;
import com.imooc.ad.index.keyword.UnitKeywordIndex;
import com.imooc.ad.mysql.constant.OpType;
import com.imooc.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 * @description
 * @date 2019/4/25
 * 需要考虑
 * 最高层级是用户层级 第二层是推广计划 第三层是推广单元以及推广单元的限制等等
 * 1.索引之间存在层级划分（之间拥有依赖关系的划分） 所以说我们在去定义索引执行的时候给他们设定一些层级
 * 当我们看到方法的时候就能知道其它层级存在依赖层级关系的限制 还是说他自己可以单独成为一个
 * 层级
 * 我们不需要用户账户
 * 2.加载全量索引其实是增量索引添加的一种特殊实现（就是在广告系统运行过程中广告主对他进行增删操作）
 * 我们需要对这个操作重新构造索引 这样有什么好处 这样在实现代码的时候就能够统一我们代码我们写一次代码
 * 就可以实现全量和增量共同的更新 全量索引和增量索引共同的构造过程
 * 用户账户不参与到任何索引当中 所以他们不依赖其它的任何索引 我们把它叫做第二层级
 * 层级的划分也是一种非常好的管理方式 便于将来对同一个层级的数据进行操作 同时也方便阅读代码
 * 看到层级划分就可以知道 他们之间是否有依赖关系 是否会依赖其它表的索引数据
 *
 * 第二层级 不予其它的索引服务有依赖关系
 * adCreative adPlan
 */
@Slf4j
public class AdLevelDataHandler {


    //给二层添加索引
    public static void handleLevel2(AdPlanTable planTable,OpType opType){
        //创建索引对象
        AdPlanObject adPlanObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        //添加adPlan的正向索引到缓存
        handlerBinlogEvent(DataTable.of(AdPlanIndex.class),
                            adPlanObject.getPlanId(),
                            adPlanObject,
                            opType );
    }

    /**
     *   private Long adId;
     *     private String name;
     *     private Integer type;
     *     private Integer materialType;
     *     private Integer height;
     *     private Integer width;
     *     private Integer auditStatus;
     *     private Integer adUrl;//物料地址
     * @param adCreativeTable
     * @param opType
     */
    public static void handleLevel2(AdCreativeTable adCreativeTable,OpType opType){
        CreativeObject creativeObject = new CreativeObject(
                adCreativeTable.getAdId(),
                adCreativeTable.getName(),
                adCreativeTable.getType(),
                adCreativeTable.getMeterialType(),
                adCreativeTable.getHeight(),
                adCreativeTable.getWidth(),
                adCreativeTable.getAuditStatus(),
                adCreativeTable.getAdUrl()
        );
        //添加creative的正向索引
        handlerBinlogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                opType
                );
    }
    //第三层有外键约束
    public static void handleLevel3(AdUnitTable unitTable,OpType opType){
       //从applicationContext中拿到缓存的索引bean 从索引bean里面在拿到AdPlanObject索引对象
        AdPlanObject adPlanObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        //如果没有就直接输出错误日志return
        if(null == adPlanObject){
            log.error("handleLevel3 found AdPlanObject error: {}", unitTable.getUnitId());
        }
        //如果存在根据table对象构造索引对象

        AdUnitObject unitObject = new AdUnitObject(
                unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                adPlanObject
        );
        //接下来就是构造索引
        handlerBinlogEvent(
                DataTable.of(AdUnitIndex.class),
                unitObject.getUnitId(),
                unitObject,
                opType);
    }
    public static void handlerLevel3(AdCreativeUnitTable adCreativeUnitTable,OpType opType){
        //操作如果是update直接return
        if(opType==OpType.UPDATE){
            log.error("CreativeUnitIndex not support update");
        }
        //分别取出索引对象
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(adCreativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(adCreativeUnitTable.getAdId());
        if(null==unitObject && null==creativeObject){
            log.error("AdCreativeUnitTable index error: {}",JSON.toJSONString(adCreativeUnitTable));
            return ;
        }
        //构造索引对象
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
                adCreativeUnitTable.getUnitId(),
                adCreativeUnitTable.getAdId()
        );
        //接下来就是把索引对象添加或者update
        handlerBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringContact(creativeUnitObject.getAdId().toString(),
                                    creativeUnitObject.getUnitId().toString()),
                creativeUnitObject,
                opType);
    }
    /**
     * 第四层级索引 与三层级推广单元有依赖关系
     */
            public static void  handlerLevel4(AdDistrictTable adDistrictTable,OpType opType){
                //首先判断是否是update
                if(opType==OpType.UPDATE){
                    log.error("UnitDistrictIndex not support update");
                    return ;
                }
                //取出adUnitObject索引对象
                AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(adDistrictTable.getUnitId());
                //如果没有则返回
                if(null == unitObject){
                    log.error("AdUnitDistrictTable index error:{}",adDistrictTable.getUnitId());
                    return ;
                }
                //构造key
                String key = CommonUtils.stringContact(adDistrictTable.getProvince(),adDistrictTable.getCity());
                //构造value
                Set<Long> value = new HashSet<>(
                        Collections.singleton(adDistrictTable.getUnitId())
                );



                //接下来就是执行opType的操作
                handlerBinlogEvent(
                        DataTable.of(UnitDistrictIndex.class),
                        key,value,
                        opType
                );

            }

     public static void handlerLevel4(AdUnitItTable adUnitItTable,OpType opType){
                if(opType==OpType.UPDATE){
                    log.error("AdunitItIndex not support update");
                    return ;
                }
                //获取adUnitObject对象
                  AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(adUnitItTable.getUnitId());
         // 判断是否为null 如果为null说明并没有推广单元 return
                  if(null == unitObject){
                     log.error("AdUnitItTable index error:{}",adUnitItTable.getUnitId());
                     return;
                  }
                //构造ke value
                Set<Long> value = new HashSet<>(
                         Collections.singleton(adUnitItTable.getUnitId())
                 );
                //执行操作
                handlerBinlogEvent(
                        DataTable.of(UnitItIndex.class),
                        // <itTag, adUnitId set>
                        adUnitItTable.getUnitTag(),
                        value,
                        opType
                );
     }

     public static void handlerLevel4(AdUnitKeywordTable adUnitKeywordTable,OpType opType){
                if(opType==OpType.UPDATE){
                    log.error("AdunitKeywordIndex not supprot update");
                }
                AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(adUnitKeywordTable.getUnitId());
                if(null==unitObject){
                    log.error("AdUnitKeywordTable index error: {}",adUnitKeywordTable.getUnitId());
                    return;
                }
                //构造value
                Set<Long> value = new HashSet<>(
                    Collections.singleton(adUnitKeywordTable.getUnitId())
                );
                //执行操作
                handlerBinlogEvent(
                        DataTable.of(UnitKeywordIndex.class),
                        adUnitKeywordTable.getKeyword(),
                        value,
                        opType
                );
     }

    /**adUnit creative adPlan
     *
     * @param index
     * @param key
     * @param value
     * @param type
     * @param <K>
     * @param <V>
     */
    private static <K,V> void handlerBinlogEvent(IndexWare<K,V> index,
                                                 K key,
                                                 V value,
                                                 OpType type){
        switch (type){
            case ADD:
              index.add(key,value);
              break;
            case UPDATE:
              index.update(key,value);
              break;
            case DELETE:
              index.delete(key,value);
              break;
            default:
              break;
        }
    }
}
