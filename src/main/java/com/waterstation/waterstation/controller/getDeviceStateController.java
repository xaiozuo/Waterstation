package com.waterstation.waterstation.controller;

import cn.hutool.http.HttpUtil;
import com.waterstation.waterstation.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.waterstation.waterstation.common.Result.*;

@RestController
@Controller
@RequestMapping("/get")
public class getDeviceStateController {
    /**
     * 获取设备状态
     */
    @PostMapping("/devicestate")
    public Result reducePointBalance(@RequestBody Map<String, Object> requestParams) {
        String appid = (String) requestParams.get("appid");
        String id = (String) requestParams.get("id");
        String saler = (String) requestParams.get("saler");
        String url = "api.happy-ti.com:2028/device/getdetail?appid="+appid+"&saler="+saler+"&deviceId="+id;
        String result= HttpUtil.get(url);
        String payStatus = extractOpenId(result,"payStatus");
        if("online".equals(payStatus)) {
            return adviceExist();
        }
        return adviceNotExist();
    }
    public static String extractOpenId(String json,String key) {
        Map<String, Object> data = parseJSON(json);
        if (data.containsKey(key)) {
            return data.get(key).toString();
        }
        return null;
    }
    public static Map<String, Object> parseJSON(String json) {
        // 这里可以使用具体的 JSON 解析库来进行实际解析
        // 为了简单示例，手动构建一个 Map
        Map<String, Object> map = new HashMap<>();
        String[] parts = json.split(",");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim().replace("\"", ""), keyValue[1].trim().replace("\"", ""));
            }
        }
        return map;
    }
}
