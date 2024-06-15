package com.waterstation.waterstation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.waterstation.waterstation.entity.TbPointtransactionrecords;
import com.waterstation.waterstation.entity.TbQrcode;
import com.waterstation.waterstation.service.TbQrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.waterstation.waterstation.common.QRCodeGenerator.generateQrCode;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjj
 * @since 2024-05-10
 */
@RestController
@RequestMapping("/tb-qrcode")
public class TbQrcodeController {

    @Autowired
    private TbQrcodeService tbQrcodeService;

    /**
     * 查询设备二维码信息
     */
//    @GetMapping("/list")
//    public List<TbQrcode> list(){return tbQrcodeService.list();}
    @GetMapping("/list")
    public List<TbQrcode> list(
            String idFilter,
            String chaddressFilter,
            String imgnameFilter,
            String codeaddressFilter,
            String salerFilter,
            BigDecimal outletFilter,
            String appidFilter,
            String deviceNameFilter) {
        QueryWrapper<TbQrcode> queryWrapper = new QueryWrapper<>();
        if (idFilter != null) {
            queryWrapper.eq("id", idFilter);
        }
        if (chaddressFilter != null) {
            queryWrapper.like("chaddress", chaddressFilter);
        }
        if (imgnameFilter != null) {
            queryWrapper.eq("imgname", imgnameFilter);
        }
        if (codeaddressFilter != null) {
            queryWrapper.eq("codeaddress", codeaddressFilter);
        }
        if (salerFilter != null) {
            queryWrapper.eq("saler", salerFilter);
        }
        if (appidFilter != null) {
            queryWrapper.eq("appid", appidFilter);
        }
        if (outletFilter != null) {
            queryWrapper.eq("outlet", outletFilter);
        }
        if (deviceNameFilter != null) {
            queryWrapper.eq("device_name", deviceNameFilter);
        }

        return tbQrcodeService.list(queryWrapper);
    }

    /**
     * 生成二维码并且保存信息
     */
    @PostMapping("/save")
    public boolean save(@RequestBody TbQrcode tbQrcode){
        String appId = tbQrcode.getAppid();
        String id = tbQrcode.getId();
        String saler = tbQrcode.getSaler();
        String combinedData = appId + id + saler;
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("appId", appId);
        jsonData.put("id", id);
        jsonData.put("saler", saler);
//        String filePath = "D:/phpstudy_pro/WWW/waterStation/";
        String filePath = "/mnt/Waterstation/qrcode/";

        tbQrcode.setCodeaddress(filePath);
        String imgName = combinedData + ".png";
        tbQrcode.setImgname(imgName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(jsonData);
            generateQrCode(json,filePath + imgName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbQrcodeService.save(tbQrcode);
    }

    /**
     * 修改设备信息并且生成新的二维码
     */
    @PostMapping("/mod")
    public boolean mod(@RequestBody TbQrcode tbQrcode){

        String appId = tbQrcode.getAppid();
        String id = tbQrcode.getId();
        String saler = tbQrcode.getSaler();
        String combinedData = appId + id + saler;
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("appId", appId);
        jsonData.put("id", id);
        jsonData.put("saler", saler);
//        String filePath = "D:/phpstudy_pro/WWW/waterStation/";
        String filePath = "/mnt/Waterstation/qrcode/";
        tbQrcode.setCodeaddress(filePath);
        String imgName = combinedData + ".jpg";
        tbQrcode.setImgname(imgName);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(jsonData);
            generateQrCode(json,filePath + imgName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbQrcodeService.updateById(tbQrcode);
    }

    @GetMapping("/delete")
    public boolean delete(String id){
        return tbQrcodeService.removeById(id);
    }

}
