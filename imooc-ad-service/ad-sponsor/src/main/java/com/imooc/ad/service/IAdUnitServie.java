package com.imooc.ad.service;

import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.*;

public interface IAdUnitServie {

    AdUnitResponse createAdUnit(AdUnitRequest request)throws AdException;

    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws  AdException;
    AdUnitKeywordResponse createUnitKeyWord(AdUnitKeywordRequest request) throws AdException;
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictResquest resquest) throws AdException;

    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
