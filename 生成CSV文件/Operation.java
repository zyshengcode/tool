Bean的关系 

	class	GarmentDataBean{

	private List<GarmentHeaderDataBean> headerBean;


	}
	
	
	class  GarmentHeaderDataBean{
		
	private List<GarmentColorBean> garmentColorBean;//颜色
	
	private List<GarmentSizeBean> garmentSizeBean;//尺码
	}


	class GarmentColorBean{
	@BeanFieldAnnotation(order = 1)
	private String color;//	颜色
	@BeanFieldAnnotation(order = 2)
	private String atwtb1;//颜色描述
	}
	
	
	
	class GarmentSizeBean{
		private String size1;//	尺码
	}








根据Bean生成多个CSV文件
	public SapResponseBean importGarmentDataForCSV(GarmentDataBean bean) throws Exception {
		SapResponseBean sapResponseBean = new SapResponseBean();
		List<GarmentHeaderDataBean> listGhb = new ArrayList<>();//母码
		List<GarmentColorBean> listGcb = new ArrayList<>();//颜色
		List<GarmentSizeBean> listGsb = new ArrayList<>();//尺码
		//得到GarmentHeaderDataBean里面的属性并且赋值
		ListIterator<GarmentHeaderDataBean> goodsRelativeHeaderBeanListIterator = bean.getHeaderBean().listIterator();
		while (goodsRelativeHeaderBeanListIterator.hasNext()) {
			GarmentHeaderDataBean Ghb = new GarmentHeaderDataBean();
			GarmentHeaderDataBean next = goodsRelativeHeaderBeanListIterator.next();
			Ghb.setSatnr(next.getSatnr());
			listGhb.add(Ghb);
			//得到颜色list
			 ListIterator<GarmentColorBean> garmentColorBeanListIterator = next.getGarmentColorBean().listIterator();
			while (garmentColorBeanListIterator.hasNext()){
				GarmentColorBean colorBean = new GarmentColorBean();
				GarmentColorBean nextColor = garmentColorBeanListIterator.next();
				BeanUtils.copyProperties(nextColor,colorBean);
				listGcb.add(colorBean);
			}
			//得到尺码list
			ListIterator<GarmentSizeBean> garmentSizeBeanListIterator = next.getGarmentSizeBean().listIterator();
			while (garmentSizeBeanListIterator.hasNext()){
				GarmentSizeBean garmentSizeBean = new GarmentSizeBean();
				GarmentSizeBean nextSize = garmentSizeBeanListIterator.next();
				BeanUtils.copyProperties(nextSize,garmentSizeBean);
				listGsb.add(garmentSizeBean);
			}
			/**
			 * 操作赋值
			 */

		}
		CsvUtil<GarmentHeaderDataBean> csvUtil = new CsvUtil<>();
		CsvUtil<GarmentColorBean> csvUtilForColor = new CsvUtil<>();
		CsvUtil<GarmentSizeBean> csvUtilForSize = new CsvUtil<>();
		try {
			//母码
			GarmentHeaderDataBean garmentH = new GarmentHeaderDataBean();
			String[] fileds = csvUtil.getFileds(garmentH);
			LinkedHashMap title = csvUtil.getTitle(garmentH);
			String fileName = csvUtil.getFileName(garmentH);
			csvUtil.createCSVFile(listGhb, fileds, title, new File("").getAbsolutePath()+File.separator.concat(fileName));
			//颜色
			GarmentColorBean colorB = new GarmentColorBean();
			String[] filedsColor = csvUtilForColor.getFileds(colorB);
			LinkedHashMap titleColor = csvUtilForColor.getTitle(colorB);
			String fileNameForColor = csvUtilForColor.getFileName(colorB);
			csvUtilForColor.createCSVFile(listGcb, filedsColor, titleColor, new File("").getAbsolutePath()+File.separator.concat(fileNameForColor));
			//尺寸
			GarmentSizeBean garmentS = new GarmentSizeBean();
			String[] filedsSize = csvUtilForSize.getFileds(garmentS);
			LinkedHashMap titleSize = csvUtilForSize.getTitle(garmentS);
			String fileNameForSize = csvUtilForSize.getFileName(garmentS);
			csvUtilForSize.createCSVFile(listGsb, filedsSize, titleSize, new File("").getAbsolutePath()+File.separator.concat(fileNameForSize));

			sapResponseBean.setIsFlag("SUCCESS");
			sapResponseBean.setMessage("商品编码关系已接收!");
		} catch (Exception e) {
			sapResponseBean.setIsFlag("FAIL");
			sapResponseBean.setMessage(e.getMessage());
		}
		return sapResponseBean;
	}