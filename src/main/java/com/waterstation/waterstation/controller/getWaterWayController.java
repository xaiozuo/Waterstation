package com.waterstation.waterstation.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.Package;
import com.waterstation.waterstation.entity.TbPointrules;
import com.waterstation.waterstation.entity.TbPointtransactionrecords;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.PackageService;
import com.waterstation.waterstation.service.TbPointrulesService;
import com.waterstation.waterstation.service.TbPointtransactionrecordsService;
import com.waterstation.waterstation.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.waterstation.waterstation.common.Result.*;
import static com.waterstation.waterstation.controller.getDeviceStateController.extractOpenId;

/**
 * 打水方式
 */
@RestController
@Controller
@RequestMapping("/getwater")
public class getWaterWayController {
    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private TbPointtransactionrecordsService tbPointtransactionrecordsService;
    @Autowired
    private PackageService packageService;
    /**
     * 积分打水
     */
    @PostMapping("/byPoint")
    public Result getWaterByPoint(@RequestBody Map<String, Object> requestParams) {
        TbPointtransactionrecords pointreduce = new TbPointtransactionrecords();
        String appid = (String) requestParams.get("Appid");
        String deviceid = (String) requestParams.get("deviceid");
        String saler = (String) requestParams.get("saler");
        String url = "api.happy-ti.com:2028/device/getdetail?appid="+appid+"&saler="+saler+"&deviceId="+deviceid;
        String result= HttpUtil.get(url);
        String location = extractOpenId(result,"location");
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("openid",appid);
        List<TbUser> tbUserList=tbUserService.listByMap(userMap);
        TbUser user = tbUserList.isEmpty()? null : tbUserList.get(0);
        assert user != null;
        Integer point = user.getPointbalance();
        double wateramount = (double) requestParams.get("water_amount");
        Map<String,Object> packageMap = new HashMap<>();
        packageMap.put("water_amount",wateramount);
        List<Package> packageList =packageService.listByMap(packageMap);
        Package pg = packageList.isEmpty()? null : packageList.get(0);
//        TbPointrules tbPointrules = tbPointrulesService.getById(1);
//        Integer reducePoint = tbPointrules.getReducePoint();
//        Integer exchangeWater = tbPointrules.getExchangeWater();
        assert pg != null;
        if (point < pg.getPoints()) {
            return pointNotEnough();
        } else {
            Integer userid = user.getId();
            user.setPointbalance(point - pg.getPoints());
            pointreduce.setUserid(userid);
            pointreduce.setIncomeOrExpenseType(-1);
            pointreduce.setDeviceId(deviceid);
            pointreduce.setPointValue(pg.getPoints());
            pointreduce.setUserName(user.getName());
            String value = Double.toString(pg.getWaterAmount());
            String ch = "";
            tbUserService.updateById(user);
            tbPointtransactionrecordsService.save(pointreduce);
            Integer salerOrderIdInt = generateRandomNumber();
            String salerOrderId = String.valueOf(salerOrderIdInt);
            String useridStr = String.valueOf(userid);
            String url1 = "api.happy-ti.com:2028/trade/qrcreate?appid=" + appid + "&saler=" + saler + "&deviceId=" + deviceid + "&value"
                    + value + "&userid" + useridStr + "&ch" + ch + "&location" + location + "&salerOrderId" + salerOrderId;
            String result1 = HttpUtil.get(url1);
            if(result1==null){
                return failOrder();
            }
            String url2 = "api.happy-ti.com:2028/trade/query?saler=" + saler + "&appid=" + appid + "&deviceId" + deviceid +"&salerOrderId=" + salerOrderId;
            String result2 = HttpUtil.get(url2);
            JSONObject jsonObject1 = new JSONObject(result2);
            String code = jsonObject1.getStr("code");
            if(!Objects.equals(code, "1")){
                return failNotifyDevice();
            }
        }
        return success();
    }
    /**
     * 零钱打水
     */
    @PostMapping("/byPocketmoney")
    public Result getWaterByPocketMoney(@RequestBody Map<String, Object> requestParams) {
//        String productDesc = (String) requestParams.get("productDesc");
//        double amount = (double) requestParams.get("amount");
//        // 商户号
//        private String merchantId;
//        // 商户证书私钥文件路径
//        private String privateKeyPath;
//        public Map<String, String> createOrder(String productDesc, double amount) {
//            // 构建签名器
//            Signer signer = buildSigner();
//            // 准备参数
//            Map<String, String> params = new HashMap<>();
//            params.put("body", productDesc);
//            params.put("out_trade_no", generateOrderId());
//            params.put("total_fee", String.valueOf((int) (amount * 100)));
//            params.put("spbill_create_ip", "127.0.0.1");
//            // 调用统一下单接口获取 prepay_id 等
//            // 这里需要根据微信支付 SDK 具体实现
//            return null;
//        }
//        private Signer buildSigner() {
//            try {
//                File privateKeyFile = new File(privateKeyPath);
//                String privateKey = new String(PemUtil.loadPem(new FileReader(privateKeyFile)));
//                return new PrivateKeySigner(merchantId, privateKey);
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException("构建签名器出错");
//            }
//        }
//        private String generateOrderId() {
//            // 生成订单号逻辑
//            return "order123";
//        }
        String appid = (String) requestParams.get("Appid");
        String deviceid = (String) requestParams.get("id");
        String saler = (String) requestParams.get("saler");
        String url = "api.happy-ti.com:2028/device/getdetail?appid="+appid+"&saler="+saler+"&deviceId="+deviceid;
        String result= HttpUtil.get(url);
        String location = extractOpenId(result,"location");
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("openid",appid);
        List<TbUser> tbUserList=tbUserService.listByMap(userMap);
        TbUser user = tbUserList.isEmpty()? null : tbUserList.get(0);
        assert user != null;
//        Integer value = (Integer) requestParams.get("value");
        Integer value = (Integer) 1;
        Integer userid = user.getId();
        String ch = "";
        Integer salerOrderIdInt = generateRandomNumber();
        String salerOrderId = String.valueOf(salerOrderIdInt);
        String url1 = "api.happy-ti.com:2028/trade/qrcreate?appid=" + appid + "&saler=" + saler + "&deviceId=" + deviceid + "&value"
                + value + "&userid" + userid + "&ch" + ch + "&location" + location + "&salerOrderId" + salerOrderId;
        String result1 = HttpUtil.get(url1);
        if(result1==null){
            return failOrder();
        }
        String url2 = "api.happy-ti.com:2028/trade/query?saler=" + saler + "&appid=" + appid + "&deviceId" + deviceid +"&salerOrderId=" + salerOrderId;
        String result2 = HttpUtil.get(url2);
        JSONObject jsonObject1 = new JSONObject(result2);
        String code = jsonObject1.getStr("code");
        if(!Objects.equals(code, "1")){
            return failNotifyDevice();
        }
        return null;
    }
    public static Integer generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(1000000000); // 这里可以根据需要调整随机数的范围
    }
}
