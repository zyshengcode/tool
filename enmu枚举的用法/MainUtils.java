package com.ibm.enmu;

import java.util.Map;
public class MainUtils {


    public static void main(String[] args) {
        Map<String, Object> errorCode = getErrorCode();
        System.out.println(errorCode);

        errorCode =  getSuccCode();
        System.out.println(errorCode);

    }




    public static Map<String,Object> getErrorCode(){
        BeanProperties beanProperties = null;
        if (null==beanProperties){
            return CommonUtils.getErrorCode(ErrorCode.InnerErrCode.FAILED_FIND.getCode(),"查询失*……*败了");
        }
        return null;
    }


    public static Map<String,Object> getSuccCode(){
        BeanProperties beanProperties = new BeanProperties();
        beanProperties.setAge("12");
        beanProperties.setSex("female");
        if (null==beanProperties){
            return CommonUtils.getErrorCode(ErrorCode.InnerErrCode.FAILED_FIND.getCode(),"查询失*……*败了");
        }
        return CommonUtils.getSuccessCode(beanProperties,"查询成功");
    }

}
