package com.ibm.core.rabbit.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.ibm.core.rabbit.ReStoreVoucherService;
import com.ibm.modules.oms.entity.VoucherOrigin;
import com.ibm.modules.oms.service.VoucherOriginService;
import com.ibm.modules.store.client.dto.storeinfo.StoreInfoDto;
import com.ibm.modules.store.client.service.storeinfo.IStoreService;
import com.ibm.oms.constants.Constants;
import com.ibm.oms.enums.ErrorCode;
import com.ibm.oms.enums.PayMethodType;
import com.ibm.oms.enums.PlateType;
import com.ibm.oms.enums.VoucherType;

@Service("reStoreVoucherServiceImpl")
public class ReStoreVoucherServiceImpl implements ReStoreVoucherService {
    @Autowired
    VoucherOriginService voucherOriginService;
    @Autowired
    IStoreService storeService;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 保存门店数据
     *
     * @param message
     */
    @Override
    public void saveDateFromStrore(Object message) {
        String str = message.toString();
        logger.info("【接受】处理门店库存记录：{}", str);
        if (StringUtils.isEmpty(str))
            return;
        List<VoucherOrigin> voucherOriginList = null;
        try {
            voucherOriginList = JSONArray.parseArray(str, VoucherOrigin.class);
        } catch (Exception ex) {
            VoucherOrigin records = new VoucherOrigin();
            records.setErrorCode(ErrorCode.SAP_DATA_TRANS_ERROR.getCode());
            records.setErrorMsg(ErrorCode.SAP_DATA_TRANS_ERROR.getMsg());
            records.setStatus(Constants.HandStatus_ERROR);
            voucherOriginService.save(records);
            logger.info("【JSON】转换异常：{}", ex);
            return;
        }
        for (VoucherOrigin records : voucherOriginList) {
            //校验与调用门店接口
            VoucherOrigin voucherOrigin = checkParam(records);
            //入库
            if (StringUtils.isEmpty(voucherOrigin.getErrorMsg())) {
                voucherOrigin.setXblnr(getXblnr(records));
                voucherOrigin.setBktxt(records.getType());
                voucherOrigin.setStatus(Constants.HandStatus_Wait);
                if (null == records.getGjahr()) {
                    voucherOrigin.setGjahr(records.getBudat().substring(0, 4));
                }
                try {
                    voucherOriginService.save(records);
                }catch (Exception e){
                    logger.info(" normal save exception  this msg {}",e.getMessage());
                }
            } else if (!ErrorCode.STORE_INTER_CALL_EXCEPTION.getCode().equals(voucherOrigin.getErrorCode())) {
                //说明为数据校验有错
                voucherOrigin.setErrorCode(ErrorCode.STORE_DATE_NOT_EXISTS_ERROR.getCode());
                voucherOrigin.setStatus(Constants.HandStatus_ERROR);
                try {
                    voucherOriginService.save(records);
                }catch (Exception e){
                    logger.info(" param  save exception  this msg {}",e.getMessage());
                }
            } else {
                //说明为接口异常
                try {
                    voucherOriginService.save(records);
                }catch (Exception e){
                    logger.info(" interface  save exception  this msg {}",e.getMessage());
                }
            }

        }
    }

    /**
     * 调用门店接口获取有关信息
     *
     * @param records
     */
    private VoucherOrigin getStoreInfo(VoucherOrigin records) throws Exception {
        //因为typ为4会带过来所有的板块，公司，利润中心等信息，所以不需要调用接口
        
        return records;
    }

    /**
     * 进行参数校验
     *
     * @param records
     */
    private VoucherOrigin checkParam(VoucherOrigin records) {
        //积分购买  (需要设置公司与利润中心),积分清理 9999 不需要调用接口
        if (!(VoucherType.PCR.getCode().equals(records.getType()) || VoucherType.PBY.getCode().equals(records.getType()))) {
            if (null == records.getStoreName() || "".equals(records.getStoreName())) {
                records.setErrorMsg(("store_Name").concat(ErrorCode.STORE_DATE_NOT_EXISTS_ERROR.getMsg()));
                return records;
            }
            //其他12中类型需要验证storeName与storeCode
            if (null == records.getStoreCode() || "".equals(records.getStoreCode())) {
                records.setErrorMsg(("store_Code").concat(ErrorCode.STORE_DATE_NOT_EXISTS_ERROR.getMsg()));
                return records;
                //加盟便利店的四种类型不需要调用接口
                /*else if (VoucherType.CPCJ.getCode().equals(records.getType()) || VoucherType.CPAJ.getCode().equals(records.getType())
                    || VoucherType.EPCAJ.getCode().equals(records.getType()) || VoucherType.EPACJ.getCode().equals(records.getType())) {
                records.setBukrs("8000");
                records.setPrctr("1800000001");
                //其他八种类型需要调用接口获取值
            } */
            } else {
                try {
                    VoucherOrigin storeInfo = getStoreInfo(records);
                    VoucherOrigin voucherOrigin = new VoucherOrigin();
                    BeanUtils.copyProperties(storeInfo, voucherOrigin);
                } catch (Exception e) {
                    //门店接口查询为空或则异常
                    records.setErrorMsg(("store interface query").concat(ErrorCode.STORE_INTER_CALL_EXCEPTION.getMsg()));
                    records.setErrorCode(ErrorCode.STORE_INTER_CALL_EXCEPTION.getCode());
                    records.setStatus(Constants.HandStatus_ERROR);
                    return records;
                }
            }
        } else {
            //积分购买---电商  门店编码不校验，写死公司编码与利润中心以及板块相关5000,1500000001
            if (VoucherType.PBY.getCode().equals(records.getType())) {
                records.setBukrs("5000");
                records.setPrctr("1500000001");
                records.setPlateName(PlateType.All.getMessage());
                records.setPlateCode(PlateType.All.getCode());
            }
        }

        //凭证类型不能为空
        if (null == records.getType() || "".equals(records.getType())) {
            records.setErrorMsg(("type").concat(ErrorCode.STORE_DATE_NOT_EXISTS_ERROR.getMsg()));
            return records;
        }
        //如果为累积积分累积 ---销售相关 那么部门编号不能为空
        if (VoucherType.CPA.getCode().equals(records.getType()) && (null == records.getDeptId() || "".equals(records.getDeptId()))) {
            records.setErrorMsg(("deptId").concat(ErrorCode.STORE_DATE_NOT_EXISTS_ERROR.getMsg()));
            return records;
        }

        //如果为累计积分累积-销售无关 需要设置成本中心
        if (VoucherType.CSR.getCode().equals(records.getType())) {
            records.setRcntr("2".concat(records.getStoreCode()).concat("99999"));
        }

        //收款方式类型  长短款需要校验收款方式类型
        if (VoucherType.LSC.getCode().equals(records.getType())) {
            if (null == records.getPayType() || "".equals(records.getPayType())) {
                records.setErrorMsg(("pay_Type").concat(ErrorCode.STORE_DATE_NOT_EXISTS_ERROR.getMsg()));
                return records;
            }
        }

        //pay_Type为1和2 需要校验PayMethod
        if (PayMethodType.PML.getCode().equalsIgnoreCase(records.getPayType()) || (PayMethodType.PMS.getCode().equalsIgnoreCase(records.getPayType()))) {
            if ((null == records.getPayMethod() || "".equals(records.getPayMethod()))) {
                records.setErrorMsg(("pay_Method").concat(ErrorCode.STORE_DATE_NOT_EXISTS_ERROR.getMsg()));
                return records;
            }
        }

        //单据日期不能为空
        if (null == records.getBldat() || "".equals(records.getBldat())) {
            records.setErrorMsg(("Bldat").concat(ErrorCode.STORE_DATE_NOT_EXISTS_ERROR.getMsg()));
            return records;
        }

        //过账日期不能为空
        if (null == records.getBudat() || "".equals(records.getBudat())) {
            records.setErrorMsg(("Budat").concat(ErrorCode.STORE_DATE_NOT_EXISTS_ERROR.getMsg()));
            return records;
        }
        return records;
    }


    /**
     * 进行单号生成拼接
     *
     * @param records
     */
    private String getXblnr(VoucherOrigin records) {
        String message = "T";
        String subDate = records.getBldat();
        //根据不同的type生成不同的固定的最后二位流水号
      
        return message.concat(records.getStoreCode()).concat(subDate).concat(lastNum);
    }
}
