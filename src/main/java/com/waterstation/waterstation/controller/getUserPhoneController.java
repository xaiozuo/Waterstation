package com.waterstation.waterstation.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
@RestController
@Controller
@RequestMapping("/get")
public class getUserPhoneController {
    /**
     * 获取用户手机号
     */
    @PostMapping("/phone")
    public Map<String, Object> getPhone(@RequestBody Map<String, Object> requestParams) {
        String code = (String) requestParams.get("code");
//        String appid = (String) requestParams.get("appid");
//        String secret = (String) requestParams.get("secret");
        String appid = "wx715a08a8d60a8595";
        String secret = "24f03b851b81a1225c43f17535886eb9";
        // 获取 access_token
        String accessToken = getAccessToken(code,appid,secret);
        if (StrUtil.isBlank(accessToken)) {
            return null;
        }
        // 发送请求获取用户手机号
        Map<String, Object> userPhoneMap = getUserPhoneNumber(accessToken,code);
        if (userPhoneMap == null ||!userPhoneMap.containsKey("phoneNumber")) {
            return null;
        }
        Map<String, Object> response = new HashMap<>();
        response.put("phoneNumber", userPhoneMap.get("phoneNumber"));
//        for (Map.Entry<String, Object> entry : response.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//        }
        return response;
//        return getUserPhoneNumber(accessToken);
    }
    public static String getAccessToken(String code,String appid,String secret) {
        // 构建请求 URL
//        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ appid +"&secret="+ secret +"&code="+ code +"&grant_type=authorization_code";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        String result= HttpUtil.get(url);
//        String accessToken = extractOpenId(result,"access_token");
        JSONObject jsonObject = new JSONObject(result);
        String accessToken = jsonObject.getStr("access_token");
        return accessToken;
    }
    public static Map<String,Object> getUserPhoneNumber(String accessToken,String code) {
        // 构建请求 URL
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        Map<String, String> params = new HashMap<>();
        params.put("code",code);
        Gson gson = new Gson();
        String jsonParams = gson.toJson(params);
        String result = HttpUtil.post(url,jsonParams);
        JSONObject jsonObject = new JSONObject(result);
        if (!jsonObject.containsKey("errcode")) {
            // 处理错误情况
            return null;
        }
        JSONObject phoneInfo = jsonObject.getJSONObject("phone_info");
        String phoneNumber = phoneInfo.getStr("phoneNumber");
//        Map<String, Object> map = convertToMap(result);
        Map<String, Object> map = new HashMap<>();
        map.put("phoneNumber", phoneNumber);
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//        }
        return map;
    }
//    public static Map<String, Object> convertToMap(String result) {
//        Map<String, Object> map = new HashMap<>();
//        // 假设结果是一个 JSON 字符串
//        // 这里可以使用合适的 JSON 解析库来解析并转换
//        // 示例中简单地模拟分割和赋值
//        String[] parts = result.split(",");
//        for (String part : parts) {
//            String[] keyValue = part.split(":");
//            if (keyValue.length == 2) {
//                map.put(keyValue[0].trim(), keyValue[1].trim());
//            }
//        }
//
//        return map;
//    }
//    public static String extractOpenId(String result, String accessToken) {
//        try {
//            JSONObject jsonObject = new JSONObject(result);
//            return jsonObject.getStr("accessToken");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}