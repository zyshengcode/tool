	     	

			//循环职员bean里面的getClerkCode（职员code）来防止对职员重复操作（如多个人对职员加工资）

			for(Clerk bean:clerkArray){
		     		 //关键字去掉空格
			     	 String hrRang = StringUtils.trimToEmpty(bean.getHrRang());
			     	 String clerkCode = StringUtils.trimToEmpty(bean.getClerkCode());
			     	 bean.setHrRang(hrRang);
			     	 bean.setClerkCode(clerkCode);
			     	 //关键字去掉空格
			     		 
		     		 //增加redis锁
 		     		 String key = bean.getClerkCode();
 		     		 boolean lock = redisUtils.finalLock(key);
 		     		 logger.info("lock加锁:"+lock);
 		     		 if(lock){//成功加锁 			     		  			     		 
 			     		 try {
 			     			 bean.setStoreSapCode(bean.getHrRang());
 	 				     	 bean.setStoreCode(bean.getHrRang());
 	 			     		 bean.setPassword(MD5Util.defaultMd5());
 			     			 Clerk clerk = clerkService.selectClerk(bean.getClerkCode());
 			     			 if(null != clerk){
 				     			 if(StringUtils.isNotBlank(clerk.getClerkCode())){
 					    			 bean.setIsNewRecord(false);//更新数据
 					    			 bean.setId(clerk.getId());
 					    			 bean.setUpdateDate(CommonUtil.getNowDateTime());
 					    			 //如果是零售公司和批发公司更新门店编码
 					    			 Store findStoreCode = storeService.findStoreCode(bean.getHrRang());
 					    			 if(ConstantsUtil.STORE_TYPE_A.equals(findStoreCode.getStoreMpType()) || ConstantsUtil.STORE_TYPE_B.equals(findStoreCode.getStoreMpType())){
 					    				 bean.setStoreCode(bean.getHrRang());
 					    			 } 
 					    		 }
 			     			 }
 			     			 //将数据标记为未使用
							 bean.setUseFlag("0");
 			 				 clerkService.save(bean);        //更新操作  看后面的保存代码
 			 			 } catch (Exception e) {
 			 				e.printStackTrace();
 			 				logger.error(e.getMessage());
 			 			 }finally{
  	 		     		    //释放锁
  	 		     			boolean unlock = redisUtils.unlock(key);
  	 		     			logger.info("释放锁unlock:"+unlock);
  	 		     		 }
 		     			 
 		     		 }else{
 		     			 
 		     		 }		     		 
		         }
				 
				 
				 
				 
		//当NewRecord为false的时候进行更新	 
	  public void save(T entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsertByConditional(true);
            this.mapper.insert(entity);
        } else {
            entity.preUpdateByConditional(true);
            this.mapper.update(entity);
        }

    }