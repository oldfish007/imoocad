package com.imooc.ad.utils;


import com.imooc.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

public class CommonUtils {

    public static String[] parsePatterns={"yyyy-MM-dd","yyyy/MM/dd","yyy.MM.dd"};
    public static String md5(String value){
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringDate(String dateString)
            throws AdException {

        //只要三方传进来的传满足数组其中一个就可以
        try{
           return  DateUtils.parseDateStrictly(dateString, parsePatterns);
        }catch(Exception e){
            throw new AdException(e.getMessage());
        }

    }

}
