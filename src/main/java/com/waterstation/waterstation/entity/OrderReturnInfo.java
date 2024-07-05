package com.waterstation.waterstation.entity;

import lombok.Data;

/**
 * 订单返回信息
 */
@Data
public class OrderReturnInfo {

    private String return_code;

    private String return_msg;

    private String result_code;

    private String appid;

    private String mch_id;

    private String nonce_str;

    private String sign;

    private String prepay_id;

    private String trade_type;

}
