package com.waterstation.waterstation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置
 * @author lf
 * @date 2023/8/30
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "pay")
public class WxPayConfig {

    /**
     * 微信公众号appid
     */
    private String appId;

    /**
     * 公众号设置的API v2密钥
     */
    private String apiKey;

    /**
     * 微信商户平台 商户id
     */
    private String mchId;

    /**
     *小程序密钥
     */
    private String appSecret;

    /**
     * 小程序支付异步回调地址
     */
    private String notifyUrl;

}
