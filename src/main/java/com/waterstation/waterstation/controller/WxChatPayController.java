package com.waterstation.waterstation.controller;

import cn.hutool.json.JSONObject;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.TbChangeFlow;
import com.waterstation.waterstation.service.TbChangeFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/order")

public class WxChatPayController {
        @Autowired
        private TbChangeFlowService tbChangeFlowService;
//        @Value("${wxpay.mchid}")
//        private String mchid;
//        @Value("${wxpay.key}")
//        private String key;
        public Result createTbChangeFlow(TbChangeFlow tbChangeFlow){
                tbChangeFlow.setId(null);
                tbChangeFlow.setPrice(tbChangeFlow.getPrice());
                tbChangeFlow.setUserid(tbChangeFlow.getUserid());
                tbChangeFlow.setPayTime(null);
                tbChangeFlow.setPay(false);
                tbChangeFlow.setCreateTime(LocalDateTime.now());
                tbChangeFlow.setOrderSn(UUID.randomUUID().toString());
                JSONObject jsonobject = null;
//                        WxPay.minAppPay(tbChangeFlow.getOrderSn(),""
//                                + tbChangeFlow.getPrice(),mchid,"购买课件ID为: " + tbChangeFlow.getcwId(),"码神课件",
//                        nu11,"http://a4tuaki.nat.ipyingshe.com/cwApi/order/callback", nu11,"O",nu1l,key);
                return Result.success("预下单成功",jsonobject);
        }
        @PostMapping("/create")
        public Result createorder(@RequestBody TbChangeFlow tbChangeFlow) {
            return createTbChangeFlow(tbChangeFlow);
        }
}

