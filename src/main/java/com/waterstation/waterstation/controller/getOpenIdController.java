package com.waterstation.waterstation.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@Controller
@RequestMapping("/get")
public class getOpenIdController {
    private static Integer isCodeZero;
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
        System.out.println(openidMap);
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
    @PostMapping("/deviceid")
    public String getDeviceInfo(@RequestBody Map<String, Object> requestParams) {
//        // "http://api.happy-ti.com:2028/device/getdetail?appid=91f25fab87eb44c9d5c2494caedc2683&saler=13466647109&deviceId=863482064245059";
//        String appid = "91f25fab87eb44c9d5c2494caedc2683";
//        String saler = "13466647109";
//        String deviceId = "863482064245059";
//        // String js_code = (String) requestParams.get("js_code");
//        // String grant_type = "authorization_code";
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("appid", appid);
//        paramMap.put("saler", saler);
//        paramMap.put("deviceId", deviceId);
//        String result= HttpUtil.get("http://api.happy-ti.com:2028/device/getdetail", paramMap);
        String appid = (String) requestParams.get("appId");
        String deviceId = (String) requestParams.get("id");
        String saler = (String) requestParams.get("saler");
        String url = "http://api.happy-ti.com:2028/device/getdetail?appid="+appid+"&saler="+saler+"&deviceId="+deviceId;
        return HttpUtil.get(url);
        // String openid = extractOpenId(result);
        // Map<String, String> openidMap = new HashMap<>();
        // openidMap.put("openid", openid);
        // return openidMap;
    }

    // 通知设备上线
    @PostMapping("/wakeup")
    public String wakeupDevice(@RequestBody Map<String, Object> requestParams) {
//        // http://api.happy-ti.com:2028/device/notify?appid=91f25fab87eb44c9d5c2494caedc2683&saler=13466647109&deviceId=863482064245059
//        String appid = "91f25fab87eb44c9d5c2494caedc2683";
//        String saler = "13466647109";
//        String deviceId = "863482064245059";
//        // String js_code = (String) requestParams.get("js_code");
//        // String grant_type = "authorization_code";
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("appid", appid);
//        paramMap.put("saler", saler);
//        paramMap.put("deviceId", deviceId);
//        String result= HttpUtil.get("http://api.happy-ti.com:2028/device/notify", paramMap);
        String appid = (String) requestParams.get("appId");
        String deviceId = (String) requestParams.get("id");
        String saler = (String) requestParams.get("saler");
        String url = "http://api.happy-ti.com:2028/device/notify?appid="+appid+"&saler="+saler+"&deviceId="+deviceId;
        return HttpUtil.get(url);
    }

    // 下达打水命令
    @PostMapping("/order")
    public ResponseWithId orderDevice(@RequestBody Map<String, Object> requestParams) {
//        // http://api.happy-ti.com:2028/trade/qrcreate?saler=13466647109&appid=91f25fab87eb44c9d5c2494caedc2683&deviceId=863482064245059&value=3&userid=13466647109&ch=&location=beijing&salerOrderId=1235
//        String appid = "91f25fab87eb44c9d5c2494caedc2683";
//        String saler = "13466647109";
//        String deviceId = "863482064245059";
//        String value = "3";
//        String userid = "13466647109";
//        String ch = "1";
//        String location = "beijing";
//        String salerOrderId = "1235";
//        // String js_code = (String) requestParams.get("js_code");
//        // String grant_type = "authorization_code";
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("appid", appid);
//        paramMap.put("saler", saler);
//        paramMap.put("deviceId", deviceId);
//        paramMap.put("value", value);
//        paramMap.put("userid", userid);
//        paramMap.put("ch", ch);
//        paramMap.put("location", location);
//        paramMap.put("salerOrderId", salerOrderId);
//        String result= HttpUtil.get("http://api.happy-ti.com:2028/trade/qrcreate", paramMap);
//        return result;
        String appid = (String) requestParams.get("appId");
        String saler = (String) requestParams.get("saler");
        String deviceId = (String) requestParams.get("id");
        Integer value = (Integer) requestParams.get("value");
        String userid = (String) requestParams.get("userid");
        String ch = (String) requestParams.get("ch");
        String location = (String) requestParams.get("location");
        Set<String> uniqueCodes = generateUniqueRandomCodes(1);
        Iterator<String> iterator = uniqueCodes.iterator();
        String salerOrderId = iterator.next();
        String url1 = "api.happy-ti.com:2028/trade/qrcreate?appid=" + appid + "&saler=" + saler + "&deviceId=" + deviceId + "&value="
                + value + "&userid=" + userid + "&ch=" + ch + "&location=" + location + "&salerOrderId=" + salerOrderId;
        ResponseWithId responseWithId = new ResponseWithId(HttpUtil.get(url1), salerOrderId);
        return responseWithId;
    }

    // 查询打水状态
    @PostMapping("/query")
    public String queryOrder(@RequestBody Map<String, Object> requestParams) {
//        // http://api.happy-ti.com:2028/trade/query?saler=13466647109&appid=91f25fab87eb44c9d5c2494caedc2683&deviceId=863482064245059&salerOrderId=1235
//        String appid = "91f25fab87eb44c9d5c2494caedc2683";
//        String saler = "13466647109";
//        String deviceId = "863482064245059";
//        String salerOrderId = "1235";
//        // String js_code = (String) requestParams.get("js_code");
//        // String grant_type = "authorization_code";
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("appid", appid);
//        paramMap.put("saler", saler);
//        paramMap.put("deviceId", deviceId);
//        paramMap.put("salerOrderId", salerOrderId);
//        String result= HttpUtil.get("http://api.happy-ti.com:2028/trade/query", paramMap);
//        return result;
        String appid = (String) requestParams.get("appId");
        String saler = (String) requestParams.get("saler");
        String deviceId = (String) requestParams.get("id");
        String salerOrderId = (String) requestParams.get("salerOrderId");
        String url2 = "api.happy-ti.com:2028/trade/query?saler=" + saler + "&appid=" + appid + "&deviceId=" + deviceId +"&salerOrderId=" + salerOrderId;
        return HttpUtil.get(url2);
//        int code = Integer.parseInt(Objects.requireNonNull(extractOpenId(result, "code")));
//        Timer timer = new Timer();
//        // 定义定时任务执行的时间间隔（单位：毫秒）
//        long interval = 60000; // 一分钟
//        // 创建定时任务对象
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                // 在这里调用接口
//                isCodeZero = callAPI(appid,saler,deviceId,salerOrderId);
//                if(isCodeZero==0){
//                    timer.cancel();
//                }
//            }
//        };
//        TimerTask finalTask = new TimerTask() {
//            @Override
//            public void run() {
//                isCodeZero = callAPI(appid,saler,deviceId,salerOrderId);
//                if(isCodeZero!=0){
//                    boolean isOver = false;
//                    timer.cancel();
//                }
//            }
//        };
//        if(code != 0){
//            // 启动定时任务
//            timer.schedule(task, 0, interval);
//            timer.schedule(finalTask, 10 * 60000);
//        }else{
//            return "已成功通知设备" + result;
//        }
//        if(isCodeZero==0){
//            return isCodeZero;
//        }
    }
    @PostMapping("/cardinfo")
    public Map<String, String> getcardinfo(@RequestBody Map<String, Object> requestParams) {
        String appid = (String) requestParams.get("appid");
        String user = (String) requestParams.get("openid");
        String card = (String) requestParams.get("icid");
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appid);
        paramMap.put("user)", user);
        paramMap.put("card", card);
        String result= HttpUtil.get("api.happy-ti.com:2028/cardinfo", paramMap);
        String value = extractOpenId(result,"value");
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("value", value);
        return valueMap;
    }
    @PostMapping("/addvalue")
    public String addvalue(@RequestBody Map<String, Object> requestParams) {
        String appid = (String) requestParams.get("appid");
        String user = (String) requestParams.get("openid");
        String card = (String) requestParams.get("icid");
        String value = (String) requestParams.get("value");
        String income = (String) requestParams.get("income");
        String password = (String) requestParams.get("password");
        String trad_id = (String) requestParams.get("trad_id");
        String url = "api.happy-ti.com:2028/addvalue?appid=" + appid +"&user=" + user + "&card=" + card + "&value=" + value + "&income=" + income + "&password=" + password + "&trad_id=" + trad_id;
        return HttpUtil.get(url);
    }
    public static Integer callAPI(String appid,String saler,String deviceId,String salerOrderId) {
        // 调用你自己的接口
        String url2 = "api.happy-ti.com:2028/trade/query?saler=" + saler + "&appid=" + appid + "&deviceId" + deviceId +"&salerOrderId=" + salerOrderId;
        String result = HttpUtil.get(url2);
        return Integer.valueOf(Objects.requireNonNull(extractOpenId(result, "code")));

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
    public Set<String> generateUniqueRandomCodes(int count) {
        Set<String> codes = new HashSet<>();
        Random random = new Random();
        while (codes.size() < count) {
            char letter = (char) (random.nextInt(26) + 'a');
            int digit =random.nextInt(1000000000);
            String code = String.valueOf(letter) + digit;
            if (!codes.contains(code)) {
                codes.add(code);
            }
        }
        return codes;
    }
}
