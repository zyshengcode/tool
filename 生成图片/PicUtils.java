 FindPtProduct getdetail = findPtProductService.getPicturedetail(findPtProduct.getId());
            List<PtProductPicture> ptProductPictures = getdetail.getPtProductPictures();
            for (PtProductPicture ptList : ptProductPictures) {
				
				//所保存服务器路径
                String localPath = request.getSession().getServletContext().getRealPath("/") + SAVE_ROOT;         SAVE_ROOT =  static/common/photo/
                logger.info("localPath  >>>>>>>>>>>,,,,,,,,,,,,,,,,,,,,,,,>>>> " + localPath);
                String fileName = ptList.getPictrueLink();                                                数据库保存的链接 FTP://XXX.XX.XX.XX:21/AAA/BBB/6/39.jpg
				
				//ftp下载连接获取与截取
                String picName = fileName.substring(fileName.lastIndexOf("/") + 1);
                String substring = fileName.substring(fileName.indexOf("/") + 2);
                String ftpPath = substring.substring(substring.indexOf("/", 2));
				
				
                try {
					
					//登录ftp下载图片到服务器
                    ftpClientService.connect();
                    ftpClientService.downloadPic(ftpPath, localPath, picName);
					//保存服务器的路径
                    ptList.setPictrueLink(SAVE_ROOT + picName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
				
				循环显示多张图片
				 <c:forEach var="item" items="${findPtProduct.ptProductPictures}" varStatus="s">
				//JSP页面显示图片
			   <img src="<%=request.getContextPath()%>/${item.pictrueLink}" height="300" width="300" />
			   
			   
			   关于ftp的操作： 在Utils里面
			   