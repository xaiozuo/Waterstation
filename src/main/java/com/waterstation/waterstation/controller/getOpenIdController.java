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
public class getOpenIdController {
    /**
     * 获取用户openid
     */
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
}
