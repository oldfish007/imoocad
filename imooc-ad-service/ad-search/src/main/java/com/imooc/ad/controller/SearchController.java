package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.annotation.IgnoreResponseAdvice;
import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class SearchController {

    private final RestTemplate restTemplate;
    @Autowired
    public SearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
@SuppressWarnings("all")
@IgnoreResponseAdvice  //我们不想使用统一的标记的时候 就可以使用到这个注解 可以去adCommon里面看看
@PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRebbon(@RequestBody AdPlanGetRequest request){
        log.info("ad-search:getAdPlansByRebbon->{}", JSON.toJSONString(request));
        return restTemplate.postForEntity(
          "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
                request,
                CommonResponse.class
        ).getBody();
    }
}
