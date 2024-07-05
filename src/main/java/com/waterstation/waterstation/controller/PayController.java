package com.waterstation.waterstation.controller;

import cn.hutool.json.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.waterstation.waterstation.Config.WxPayConfig;
import com.waterstation.waterstation.constant.QrCodeRequest;
import com.waterstation.waterstation.entity.Result;
import com.waterstation.waterstation.entity.WeChatPay;
import com.waterstation.waterstation.service.PayService;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 支付模块控制器
 */
@RestController
@Log4j2
public class PayController {

    @Autowired
    private PayService payService;
    @Autowired
    private WxPayConfig payProperties;

    /**
     * 小程序支付下单接口
     * @return 返回结果
     */
    @ApiOperation("小程序支付功能")
    @PostMapping("/pay")
    public Result wxPay(@RequestBody Map<String, Object> requestParams){
        String SpbillCreateIp = (String) requestParams.get("SpbillCreateIp");
        String Openid = (String) requestParams.get("Openid");
        String valueStr = (String) requestParams.get("value");
        double value = Double.parseDouble(valueStr);
        Map payHistory = payService.insertPayRecord(SpbillCreateIp,Openid,value);
        return Result.success(payHistory);
    }


    /**
     * 查询订单
     */
    @ApiOperation("订单查询")
    @PostMapping("/wx/query")
    public Result orderQuery(@RequestParam("out_trade_no") String out_trade_no) {
        Map query = payService.orderQuery(out_trade_no);
        return Result.success(query);
    }

    /**
     * 微信小程序支付成功回调
     * @param request 请求
     * @param response 响应
     * @return 返回结果
     * @throws Exception 异常处理
     */
    @RequestMapping("/weixin/callback")
    public String callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("接口已被调用");
        ServletInputStream inputStream = request.getInputStream();
        String notifyXml = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        System.out.println(notifyXml);

        // 解析返回结果
        Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyXml);
        // 判断支付是否成功
        if ("SUCCESS".equals(notifyMap.get("result_code"))) {

            //支付成功时候，处理业务逻辑

            System.out.println("支付成功");
            System.out.println("<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ");

            /**
             * 注意
             * 因为微信回调会有八次之多,所以当第一次回调成功了,那么我们就不再执行逻辑了
             * return返回的结果一定是这种格式，当result_code返回的结果是SUCCESS时，则不进行调用了
             * 如果不返回下面的格式，业务逻辑会出现回调多次的情况，我就遇到过这种情况。
             */
            return "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        }

        // 创建响应对象：微信接收到校验失败的结果后，会反复的调用当前回调函数
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return_code", "FAIL");
        returnMap.put("return_msg", "");
        String returnXml = WXPayUtil.mapToXml(returnMap);
        response.setContentType("text/xml");
        System.out.println("校验失败");
        return returnXml;
    }
    /**
     * 小程序支付下单接口
     * @return 返回结果
     */
    @ApiOperation("小程序支付功能")
    @PostMapping("/pay/refund")
    public Result refund(@RequestBody Map<String, Object> requestParams){
        String SpbillCreateIp = (String) requestParams.get("SpbillCreateIp");
        String Openid = (String) requestParams.get("Openid");
        String valueStr = (String) requestParams.get("value");
        double value = Double.parseDouble(valueStr);
        Map payHistory = payService.insertPayRefund(SpbillCreateIp,Openid,value);
        return Result.success(payHistory);
    }
    @ApiOperation("小程序支付功能")
    @PostMapping("/weixinpay")
    public com.waterstation.waterstation.common.Result Pay(@RequestBody Map<String, Object> requestParams){
        String valueStr = (String) requestParams.get("value");
        double value = Double.parseDouble(valueStr);
        BigDecimal fee = BigDecimal.valueOf(value);
        BigDecimal RMB = new BigDecimal(100);
        BigDecimal totalFee = fee.multiply(RMB);
        String totalFeeStr = totalFee.stripTrailingZeros().toPlainString();
        //开始支付，构建数据体
        JSONObject json = new JSONObject();
        //生成订单号
        String orderNo = getRandomStringByLength(32);
        String SpbillCreateIp = (String) requestParams.get("SpbillCreateIp");
        String ip = SpbillCreateIp;
        //获取微信小程序openid
        String Openid = (String) requestParams.get("Openid");
        //分账接收用户id
        Integer userId = 1;

        //组装数据
        WeChatPay weChatPay = new WeChatPay();
        weChatPay.setOpenid(Openid);
        weChatPay.setTrade_type("JSAPI");
        weChatPay.setAppid(payProperties.getAppId());
        weChatPay.setMch_id(payProperties.getMchId());
        weChatPay.setNotify_url(payProperties.getNotifyUrl());
        weChatPay.setKey(payProperties.getAppSecret());

        weChatPay.setBody("打水");
        weChatPay.setOut_trade_no(orderNo);
        weChatPay.setProduct_id("1001");
        weChatPay.setSpbill_create_ip(ip);
        weChatPay.setTotal_fee(totalFeeStr);
        weChatPay.setNonce_str(getRandomStringByLength(32));

        json.put("userId",userId);
        json.put("type","weixinpay");
        json.put("ip",ip);
        //创建订单
        String params = json.toString().replace("\"","'");
        log.info("执行支付传递的参数：{}",params);
        weChatPay.setAttach(params);
        log.info("操作完毕，开始发起支付");
        Map<String,String> weixinMap = QrCodeRequest.submitWeixinMessage(weChatPay);
        return com.waterstation.waterstation.common.Result.success("",weixinMap);
    }
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
