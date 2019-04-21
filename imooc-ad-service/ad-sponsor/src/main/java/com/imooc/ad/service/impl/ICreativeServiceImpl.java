package com.imooc.ad.service.impl;

import com.imooc.ad.dao.CreativeRepository;
import com.imooc.ad.entity.Creative;
import com.imooc.ad.service.ICreativeService;
import com.imooc.ad.vo.CreativeRequest;
import com.imooc.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class ICreativeServiceImpl implements ICreativeService {

    @Autowired
    private CreativeRepository creativeRepository;
    @Override
    public CreativeResponse createCreative(CreativeRequest request) {
        //一系列的校验判断 没有写 比如正则匹配非空判断
        Creative creative = request.convert2Entity();
        creative = creativeRepository.save(creative);
        return new CreativeResponse(creative.getId(),creative.getName());
    }
}
