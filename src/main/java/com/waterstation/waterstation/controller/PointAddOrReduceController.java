package com.waterstation.waterstation.controller;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.TbPointrules;
import com.waterstation.waterstation.entity.TbPointtransactionrecords;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.TbPointrulesService;
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
    @Autowired
    private TbPointrulesService tbPointrulesService;
    @PostMapping("/add")
    public Result incrementPointBalance(@RequestBody Map<String, Object> requestParams) {
        Integer userId = (Integer) requestParams.get("userId");
        Integer id = userId;
        String userName = (String) requestParams.get("userName");
        Integer type = (Integer) 1;
        TbPointrules tbPointrules = tbPointrulesService.getById(1);
        Integer taskCount = tbPointrules.getCounts();
        Integer pointValue = tbPointrules.getIncreasepoint();
        String pointChangeType = (String) requestParams.get("pointChangeType");
        String deviceId = (String) requestParams.get("deviceId");
        Integer outlet= (Integer) requestParams.get("outlet");
        String advertisementLink = (String) requestParams.get("advertisementLink");
//        LocalDateTime transactionTime = (LocalDateTime ) requestParams.get("transaction_time");
        TbUser tbuser = tbUserService.getById(id);
        TbPointtransactionrecords point =new TbPointtransactionrecords();
//        point.setId(id);
        point.setUserid(userId);
        point.setUserName(userName);
        point.setIncomeOrExpenseType(type);
        point.setPointValue(pointValue);
        point.setPointChangeType(pointChangeType);
        point.setDeviceId(deviceId);
        point.setOutlet(outlet);
        point.setAdvertisementLink(advertisementLink);
        tbuser.setId(userId);
        if(tbuser.getTaskCount()<taskCount){
            tbuser.setTaskCount(tbuser.getTaskCount()+1);
        }else {
            return taskCountFull();
        }
        tbuser.setPointbalance(tbuser.getPointbalance() + pointValue);
        if(tbUserService.updateById(tbuser)&&tbPointtransactionrecordsService.save(point)){
            return addPointSuccess();
        }
        return addPointFail();
    }

}
