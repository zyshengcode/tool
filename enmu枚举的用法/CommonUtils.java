package com.ibm.enmu;

import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

    public static Map<String,Object> getSuccessCode(BeanProperties obj,String msg){

        Map<String,Object> map = new HashMap<>();
        map.put("returncode",SuccessCode.InnerSuCode.SYSTEM_SUEECESS_FIND.getCode());
        map.put("obj",obj);
        map.put("msg",msg);
        return map;
    }


    public static Map<String,Object> getErrorCode(String code,String msg){

        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }
}
