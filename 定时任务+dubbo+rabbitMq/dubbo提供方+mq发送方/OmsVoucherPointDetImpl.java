package com.ibm.oms.rs;

import com.alibaba.fastjson.JSON;
import com.ibm.core.service.impl.OmsSendService;
import com.ibm.modules.oms.entity.OmsVoucherPointDet;
import com.ibm.modules.oms.service.IOmsVoucherPointDet;
import com.ibm.modules.oms.entity.info.OmsVoucher;
import com.ibm.modules.oms.service.OmsVoucherPointDetService;
import com.ibm.modules.store.client.dto.storeinfo.StoreInfoDto;
import com.ibm.modules.store.client.dto.storeinfo.StoreType;
import com.ibm.modules.store.client.service.storeinfo.IStoreService;
import com.ibm.oms.enums.ErrorCode;
import com.ibm.oms.enums.PlateType;
import com.ibm.oms.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OmsVoucherPointDetImpl implements IOmsVoucherPointDet {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OmsVoucherPointDetService omsVoucherPointDetService;

    @Autowired
    IStoreService storeService;

    @Autowired
    OmsSendService omsSendService;

    private static final String SALE_TYPE = "10";

    public static final String SEND_STORE_VOUCHER = "sap.voucher.receipts";


    @Override
    public void sTicketVoucher() {
        OmsVoucherPointDet omsVoucherPointDet = new OmsVoucherPointDet();
        omsVoucherPointDet.setCreateDate(new Date());
        omsVoucherPointDet.setSaleType(SALE_TYPE);
		
		//查找数据并且入新库
        List<OmsVoucherPointDet> voList = omsVoucherPointDetService.findListForVoucher(omsVoucherPointDet);
        for (OmsVoucherPointDet bean : voList) {
            OmsVoucherPointDet omsInfo = getStoreInfoDto(bean);
            if (null == omsInfo)
                continue;
            bean.setBldat(getDate(-1));//设置单据日志
            bean.setBudat(getDate(0));//设置过账日期
            try {
                omsVoucherPointDetService.save(bean);
            } catch (Exception e) {
                logger.info("数据入库异常，异常信息{}", e.getMessage());
            }
        }

        //新数据库查询发送数据，并且修改
        List<OmsVoucherPointDet> listForMQ = omsVoucherPointDetService.findListForMQ("0");
        List<OmsVoucher> targetList = new ArrayList<>();
        if (null!=listForMQ&&!"".equals(listForMQ)) {
            for (OmsVoucherPointDet bean : listForMQ){
                OmsVoucher targetBean = new OmsVoucher();
                BeanUtils.copyProperties(bean,targetBean);
                targetList.add(targetBean);
            }
            String str = JSON.toJSONString(targetList);
            logger.info("【发送小票MQ】,json字符串为 {}", str);
            try {
                rabbitTemplate.convertAndSend("exchange.direct", SEND_STORE_VOUCHER, str);
                logger.info(" sap.voucher.receipts  send mq message success");
                omsVoucherPointDetService.updateForMQ("1");
            } catch (Exception e) {
                logger.error("【发送小票MQ】,sap.voucher.receipts  failed，失败消息 {}", e.getMessage());
                throw new ServiceException(ErrorCode.SAP_INTEGRAL_ACCUMULATION_ERROR.getCode(),
                        ErrorCode.SAP_INTEGRAL_ACCUMULATION_ERROR.getMsg());
            }
        }

    }

    /**
     * 获取日期
     *
     * @param flag
     * @return
     */
    private String getDate(int flag) {
     
    }

    /**
     * 获取门店名字
     *
     * @param bean
     * @return
     */
    private OmsVoucherPointDet getStoreInfoDto(OmsVoucherPointDet bean) {
        logger.info("通过storeCode获取storeName，storeCode {}", bean.getStoreCode());
      
    }

}
