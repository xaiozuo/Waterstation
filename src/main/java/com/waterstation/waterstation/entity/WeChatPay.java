package com.waterstation.waterstation.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 微信支付预下单实体类
 */
@Data
@Accessors(chain = true)
public class WeChatPay {

    /**
     * 返回状态码  此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    public String return_code;

    /**
     * 返回信息 当return_code为FAIL时返回信息为错误原因 ，例如 签名失败 参数格式校验错误
     */
    private String return_msg;

    /**
     * 公众账号ID 调用接口提交的公众账号ID
     */
    private String appid;

    /**
     * 商户号 调用接口提交的商户号
     */
    private String mch_id;

    /**
     * api密钥 详见：https://pay.weixin.qq.com/index.php/extend/employee
     */
    private String api_key;

    /**
     * 设备号  自定义参数，可以为请求支付的终端设备号等
     */
    private String device_info;

    /**
     * 随机字符串    5K8264ILTKCH16CQ2502SI8ZNMTM67VS   微信返回的随机字符串
     */
    private String nonce_str;

    /**
     * 签名 微信返回的签名值，详见签名算法:https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_3
     */
    private String sign;

    /**
     * 签名类型
     */
    private String sign_type;


    /**
     * 业务结果 SUCCESS SUCCESS/FAIL
     */
    private String result_code;

    /**
     * 错误代码 当result_code为FAIL时返回错误代码，详细参见下文错误列表
     */
    private String err_code;

    /**
     * 错误代码描述 当result_code为FAIL时返回错误描述，详细参见下文错误列表
     */
    private String err_code_des;

    /**
     * 交易类型 JSAPI JSAPI -JSAPI支付 NATIVE -Native支付 APP -APP支付 说明详见；https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
     */
    private String trade_type;

    /**
     * 预支付交易会话标识 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    private String prepay_id;

    /**
     * 二维码链接     weixin://wxpay/bizpayurl/up?pr=NwY5Mz9&groupid=00 trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。注意：code_url的值并非固定，使用时按照URL格式转成二维码即可
     */
    private String code_url;

    /**
     * 商品描述  商品简单描述，该字段请按照规范传递，具体请见 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
     */
    private String body;

    /**
     * 商家订单号 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
     */
    private String out_trade_no;

    /**
     * 标价金额 订单总金额，单位为分，详见支付金额 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
     */
    private String total_fee;

    /**
     * 终端IP 支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
     */
    private String spbill_create_ip;

    /**
     * 通知地址 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。公网域名必须为https，如果是走专线接入，使用专线NAT IP或者私有回调域名可使用http
     */
    private String notify_url;

    /**
     * 子商户号 sub_mch_id 非必填（商户不需要传入，服务商模式才需要传入） 微信支付分配的子商户号
     */
    private String sub_mch_id;

    /**
     * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    private String attach;

    /**
     * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     */
    private String out_refund_no;

    /**
     * 退款总金额，单位为分，只能为整数，可部分退款。详见支付金额 https://pay.weixin.qq.com/wiki/doc/api/native_sl.php?chapter=4_2
     */
    private String refund_fee;

    /**
     * 退款原因 若商户传入，会在下发给用户的退款消息中体现退款原因 注意：若订单退款金额≤1元，且属于部分退款，则不会在退款消息中体现退款原因
     */
    private String refund_desc;

    /**
     * 交易结束时间 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟
     */
    private String time_expire;

    /**
     * 用户标识 trade_type=JSAPI，此参数必传，用户在主商户appid下的唯一标识。openid和sub_openid可以选传其中之一，如果选择传sub_openid,则必须传sub_appid。下单前需要调用【网页授权获取用户信息: https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html 】接口获取到用户的Openid。
     */
    private String openid;

    /**
     * 时间戳
     */
    private String time_stamp;

    /**
     * 会员类型
     */
    private String memberShipType;

}