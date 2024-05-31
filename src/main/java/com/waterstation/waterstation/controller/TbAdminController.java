package com.waterstation.waterstation.controller;


import com.waterstation.waterstation.entity.TbAdmin;
import com.waterstation.waterstation.service.TbAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/list")
    public List<TbAdmin> list(){
        return tbAdminService.list();
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
