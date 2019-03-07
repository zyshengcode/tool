package com.ibm.enmu;

public class ErrorCode {

    public enum InnerErrCode{

        FAILED_FIND("20001","查询失败"),
        FAILED_DELETE("20002","删除失败"),
        FAILED_UPDATE("20003","更新失败"),
        FAILED_INSERT("20004","新增失败");

        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        private InnerErrCode(){ }

        private InnerErrCode(String code,String msg){
            this.code = code;
            this.msg = msg;
        }
    }

}
