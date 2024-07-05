package com.waterstation.waterstation.constant;

import com.github.wxpay.sdk.WXPayUtil;
import com.waterstation.waterstation.entity.WeChatPay;
import com.waterstation.waterstation.utils.HttpRequest;
import com.waterstation.waterstation.utils.SignUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;

import static com.waterstation.waterstation.constant.WeChatPayUrlConstants.Uifiedorder;

@Log4j2
public class QrCodeRequest {
    public static Map<String, String> submitWeixinMessage(WeChatPay data){
        try {
            data.setTrade_type(data.getTrade_type());//扫一扫支付方式 NATIVE
            data.setSign(SignUtils.getSign(data.toMap()));//签名字符
            String returnData = HttpRequest.sendPost(Uifiedorder, data.toMap());
            System.out.println("======================>"+ returnData);
            if(returnData != null && !"".equals(returnData)){
                log.info("当前支付成功返回的数据:============>{}",returnData);
                Map<String, String> wxResultMap = WXPayUtil.xmlToMap(returnData);
//                Map<String,String>map = XMLParser.getMapFromXML(returnData);//解析返回的字符串 并且组成map集合
                System.out.println("==============================>" + wxResultMap);
                if(null != wxResultMap && !wxResultMap.isEmpty()){
                    String resultCode =(String)wxResultMap.get(WeChatPayUrlConstants.RESULT);
                    if("SUCCESS".equals(resultCode)){//链接生成成功
                        HashMap<String,String> nmap =new HashMap<>();
                        String params = data.getAttach().replace("'","\"");
                        nmap.put("appId",data.getAppid());
                        nmap.put("timeStamp",System.currentTimeMillis()+" ");
                        nmap.put("signType","MD5");
                        nmap.put("nonceStr",data.getNonce_str());
                        nmap.put("package", "prepay id="+ wxResultMap.get("prepay id"));
                        nmap.put("paySign",SignUtils.getSign(nmap));
                        nmap.put("attach",params);
                        nmap.put("orderNumber",data.getOut_trade_no()+" ");
                        return nmap;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
