package com.waterstation.waterstation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.TbGroup;
import com.waterstation.waterstation.entity.TbQrcode;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.TbUserService;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjj
 * @since 2024-05-10
 */
@RestController
@RequestMapping("/tb-user")
public class TbUserController {

    @Autowired
    private TbUserService tbUserService;
//    @GetMapping("/list")
//    public List<TbUser> list(){return tbUserService.list();}

    @GetMapping("/list")
    public List<TbUser> list(
            String idFilter,
            String openidFilter,
            String profilePhotoFilter,
            String phoneFilter,
            String roleFilter,
            BigDecimal nameFilter,
            String pointbalanceFilter,
            String taskCountFilter,
            String groupIdFilter) {
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        if (idFilter != null) {
            queryWrapper.eq("id", idFilter);
        }
        if (openidFilter != null) {
            queryWrapper.like("openid", openidFilter);
        }
        if (profilePhotoFilter != null) {
            queryWrapper.eq("profile_photo", profilePhotoFilter);
        }
        if (phoneFilter != null) {
            queryWrapper.eq("phone", phoneFilter);
        }
        if (roleFilter != null) {
            queryWrapper.eq("saler", roleFilter);
        }
        if (nameFilter != null) {
            queryWrapper.eq("name", nameFilter);
        }
        if (pointbalanceFilter != null) {
            queryWrapper.eq("pointbalance", pointbalanceFilter);
        }
        if (taskCountFilter != null) {
            queryWrapper.eq("task_count", taskCountFilter);
        }
        if (groupIdFilter != null) {
            queryWrapper.eq("group_id", groupIdFilter);
        }
        return tbUserService.list(queryWrapper);
    }

    @PostMapping("/listByOpenid")
    public List<TbUser> listBy(@RequestBody Map<String, Object> requestParams){
        return tbUserService.listByMap(requestParams);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody TbUser tbUser){
        return tbUserService.save(tbUser);
    }

    @PostMapping("/mod")
    public boolean mod(@RequestBody Map<String, Object> requestParams){
        Integer id = (Integer) requestParams.get("id");
        String groupId = (String) requestParams.get("groupId");
        Integer groupIdInt = null;
        if(groupId!=null){
            groupIdInt = Integer.parseInt(groupId);
        }
        TbUser tbUser = tbUserService.getById(id);
        UpdateWrapper<TbUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",tbUser.getId());
//        updateWrapper.set("openid", tbUser.getOpenid());
//        updateWrapper.set("phone", tbUser.getPhone());
//        updateWrapper.set("name", tbUser.getName());
//        updateWrapper.set("pointbalance", tbUser.getPointbalance());
//        updateWrapper.set("groupId", tbUser.getProfilePhoto());
//        updateWrapper.set("groupId", tbUser.getTaskCount());
//        updateWrapper.set("groupId", tbUser.getRole());
        updateWrapper.set("group_id", groupIdInt == null? null : tbUser.getGroupId());

        return tbUserService.update(updateWrapper);
    }

    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody TbUser tbUser){
        return tbUserService.saveOrUpdate(tbUser);
    }

    @GetMapping("/delete")
    public boolean delete(Integer id){
        return tbUserService.removeById(id);
    }
    @PostMapping("/UploadAvatarServlet")
    public String uploadAvatarServlet(@RequestBody Map<String, Object> requestParams) {
        // 获取用户标识参数
//        String userId = (String) requestParams.get("userid");;
//
//        // 获取上传的文件部分
//        Part filePart = (Part) requestParams.get("avatar");
        String openid = (String) requestParams.get("openid");
        Base64 avatar = (Base64) requestParams.get("avatar");
        // 对 base64 编码数据进行解码为字节数组
        byte[] avatarBytes = Base64.getDecoder().decode(avatar.toString());
        // 获取文件名
//        String fileName = filePart.getOriginalFilename();
        String fileName = "avatar.jpg";

        // 生成唯一的文件名（防止重名覆盖）
        String uniqueFileName = UUID.randomUUID() + "_" + fileName;

        // 指定存储路径
//        String savePath = "/mnt/Waterstation/qrcode/" + openId + "/" + uniqueFileName;
        String savePath ="E:/weiwu/hx_be/Waterstation/Avatar/" + openid + "/" + uniqueFileName;
        // 创建对应的用户文件夹（如果不存在）
//        File userFolder = new File("/mnt/Waterstation/qrcode/" + openId);
        File userFolder = new File("E:/weiwu/hx_be/Waterstation/Avatar/" + openid);
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }

        // 写入文件
//        try (InputStream inputStream = filePart.getInputStream();
        try (FileOutputStream outputStream = new FileOutputStream(new File(savePath))) {
//            filePart.transferTo(new File(savePath));
            outputStream.write(avatarBytes);
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer))!= -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "上传成功，文件地址：" + savePath;
    }
}
