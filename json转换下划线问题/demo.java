package com.carrefour.mapr.entity.base;

import com.alibaba.fastjson.annotation.JSONField;

public class Demo extends BaseEntity {
 
 //after  正确版本

    private String _Id ;

    @JSONField(name="_id")
    public String get_Id() {
        return _Id;
    }

    @JSONField(name="_id")
    public void set_Id(String _Id) {
        this._Id = _Id;
    }

	
	//before之前版本
	
	 private String _Id ;
	
	  
    public String get_Id() {
        return _Id;
    }

 
    public void set_Id(String _Id) {
        this._Id = _Id;
    }
   
}
