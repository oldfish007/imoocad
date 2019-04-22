package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdUnitServie;
import com.imooc.ad.service.ICreativeService;
import com.imooc.ad.vo.CreativeRequest;
import com.imooc.ad.vo.CreativeResponse;
import com.imooc.ad.vo.CreativeUnitRequest;
import com.imooc.ad.vo.CreativeUnitResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @description广告创意
 * @date 2019/4/22
 */
@Slf4j
@RestController
public class AdCreativeOPController {
    private final IAdUnitServie adUnitServie;

    private final ICreativeService creativeService;
    @Autowired
    public AdCreativeOPController(IAdUnitServie adUnitServie, ICreativeService creativeService) {
        this.adUnitServie = adUnitServie;
        this.creativeService = creativeService;
    }
    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException {
        log.info("ad-sponsor:createCreative->{}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
    @PostMapping("/creat/creativeunit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException{
        log.info("ad-sponsor:createCreativeUnit->{}",JSON.toJSONString(request));
        return adUnitServie.createCreativeUnit(request);
    }
}
