package com.imooc.ad.service.impl;

import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUnitRepository;
import com.imooc.ad.dao.CreativeRepository;
import com.imooc.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.imooc.ad.dao.unit_condition.AdUnitItRepository;
import com.imooc.ad.dao.unit_condition.AdUnitKeyWordRepository;
import com.imooc.ad.dao.unit_condition.CreativeUnitRepository;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUnit;
import com.imooc.ad.entity.unit_condition.AdUnitDistrict;
import com.imooc.ad.entity.unit_condition.AdUnitIt;
import com.imooc.ad.entity.unit_condition.AdUnitKeyWords;
import com.imooc.ad.entity.unit_condition.CreativeUnit;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdUnitServie;
import com.imooc.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


public class IAdUnitServieImpl implements IAdUnitServie {

    private  final AdPlanRepository adPlanRepository;
    private final AdUnitRepository adUnitRepository;
    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;
    @Autowired
    private AdUnitItRepository adUnitItRepository;
    @Autowired
    private AdUnitKeyWordRepository adUnitKeyWordRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;


    @Autowired
    public IAdUnitServieImpl(AdPlanRepository adPlanRepository, AdUnitRepository adUnitRepository) {
        this.adPlanRepository = adPlanRepository;
        this.adUnitRepository = adUnitRepository;
    }

    @Override
    public AdUnitResponse createAdUnit(AdUnitRequest request) throws Exception {
        if(!request.createValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdPlan> oldPlan = adPlanRepository.findById(request.getPlanId());
        if(!oldPlan.isPresent()){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdUnit oldUnit = adUnitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if(oldPlan!=null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit newAdUnit = adUnitRepository.save(new AdUnit(
                request.getPlanId(),
                request.getUnitName(),
                request.getPositionType(),
                request.getBudget()));
        return new AdUnitResponse(newAdUnit.getId(),newAdUnit.getUnitName());
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws Exception {
        List<Long> unitIds = request.getUnitIts().stream().map(
                AdUnitItRequest.UnitIt::getUnitId
        ).collect(Collectors.toList());
        if(isRelatedCreativeExist(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = new ArrayList<>();
        List<AdUnitIt> adUnitIts = new ArrayList<>();
        //判断集合
        if(!CollectionUtils.isEmpty(request.getUnitIts())){
            //收集
            request.getUnitIts().forEach(i->adUnitIts.add(
                    new AdUnitIt(i.getUnitId(),i.getItTag())
            ));
            //保存
            ids = adUnitItRepository.saveAll(adUnitIts).stream().map(
                    AdUnitIt::getId
            ).collect(Collectors.toList());
        }
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitKeywordResponse createUnitKeyWord(AdUnitKeywordRequest request) throws Exception {
        //首先收集传上来的request集合
        List<Long> unitIds = request.getUnitKeyWords().stream()
                .map(AdUnitKeywordRequest.UnitKeyWord::getUnitId)
                .collect(Collectors.toList());
        //然后判断集合是否为 null 抛异常
        if(isRelatedCreativeExist(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        //创建一个Long集合
        List<Long> ids = Collections.emptyList();
        //创建一个adUnitKeyWords集合
        List<AdUnitKeyWords> adUnitKeyWords = new ArrayList<>();
        //根据request收集的集合循环创建对象
        if(!CollectionUtils.isEmpty(request.getUnitKeyWords())){

            request.getUnitKeyWords().forEach(i->adUnitKeyWords.add(
                    new AdUnitKeyWords(i.getUnitId(),i.getKeywords())
            ));
            adUnitKeyWordRepository.saveAll(adUnitKeyWords).stream()
                    .map(AdUnitKeyWords::getId)
                    .collect(Collectors.toList());
        }
        //保存对象返回LONG集合
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictResquest request) throws Exception {
        List<Long> unitIds = request.getUnitDistirctList().stream()
                .map(AdUnitDistrictResquest.UnitDistirct::getUnitId)
                .collect(Collectors.toList());
        if(isRelatedCreativeExist(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> ids = new ArrayList<>();
        List<AdUnitDistrict> adUnitDistricts = new ArrayList<>();
        //判断 获取
        if(!CollectionUtils.isEmpty(request.getUnitDistirctList())){
            //收集
            request.getUnitDistirctList().forEach(d->adUnitDistricts.add(
                    new AdUnitDistrict(d.getUnitId(),d.getProvince(),d.getCity())
            ));
            //保存
            ids =  adUnitDistrictRepository.saveAll(adUnitDistricts).stream()
                    .map(
                            AdUnitDistrict::getId
                    ).collect(Collectors.toList());

        }
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws Exception {
        //收集
        List<Long> unitIds = request.getUnitItems().stream().map(
                CreativeUnitRequest.CreativeUnitItem::getUnitId
        ).collect(Collectors.toList());
        List<Long> creativeIds = request.getUnitItems().stream().map(
                CreativeUnitRequest.CreativeUnitItem::getCreativeId
        ).collect(Collectors.toList());
        //判断
        if(!(isRelatedCreativeExist(unitIds)&&isRelatedCreativeUnitExist(creativeIds))){
            throw  new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<CreativeUnit> creativeUnits = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        //批量创建
        if(!CollectionUtils.isEmpty(request.getUnitItems())){
            //收集创建
            request.getUnitItems().forEach(i->creativeUnits.add(
                    new CreativeUnit(i.getCreativeId(),i.getUnitId())
            ));
            //保存 收集ID
            ids =  creativeUnitRepository.saveAll(creativeUnits).stream().map(
                    CreativeUnit::getId
            ).collect(Collectors.toList());
        }
        return new CreativeUnitResponse(ids);
    }

    //用于判断三方提交上来的推广单元集合是否为null
    private boolean isRelatedCreativeExist(List<Long> unitIds){
        if(CollectionUtils.isEmpty(unitIds)){
            return false;
        }
        return adUnitRepository.findAllById(unitIds).size()==
                new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeUnitExist(List<Long> creativeIds){
        if(CollectionUtils.isEmpty(creativeIds)){
            return false;
        }
        return creativeRepository.findAllById(creativeIds).size()==
                new HashSet<>(creativeIds).size();
    }
}
