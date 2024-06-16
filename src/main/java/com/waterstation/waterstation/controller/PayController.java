package com.waterstation.waterstation.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.waterstation.waterstation.entity.Result;
import com.waterstation.waterstation.service.PayService;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付模块控制器
 */
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 小程序支付下单接口
     * @return 返回结果
     */
    @ApiOperation("小程序支付功能")
    @PostMapping("/pay")
    public Result wxPay(){
        Map payHistory = payService.insertPayRecord();
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
}
