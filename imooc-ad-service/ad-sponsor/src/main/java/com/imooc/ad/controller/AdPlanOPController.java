package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdPlanService;
import com.imooc.ad.vo.AdPlanGetRequest;
import com.imooc.ad.vo.AdPlanRequest;
import com.imooc.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @description
 * @date 2019/4/22
 */
@RestController
@Slf4j
public class AdPlanOPController {
    private final IAdPlanService iAdPlanService;
    @Autowired
    public AdPlanOPController(IAdPlanService iAdPlanService) {
        this.iAdPlanService = iAdPlanService;
    }

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {

        log.info("ad-sponsor:createAdplan -> {}", JSON.toJSONString(request));
        return iAdPlanService.createAdPlan(request);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException{

        log.info("ad-sponsor:getAdPlanByIds->{}",JSON.toJSONString(request));
        return iAdPlanService.getAdPlanByIds(request);
    }

    @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor:updateAdPlan->{}",JSON.toJSONString(request));
        return iAdPlanService.updateAdPlan(request);
    }

    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody
                             AdPlanRequest  request) throws  AdException{
        log.info("ad-sponsor:deleteAdPlan->{}",JSON.toJSONString(request));
        iAdPlanService.deleteAdPlan(request);

    }
}
