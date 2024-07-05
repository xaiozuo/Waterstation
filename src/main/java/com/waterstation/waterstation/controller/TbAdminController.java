package com.waterstation.waterstation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.TbAdmin;
import com.waterstation.waterstation.service.TbAdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * 对管理员信息的增删改查
 * @author Administrator
 */
@RestController
@Controller
@RequestMapping("/tb-admin")
public class TbAdminController {

    // 假设超级管理员的用户 ID
    private static final String SUPER_ADMIN_ID = "18888888888";

    @Autowired
    private TbAdminService tbAdminService;

//    @GetMapping("/list")
//    public List<TbAdmin> list(){
//        return tbAdminService.list();
//    }
    @GetMapping("/list")
    public List<TbAdmin> list(
            Integer idFilter,
            String adminNameFilter,
//            String adminPasswordFilter,
            String permissionFilter,
            String addTimeFilter,
            String phoneNumberFilter,
            String genderFilter) throws InvocationTargetException, IllegalAccessException {
        QueryWrapper<TbAdmin> queryWrapper = new QueryWrapper<>();
        if (idFilter != null) {
            queryWrapper.eq("id", idFilter);
        }
        if (adminNameFilter != null) {
            queryWrapper.like("admin_name", adminNameFilter);
        }
//        if (adminPasswordFilter != null) {
//            queryWrapper.eq("admin_password", adminPasswordFilter);
//        }
        if (permissionFilter != null) {
            queryWrapper.eq("permission", permissionFilter);
        }
        if (addTimeFilter != null) {
            queryWrapper.eq("add_time", addTimeFilter);
        }
        if (phoneNumberFilter != null) {
            queryWrapper.eq("phone_number", phoneNumberFilter);
        }
        if (genderFilter != null) {
            queryWrapper.eq("gender", genderFilter);
        }
        // 获取包含所有字段的列表
        List<TbAdmin> adminList = tbAdminService.list(queryWrapper);
        // 创建新的列表用于存储不包含密码字段的结果
        List<TbAdmin> resultList = new ArrayList<>();
        for (TbAdmin admin : adminList) {
            TbAdmin newAdmin = new TbAdmin();
            newAdmin.setId(admin.getId());
            newAdmin.setAdminName(admin.getAdminName());
            newAdmin.setPermission(admin.getPermission());
            newAdmin.setAddTime(admin.getAddTime());
            newAdmin.setPhoneNumber(admin.getPhoneNumber());
            newAdmin.setGender(admin.getGender());
            newAdmin.setAdminPassword(null);
            resultList.add(newAdmin);
        }
        return resultList;
    }

    //新增
    @PostMapping("/save")
    public boolean save(@RequestBody TbAdmin tbAdmin){
        return tbAdminService.save(tbAdmin);
    }

    @PostMapping("/mod")
    public boolean mod(@RequestBody TbAdmin tbAdmin){
        return tbAdminService.updateById(tbAdmin);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody TbAdmin tbAdmin){
        return tbAdminService.saveOrUpdate(tbAdmin);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return tbAdminService.removeById(id);
    }

    @PostMapping("/login")
    public Result adminlogin(HttpServletRequest request) {
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("adminPassword");
        if(phoneNumber!= null && !phoneNumber.isEmpty()){
//            HashMap<String, Object> paramMap = new HashMap<>();
//            paramMap.put("phone_number",phoneNumber);
//            List<TbAdmin> tbAdmin = tbAdminService.listByMap(paramMap);
//            if(tbAdmin.isEmpty()){
//                return Result.fail("002","未查询到用户",null);
//            }
//            TbAdmin tbAdmin1 = tbAdmin.get(0);
            TbAdmin tbAdmin1 = queryByphoneNumber(phoneNumber);
            if(tbAdmin1==null){
                return Result.fail("002","未查询到管理员",null);
            }
            if(password.isEmpty()){
                return  Result.fail("003","密码为空",null);
            }
            if(tbAdmin1.getAdminPassword().equals(password)){
                Map<String, Object> adminMap = objectToMap(tbAdmin1);
                adminMap.remove("adminPassword");  // 移除密码字段
                return Result.success("登录成功",adminMap);
            }else{
                return Result.fail("004","密码错误",null);
            }

        }else{
            return Result.fail("001","号码为空",null);
        }

//        return login(phoneNumber, password);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, Object> params) {
        // 从参数中获取用户标识、新密码和旧密码
        Object userIdObj = params.get("phoneNumber");
        Object newPasswordObj = params.get("newPassword");
        Object oldPasswordObj = params.get("oldPassword");

        if (userIdObj == null || newPasswordObj == null || oldPasswordObj == null) {
            return Result.fail("005","用户id或者密码不能为空",null);
        }

        String userId = userIdObj.toString();
        String newPassword = newPasswordObj.toString();
        String oldPassword = oldPasswordObj.toString();

        // 模拟检查旧密码是否正确
        if (!isOldPasswordCorrect(userId, oldPassword)) {
            return Result.fail("006","旧密码错误",null);
        }

        // 检查是否为超级管理员，超级管理员不让修改密码
        if (Objects.equals(userId, SUPER_ADMIN_ID)) {
            return Result.fail("007","超级管理员不允许修改密码",null);
        }

        // 假设这里将用户标识和新密码保存到数据库或进行其他修改密码的操作
        TbAdmin tbAdmin = queryByphoneNumber(userId);
        if (tbAdmin != null) {
            tbAdmin.setAdminPassword(newPassword);
        }
        if(tbAdminService.updateById(tbAdmin)){
            // 以下是模拟成功修改密码的返回
            return Result.success("密码修改成功！");
        }else{
            return Result.fail("008","数据库修改密码错误",null);
        }


    }

    // 模拟检查旧密码是否正确的方法
    private boolean isOldPasswordCorrect(String userId, String oldPassword) {
        // 在此处实现检查旧密码是否正确的逻辑，例如与数据库中存储的密码进行比较
        TbAdmin tbAdmin = queryByphoneNumber(userId);
        if(tbAdmin==null){
            return false;
        }
        // 这里简单模拟返回 true
        if(Objects.equals(oldPassword, tbAdmin.getAdminPassword())){
            return true;
        }else{
            return false;
        }
    }
    private TbAdmin queryByphoneNumber(String phoneNumber){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("phone_number",phoneNumber);
        List<TbAdmin> tbAdmin = tbAdminService.listByMap(paramMap);
        if(tbAdmin.isEmpty()){
            return null;
        }
        return tbAdmin.get(0);
    }

//    public boolean login(String phoneNumber, String password) {
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("phone_number",phoneNumber);
//        List<TbAdmin> tbAdmin = tbAdminService.listByMap(paramMap);
//        TbAdmin tbAdmin1 = tbAdmin.isEmpty()? null : tbAdmin.get(0);
//        return (tbAdmin1 != null && tbAdmin1.getAdminPassword().equals(password));
//    }


//    public static void main(String[] args) {
//        String username = "user1";
//        String password = "pass123";
//
//        // 登录验证并处理加密存储
//        if (loginAndEncrypt(username, password)) {
//            System.out.println("登录成功并加密存储密码完成");
//        } else {
//            System.out.println("登录失败");
//        }
//    }
    // 用于将字符串转换为 MD5 哈希值
//    public static String md5(String str) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] bytes = md.digest(str.getBytes());
//
//            StringBuilder sb = new StringBuilder();
//            for (byte b : bytes) {
//                sb.append(String.format("%02x", b));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    public static boolean loginAndEncrypt(String username, String password) {
//        // 假设已经建立好数据库连接
//        try (Connection connection = DriverManager.getConnection("your_database_connection_url")) {
//
//            // 先检查用户是否存在
//            String checkUserQuery = "SELECT * FROM users WHERE username =?";
//            PreparedStatement checkUserStmt = connection.prepareStatement(checkUserQuery);
//            checkUserStmt.setString(1, username);
//
//            ResultSet resultSet = checkUserStmt.executeQuery();
//
//            if (resultSet.next()) {
//                // 用户存在，获取存储的加密密码并与输入的加密后密码比较
//                String storedEncryptedPassword = resultSet.getString("password");
//
//                String encryptedInputPassword = md5(password);
//
//                if (storedEncryptedPassword.equals(encryptedInputPassword)) {
//                    // 登录成功，更新密码（可选，若需要重新加密存储）
//                    String updatePasswordQuery = "UPDATE users SET password =? WHERE username =?";
//                    PreparedStatement updateStmt = connection.prepareStatement(updatePasswordQuery);
//                    updateStmt.setString(1, encryptedInputPassword);
//                    updateStmt.setString(2, username);
//
//                    int rowsUpdated = updateStmt.executeUpdate();
//
//                    return rowsUpdated > 0;
//                } else {
//                    return false;
//                }
//            } else {
//                // 用户不存在
//                return false;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
public static Map<String, Object> objectToMap(Object obj) {
    Map<String, Object> map = new HashMap<>();
    Class<?> clazz = obj.getClass();
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
        field.setAccessible(true);
        try {
            map.put(field.getName(), field.get(obj));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    return map;
}
}
