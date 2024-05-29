package com.waterstation.waterstation.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
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
    @PostMapping("/phone")
    public Map<String, Object> getPhone(@RequestBody Map<String, Object> requestParams) {
        String code = (String) requestParams.get("code");
        // 获取 access_token
        String accessToken = getAccessToken();
        if (accessToken == null) {
            return null;
        }
        // 发送请求获取用户手机号
        return getUserPhoneNumber(accessToken);
    }
    public static String getAccessToken() {
        // 构建请求 URL
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + "wx715a08a8d60a8595" + "&secret=" + "24f03b851b81a1225c43f17535886eb9";
        String result= HttpUtil.get(url);
        String accessToken = extractOpenId(result,"access_token");
        return accessToken;
    }
    public static Map<String,Object> getUserPhoneNumber(String accessToken) {
        // 构建请求 URL
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        String result = HttpUtil.get(url);
        Map<String, Object> map = convertToMap(result);
        return map;
    }
    public static Map<String, Object> convertToMap(String result) {
        Map<String, Object> map = new HashMap<>();
        // 假设结果是一个 JSON 字符串
        // 这里可以使用合适的 JSON 解析库来解析并转换
        // 示例中简单地模拟分割和赋值
        String[] parts = result.split(",");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        return map;
    }
    public static String extractOpenId(String result, String accessToken) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getStr("accessToken");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}