package com.waterstation.waterstation.utils;

/**
 * 微信支付配置类
 * @author lf
 * @date 2023/9/1
 */
public class Configure {

    /**
     * 商户支付秘钥
     */
    private static String key = "";

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        Configure.key = key;
    }

}
