package com.waterstation.waterstation.constant;

public class WechatPayHttpHeaders {
    public static final String REQUEST_ID = "Request-ID";
    public static final String WECHAT_PAY_SERIAL = "Wechatpay-Serial";
    public static final String WECHAT_PAY_SIGNATURE = "Wechatpay-Signature";
    public static final String WECHAT_PAY_TIMESTAMP = "Wechatpay-Timestamp";
    public static final String WECHAT_PAY_NONCE = "Wechatpay-Nonce";

    private WechatPayHttpHeaders() {
        // Don't allow instantiation
    }
}
