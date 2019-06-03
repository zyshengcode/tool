package com.ibm.modules.oms.task.job;
import com.ibm.modules.oms.service.IOmsVoucherPointDet;
import com.jeeplus.common.utils.SpringContextHolder;
import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jeeplus.modules.monitor.entity.Task;


//定时任务 Dubbo服务的消费方

@DisallowConcurrentExecution
public class OmsVoucherPoint extends Task {

    private Logger logger = LoggerFactory.getLogger(OmsVoucherPoint.class);

    @Override
    public void run() {
        logger.info("小票凭证产生与发送Job ============================> START");
        IOmsVoucherPointDet bean = SpringContextHolder.getBean(IOmsVoucherPointDet.class);     //IOmsVoucherPointDet dubbo提供的接口    
        bean.sTicketVoucher();
        logger.info("小票凭证产生与发送Job ============================> END");
    }
}
