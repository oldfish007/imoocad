package com.imooc.ad.service;

import com.imooc.ad.Application;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUnitRepository;
import com.imooc.ad.dao.CreativeRepository;
import com.imooc.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.imooc.ad.dao.unit_condition.AdUnitItRepository;
import com.imooc.ad.dao.unit_condition.AdUnitKeyWordRepository;
import com.imooc.ad.dao.unit_condition.CreativeUnitRepository;
import com.imooc.ad.entity.unit_condition.CreativeUnit;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class},
                        WebEnvironment=SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanRepository adPlanRepository;
    @Autowired
    private AdUnitRepository adUnitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitRepository adUnitRepository;
    @Autowired
    private AdUnitKeyWordRepository adUnitKeyWordRepository;
    @Autowired
    private AdUnitItRepository adUnitItRepository;
    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;
}
