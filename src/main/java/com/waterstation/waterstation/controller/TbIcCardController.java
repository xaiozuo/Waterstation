package com.waterstation.waterstation.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.waterstation.waterstation.entity.TbIcCard;
import com.waterstation.waterstation.service.TbIcCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Controller
@RequestMapping("/tb-IcCard")
public class TbIcCardController {
    @Autowired
    private TbIcCardService tbIcCardService;
    /**
     * 查询ic卡信息
     */
    @GetMapping("/list")
    public List<TbIcCard> list(){return tbIcCardService.list();}

    /**
     * 添加ic卡信息
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TbIcCard tbIcCard){
        return tbIcCardService.save(tbIcCard);
    }
    /**
     * 积分转换为ic卡余额
     */
    @PostMapping("/rechargeByPoint")
    public String rechargeByPoint(@RequestBody Map<String, Object> requestParams){
        String appid = (String) requestParams.get("appid");
        String saler = (String) requestParams.get("saler");
        String  icid= (String) requestParams.get("icid");
        String value = (String) requestParams.get("value");
        String income = (String) requestParams.get("income");
        String password = (String) requestParams.get("password");
        String trad_id = (String) requestParams.get("trad_id");
        String url = "api.happy-ti.com:2028/device/getdetail?appid="+appid+"&user="+saler+"&card="+icid+"&value="+value+"&income="+income+"&password="+password+"&trad_id="+trad_id;
        String response = HttpUtil.get(url);
        JSONObject responseObject = new JSONObject(response);
        return responseObject.toString();
    }
    /**
     * 零钱换ic卡余额（弃用）
     */
//    @PostMapping("/rechargeByPocketMoney")
//    public String rechargeByPocketMoney(@RequestBody Map<String, Object> requestParams){
//        String appid = (String) requestParams.get("appid");
//        String saler = (String) requestParams.get("saler");
//        String  icid= (String) requestParams.get("icid");
//        String value = (String) requestParams.get("value");
//        String income = (String) requestParams.get("income");
//        String password = (String) requestParams.get("password");
//        String trad_id = (String) requestParams.get("trad_id");
//        String url = "api.happy-ti.com:2028/device/getdetail?appid="+appid+"&user="+saler+"&card="+icid+"&value="+value+"&income="+income+"&password="+password+"&trad_id="+trad_id;
//        String response = HttpUtil.get(url);
//        JSONObject responseObject = new JSONObject(response);
//        return responseObject.toString();
//    }
    /**
     * 删除ic卡信息
     */
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return tbIcCardService.removeById(id);
    }
}
