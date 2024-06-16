package com.waterstation.waterstation.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.thoughtworks.xstream.XStream;
import com.waterstation.waterstation.config.WxPayConfig;
import com.waterstation.waterstation.constant.WeChatPayUrlConstants;
import com.waterstation.waterstation.entity.OrderReturnInfo;
import com.waterstation.waterstation.entity.QueryReturnInfo;
import com.waterstation.waterstation.entity.SignInfo;
import com.waterstation.waterstation.entity.WeChatPay;
import com.waterstation.waterstation.service.PayService;
import com.waterstation.waterstation.utils.HttpRequest;
import com.waterstation.waterstation.utils.SignUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 支付模块相关功能
 */
@Service("payService")
public class PayServiceImpl implements PayService {
    @Resource
    private WxPayConfig payProperties;

    private static final DecimalFormat df = new DecimalFormat("#");

    /**
     * 插入订单记录
     * @return 返回结果
     */
    @Override
    @Transactional
    public Map insertPayRecord() {
        //接收返回的参数
        Map<String, Object> map = new HashMap<>();
        String title = "koko测试点数";
        //金额 * 100 以分为单位
        BigDecimal fee = BigDecimal.valueOf(1);
        BigDecimal RMB = new BigDecimal(100);
        BigDecimal totalFee = fee.multiply(RMB);

        try {
            WeChatPay weChatPay = new WeChatPay();
            weChatPay.setAppid(payProperties.getAppId());
            weChatPay.setMch_id(payProperties.getMchId());
            weChatPay.setNonce_str(getRandomStringByLength(32));
            weChatPay.setBody(title);
            weChatPay.setOut_trade_no(getRandomStringByLength(32));
            weChatPay.setTotal_fee( df.format(Double.parseDouble(String.valueOf(totalFee))));
            weChatPay.setSpbill_create_ip("127.0.0.1");
            weChatPay.setNotify_url(payProperties.getNotifyUrl());
            weChatPay.setTrade_type("JSAPI");
            //这里直接使用当前用户的openid
            weChatPay.setOpenid("oOKq*******xj8o");
            weChatPay.setSign_type("MD5");
            //生成签名
            String sign = SignUtils.getSign(weChatPay);
            weChatPay.setSign(sign);

            String result = HttpRequest.sendPost(WeChatPayUrlConstants.Uifiedorder, weChatPay);
            System.out.println(result);

            //将返回结果从xml格式转换为map格式
            Map<String, String> wxResultMap = WXPayUtil.xmlToMap(result);
            if (ObjectUtil.isNotEmpty(wxResultMap.get("return_code")) && wxResultMap.get("return_code").equals("SUCCESS")){
                if (wxResultMap.get("result_code").equals("FAIL")){
                    map.put("msg", "统一下单失败");
                    map.put("status",500);
                    map.put("data", wxResultMap.get("err_code_des"));
                    return map;
                }
            }
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);

            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);
            // 二次签名
            if ("SUCCESS".equals(returnInfo.getReturn_code()) && returnInfo.getReturn_code().equals(returnInfo.getResult_code())) {
                SignInfo signInfo = new SignInfo();
                signInfo.setAppId(payProperties.getAppId());
                long time = System.currentTimeMillis() / 1000;
                signInfo.setTimeStamp(String.valueOf(time));
                signInfo.setNonceStr(WXPayUtil.generateNonceStr());
                signInfo.setRepay_id("prepay_id=" + returnInfo.getPrepay_id());
                signInfo.setSignType("MD5");
                //生成签名
                String sign1 = SignUtils.getSign(signInfo);
                Map<String, String> payInfo = new HashMap<>();
                payInfo.put("timeStamp", signInfo.getTimeStamp());
                payInfo.put("nonceStr", signInfo.getNonceStr());
                payInfo.put("package", signInfo.getRepay_id());
                payInfo.put("signType", signInfo.getSignType());
                payInfo.put("paySign", sign1);
                map.put("status", 200);
                map.put("msg", "统一下单成功!");
                map.put("data", payInfo);

                //预下单成功，处理业务逻辑
                //****************************//

                // 业务逻辑结束 回传给小程序端唤起支付
                return map;
            }
            map.put("status", 500);
            map.put("msg", "统一下单失败!");
            map.put("data", null);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询订单
     * @param out_trade_no 订单号
     * @return 返回结果
     */
    @Override
    public Map orderQuery(String out_trade_no){

        Map<String, Object> map = new HashMap<>();

        try {
            WeChatPay weChatPay = new WeChatPay();
            weChatPay.setAppid(payProperties.getAppId());
            weChatPay.setMch_id(payProperties.getMchId());
            weChatPay.setNonce_str(WXPayUtil.generateNonceStr());
            weChatPay.setOut_trade_no(out_trade_no);
            //order.setSign_type("MD5");
            //生成签名
            String sign = SignUtils.getSign(weChatPay);
            weChatPay.setSign(sign);

            String result = HttpRequest.sendPost(WXPayConstants.ORDERQUERY_URL, weChatPay);
            System.out.println(result);
            XStream xStream = new XStream();
            xStream.alias("xml", QueryReturnInfo.class);

            QueryReturnInfo returnInfo = (QueryReturnInfo) xStream.fromXML(result);
            map.put("status", 500);
            map.put("msg", "统一下单失败!");
            map.put("data", returnInfo);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}

