package com.waterstation.waterstation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.waterstation.waterstation.entity.TbQrcode;
import com.waterstation.waterstation.service.TbQrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    public List<TbQrcode> list(){return tbQrcodeService.list();}

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
//

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
        return tbQrcodeService.save(tbQrcode);
    }

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

    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody TbQrcode tbQrcode){
        return tbQrcodeService.saveOrUpdate(tbQrcode);
    }

    @GetMapping("/delete")
    public boolean delete(String id){
        return tbQrcodeService.removeById(id);
    }

}
