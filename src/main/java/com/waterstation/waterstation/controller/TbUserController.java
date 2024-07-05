package com.waterstation.waterstation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.TbUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Result save(@RequestBody TbUser tbUser){
        if(tbUserService.save(tbUser)){
            return Result.success("添加用户信息成功",tbUser);
        }
        return Result.fail("添加用户信息失败");
    }

//    @PostMapping("/mod")
//    public boolean mod(@RequestBody Map<String, Object> requestParams){
//        Integer id = (Integer) requestParams.get("id");
//        String openid = (String) requestParams.get("openid");
//        String phone = (String) requestParams.get("phone");
//        String name = (String) requestParams.get("name");
//        Integer pointbalance = (Integer) requestParams.get("pointbalance");
//        Integer taskCount = (Integer) requestParams.get("taskCount");
//        Integer role = (Integer) requestParams.get("role");
//        String groupId = (String) requestParams.get("groupId");
//        TbUser tbUser = tbUserService.getById(id);
//        UpdateWrapper<TbUser> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("id",tbUser.getId());
//        if(groupId !=null ){
//            Integer groupIdInt = null;
//            if(!"null".equals(groupId)){
//                groupIdInt = Integer.parseInt(groupId);
//            }
//            updateWrapper.set("group_id",groupIdInt);
//        }
//        if(name!=null){  // 新增对 name 的处理
//            updateWrapper.set("name", name);
//        }
//        if(name!=null){  // 新增对 name 的处理
//            updateWrapper.set("name", name);
//        }
//        return tbUserService.update(updateWrapper);
//    }
    @PostMapping("/mod")
    public boolean mod(@RequestBody Map<String, Object> requestParams){
        Integer id = (Integer) requestParams.get("id");
        String openid = (String) requestParams.get("openid");
        String phone = (String) requestParams.get("phone");
        String name = (String) requestParams.get("name");
        String profilePhoto = (String) requestParams.get("profilePhoto");
        Integer pointbalance = (Integer) requestParams.get("pointbalance");
        Integer taskCount = (Integer) requestParams.get("taskCount");
        Integer role = (Integer) requestParams.get("role");
        boolean hasGroupId = requestParams.containsKey("groupId");
        String groupId = (String) requestParams.get("groupId");
        Integer groupIdInt = null;
        if(groupId!=null){
            groupIdInt = Integer.parseInt(groupId);
        }
        TbUser tbUser = tbUserService.getById(id);
        UpdateWrapper<TbUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",tbUser.getId());
        Integer group = groupIdInt;
        if(openid!=null){  // 新增对 name 的处理
            updateWrapper.set("openid", openid);
        }
        if(phone!=null){  // 新增对phone 的处理
            updateWrapper.set("phone", phone);
        }
        if(name!=null){  // 新增对 name 的处理
            updateWrapper.set("name", name);
        }
        if(profilePhoto!=null){  // 新增对 profilePhoto 的处理
            updateWrapper.set("profile_photo", profilePhoto);
        }
        if(pointbalance!=null){  // 新增对pointbalance 的处理
            updateWrapper.set("pointbalance", pointbalance);
        }
        if(taskCount!=null){  // 新增对 taskCount 的处理
            updateWrapper.set("task_count", taskCount);
        }
        if(role!=null){  // 新增对 role 的处理
            updateWrapper.set("role", role);
        }
        updateWrapper.set("group_id",hasGroupId ? (groupId==null ? null : group) : tbUser.getGroupId());

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

//    @ApiOperation(value = "上传base64格式文件", notes = "上传base64格式文件")
//    @PostMapping("uploadBase64Img")
//    public Result<?> uploadBase64Img(@RequestBody Base64Req base64Req) {
//        ValidatorUtils.validateEntity(base64Req);
//        return Results.newSuccessResult(fileService.uploadBase64(base64Req.getBase64Str()));
//    }

    @PostMapping("/UploadAvatarServlet")
    public String uploadAvatarServlet(@RequestBody Map<String, Object> requestParams) {
        // 获取用户标识参数
//        String userId = (String) requestParams.get("userid");;
//
//        // 获取上传的文件部分
//        Part filePart = (Part) requestParams.get("avatar");
        String openid = (String) requestParams.get("openid");
        String avatar = (String) requestParams.get("avatar");
        // 对 base64 编码数据进行解码为字节数组
        byte[] avatarBytes = Base64.getDecoder().decode(avatar);

        // 获取旧头像的路径
        String oldAvatarPath = "/mnt/Waterstation/qrcode/avatar/" + openid + ".jpg";
        File oldAvatarFile = new File(oldAvatarPath);

        // 如果旧头像存在，删除它
        if (oldAvatarFile.exists()) {
            oldAvatarFile.delete();
        }
        // 获取文件名
//        String fileName = filePart.getOriginalFilename();
        String fileName = openid + ".jpg";

//        // 生成唯一的文件名（防止重名覆盖）
//        String uniqueFileName = UUID.randomUUID() + "_" + fileName;

        // 指定存储路径
        String savePath = "/mnt/Waterstation/qrcode/avatar/" + fileName;
//        String savePath = "/mnt/Waterstation/qrcode/avatar/" + uniqueFileName;
//        String savePath ="E:/weiwu/hx_be/Waterstation/Avatar/" + openid + "/" + uniqueFileName;
        // 创建对应的用户文件夹（如果不存在）
        File userFolder = new File("/mnt/Waterstation/qrcode/avatar/");
//        File userFolder = new File("E:/weiwu/hx_be/Waterstation/Avatar/" + openid);
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

        return  fileName;
    }
}
