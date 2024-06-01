package com.waterstation.waterstation.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Controller
@RequestMapping("/get")
public class getOpenIdController {
    @PostMapping("/openid")
    public Map<String, String> getOpenid(@RequestBody Map<String, Object> requestParams) {
        String appid = "wx715a08a8d60a8595";
        String secret = "24f03b851b81a1225c43f17535886eb9";
        String js_code = (String) requestParams.get("js_code");
        String grant_type = "authorization_code";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appid);
        paramMap.put("secret", secret);
        paramMap.put("js_code", js_code);
        paramMap.put("grant_type", grant_type);
        String result= HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", paramMap);
        String openid = extractOpenId(result);
        Map<String, String> openidMap = new HashMap<>();
        openidMap.put("openid", openid);
        return openidMap;
    }
    public static String extractOpenId(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getStr("openid");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取设备信息
    @GetMapping("/deviceid")
    public String getDeviceInfo() {
        // "http://api.happy-ti.com:2028/device/getdetail?appid=91f25fab87eb44c9d5c2494caedc2683&saler=13466647109&deviceId=863482064245059";
        String appid = "91f25fab87eb44c9d5c2494caedc2683";
        String saler = "13466647109";
        String deviceId = "863482064245059";
        // String js_code = (String) requestParams.get("js_code");
        // String grant_type = "authorization_code";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appid);
        paramMap.put("saler", saler);
        paramMap.put("deviceId", deviceId);
        String result= HttpUtil.get("http://api.happy-ti.com:2028/device/getdetail", paramMap);
        return result;
        // String openid = extractOpenId(result);
        // Map<String, String> openidMap = new HashMap<>();
        // openidMap.put("openid", openid);
        // return openidMap;
    }

    // 通知设备上线
    @GetMapping("/wakeup")
    public String wakeupDevice() {
        // http://api.happy-ti.com:2028/device/notify?appid=91f25fab87eb44c9d5c2494caedc2683&saler=13466647109&deviceId=863482064245059
        String appid = "91f25fab87eb44c9d5c2494caedc2683";
        String saler = "13466647109";
        String deviceId = "863482064245059";
        // String js_code = (String) requestParams.get("js_code");
        // String grant_type = "authorization_code";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appid);
        paramMap.put("saler", saler);
        paramMap.put("deviceId", deviceId);
        String result= HttpUtil.get("http://api.happy-ti.com:2028/device/notify", paramMap);
        return result;
    }

    // 下达打水命令
    @GetMapping("/order")
    public String orderDevice() {
        // http://api.happy-ti.com:2028/trade/qrcreate?saler=13466647109&appid=91f25fab87eb44c9d5c2494caedc2683&deviceId=863482064245059&value=3&userid=13466647109&ch=&location=beijing&salerOrderId=1235
        String appid = "91f25fab87eb44c9d5c2494caedc2683";
        String saler = "13466647109";
        String deviceId = "863482064245059";
        String value = "3";
        String userid = "13466647109";
        String ch = "1";
        String location = "beijing";
        String salerOrderId = "1235";
        // String js_code = (String) requestParams.get("js_code");
        // String grant_type = "authorization_code";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appid);
        paramMap.put("saler", saler);
        paramMap.put("deviceId", deviceId);
        paramMap.put("value", value);
        paramMap.put("userid", userid);
        paramMap.put("ch", ch);
        paramMap.put("location", location);
        paramMap.put("salerOrderId", salerOrderId);
        String result= HttpUtil.get("http://api.happy-ti.com:2028/trade/qrcreate", paramMap);
        return result;
    }

    // 查询打水状态
    @GetMapping("/query")
    public String queryOrder() {
        // http://api.happy-ti.com:2028/trade/query?saler=13466647109&appid=91f25fab87eb44c9d5c2494caedc2683&deviceId=863482064245059&salerOrderId=1235
        String appid = "91f25fab87eb44c9d5c2494caedc2683";
        String saler = "13466647109";
        String deviceId = "863482064245059";
        String salerOrderId = "1235";
        // String js_code = (String) requestParams.get("js_code");
        // String grant_type = "authorization_code";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appid);
        paramMap.put("saler", saler);
        paramMap.put("deviceId", deviceId);
        paramMap.put("salerOrderId", salerOrderId);
        String result= HttpUtil.get("http://api.happy-ti.com:2028/trade/query", paramMap);
        return result;
    }
}
