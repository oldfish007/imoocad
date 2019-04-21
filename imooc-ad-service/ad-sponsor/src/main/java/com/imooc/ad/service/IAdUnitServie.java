package com.imooc.ad.service;

import com.imooc.ad.vo.*;

public interface IAdUnitServie {

    AdUnitResponse createAdUnit(AdUnitRequest request)throws  Exception;

    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws  Exception;
    AdUnitKeywordResponse createUnitKeyWord(AdUnitKeywordRequest request) throws Exception;
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictResquest resquest) throws Exception;

    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws Exception;
}
