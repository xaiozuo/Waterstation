package com.waterstation.waterstation.service;

import java.util.Map;

public interface PayService {
    /**
     * 发起支付
     * @return
     */
    Map insertPayRecord(String SpbillCreateIp,String Openid,double value);

    /**
     * 查询支付情况
     * @param out_trade_no
     * @return
     */
    Map orderQuery(String out_trade_no);
    /**
     * 申请退款
     * @return
     */
    Map insertPayRefund(String SpbillCreateIp,String Openid,double value);

}
