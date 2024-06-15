package com.waterstation.waterstation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.TbAdmin;
import com.waterstation.waterstation.service.TbAdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;


/**
 * 对管理员信息的增删改查
 * @author Administrator
 */
@RestController
@Controller
@RequestMapping("/tb-admin")
public class TbAdminController {

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
            String adminPasswordFilter,
            String permissionFilter,
            String addTimeFilter,
            String phoneNumberFilter,
            String genderFilter) {
        QueryWrapper<TbAdmin> queryWrapper = new QueryWrapper<>();
        if (idFilter != null) {
            queryWrapper.eq("id", idFilter);
        }
        if (adminNameFilter != null) {
            queryWrapper.like("admin_name", adminNameFilter);
        }
        if (adminPasswordFilter != null) {
            queryWrapper.eq("admin_password", adminPasswordFilter);
        }
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
        return tbAdminService.list(queryWrapper);
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
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("phone_number",phoneNumber);
            List<TbAdmin> tbAdmin = tbAdminService.listByMap(paramMap);
            if(tbAdmin.isEmpty()){
                return Result.fail("002","未查询到用户",null);
            }
            TbAdmin tbAdmin1 = tbAdmin.get(0);
            if(password.isEmpty()){
                return  Result.fail("003","密码为空",null);
            }
            if(tbAdmin1.getAdminPassword().equals(password)){
                return Result.success("登录成功",tbAdmin1);
            }else{
                return Result.fail("004","密码错误",null);
            }

        }else{
            return Result.fail("001","号码为空",null);
        }

//        return login(phoneNumber, password);
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
}
