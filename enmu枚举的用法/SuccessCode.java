package com.ibm.enmu;

public class SuccessCode {

    public enum InnerSuCode{

        SYSTEM_SUEECESS_FIND("000001","查询成功"),
        YSTEM_SUEECESS_UPDATE("000002","更新成功"),
        SYSTEM_SUEECESS_DELETE("000003","删除成功"),
        SYSTEM_SUEECESS_INSERT("000004","插入成功")
        ;

        private String code;
        private String mes;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMes() {
            return mes;
        }

        public void setMes(String mes) {
            this.mes = mes;
        }

        private InnerSuCode(){

        };

        private InnerSuCode(String code,String mes){
            this.code = code;
            this.mes = mes;
        };

    }


}
