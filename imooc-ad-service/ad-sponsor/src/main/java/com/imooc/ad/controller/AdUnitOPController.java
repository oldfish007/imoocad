package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.entity.unit_condition.AdUnitIt;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdUnitServie;
import com.imooc.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @description广告单元
 * @date 2019/4/22
 */
@RestController
@Slf4j
public class AdUnitOPController {

    private final IAdUnitServie adUnitServie;
    @Autowired
    public AdUnitOPController(IAdUnitServie adUnitServie) {
        this.adUnitServie = adUnitServie;
    }

    @PostMapping("/create/adUnit")
    public AdUnitResponse createAdUnit(@RequestBody AdUnitRequest request)throws AdException{
        log.info("ad-sponsor:createAdUnit->{}", JSON.toJSONString(request));
        return adUnitServie.createAdUnit(request);
    }

    @PostMapping("/create/adUnitKeywords")
    public AdUnitKeywordResponse createUnitKeywords(@RequestBody AdUnitKeywordRequest request) throws AdException{
         log.info("ad-sponsor:createUnitKeywords->{}",JSON.toJSONString(request));
         return adUnitServie.createUnitKeyWord(request);
    }
    @PostMapping("/create/adUnitIt")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request)throws AdException{
        log.info("ad-sponsor:createUnitIt->{}", JSON.toJSONString(request));
        return adUnitServie.createUnitIt(request);
    }
    @PostMapping("/create/adUnitdistrict")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictResquest resquest)throws  AdException{
        log.info("ad-sponsor:createUnitDistrict->{}", JSON.toJSONString(resquest));
        return adUnitServie.createUnitDistrict(resquest);
    }

}
