package com.waterstation.waterstation.controller;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.TbPointtransactionrecords;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.TbPointtransactionrecordsService;
import com.waterstation.waterstation.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.waterstation.waterstation.common.Result.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjj
 * @since 2024-05-10
 */
@RestController
@Controller
@RequestMapping("/point")
public class PointAddOrReduceController {

    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private TbPointtransactionrecordsService tbPointtransactionrecordsService;
    @PostMapping("/add")
    public Result incrementPointBalance(@RequestBody Map<String, Object> requestParams) {
        Integer id = (Integer) requestParams.get("id");
        Integer userId = (Integer) requestParams.get("userId");
        String userName = (String) requestParams.get("userName");
        Integer type = (Integer) 1;
        Integer pointValue = (Integer) requestParams.get("pointValue");
        String pointChangeType = (String) requestParams.get("pointChangeType");
        String deviceId = (String) requestParams.get("deviceId");
        Integer outlet= (Integer) requestParams.get("outlet");
        String advertisementLink = (String) requestParams.get("advertisementLink");
//        LocalDateTime transactionTime = (LocalDateTime ) requestParams.get("transaction_time");
        TbUser user = tbUserService.getById(userId);
        TbPointtransactionrecords point =new TbPointtransactionrecords();
        point.setId(id);
        point.setUserid(userId);
        point.setUserName(userName);
        point.setIncomeOrExpenseType(type);
        point.setPointValue(pointValue);
        point.setPointChangeType(pointChangeType);
        point.setDeviceId(deviceId);
        point.setOutlet(outlet);
        point.setAdvertisementLink(advertisementLink);
//        point.setTransactionTime(transactionTime);

        user.setId(userId);
        if(user.getTaskCount()<(Integer)(100)){
            user.setTaskCount(user.getTaskCount()+1);
        }else {
            return taskCountFull();
        }
        user.setPointbalance(user.getPointbalance() + 100);
        if(tbUserService.updateById(user)&&tbPointtransactionrecordsService.save(point)){
            return addPointSuccess();
        }
        return addPointFail();
    }

    @PostMapping("/reduce")
    public Result reducePointBalance(@RequestBody Map<String, Object> requestParams) {
        String appid = (String) requestParams.get("Appid");
        String id = (String) requestParams.get("id");
        String saler = (String) requestParams.get("saler");
        String url = "api.happy- ti.com:2028/device/getdetail?appid="+appid+"&saler="+saler+"&deviceId="+id;
        String result= HttpUtil.get(url);
        String payStatus = extractOpenId(result,"payStatus");
        String location = extractOpenId(result,"location");
        if("online".equals(payStatus)) {
            TbUser user = tbUserService.getByOpenid(id);
            Integer point = user.getPointbalance();
            if (point < 600) {
                return pointNotEnough();
            } else {
                user.setPointbalance(user.getPointbalance() - 600);
                TbPointtransactionrecords pointreduce = new TbPointtransactionrecords();
                pointreduce.setUserid(user.getId());
                pointreduce.setIncomeOrExpenseType(-1);
                pointreduce.setDeviceId(id);
                pointreduce.setPointValue(600);
                pointreduce.setUserName(user.getName());
                Integer value = (Integer) 1;
                Integer userid = user.getId();
                String ch = "";
                boolean updateById = tbUserService.updateById(user);
                boolean savepointreduce = tbPointtransactionrecordsService.save(pointreduce);
                String salerOrderId = String.valueOf(pointreduce.getId());
                String url1 = "api.happy- ti.com:2028/trade/qrcreate?appid=" + appid + "&saler=" + saler + "&deviceId=" + id + "&value"
                        + value + "&userid" + userid + "&ch" + ch + "&location" + location + "&salerOrderId" + salerOrderId;
                String result1 = HttpUtil.get(url1);
                if(result1==null){
                    return adviceNotExist();//改
                }
                String url2 = "api.happy- ti.com:2028/trade/query?saler=" + saler + "&appid=" + appid + "&deviceId" + pointreduce.getDeviceId() +"&salerOrderId=" + salerOrderId;
                String result2 = HttpUtil.get(url2);
                JSONObject jsonObject1 = new JSONObject(result2);
                String code = jsonObject1.getStr("code");
                if(Objects.equals(code, "1")){
                    String msg = "操作成功";
                    return adviceNotExist();//改
                }
            }
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
//        try {
//            JSONObject jsonObject = new JSONObject(result);
//            return jsonObject.getStr(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
