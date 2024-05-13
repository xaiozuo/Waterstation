package com.waterstation.waterstation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.mapper.TbUserMapper;
import com.waterstation.waterstation.service.TbUserService;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjj
 * @since 2024-05-10
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {
    @Override
    public TbUser getByOpenid(String openid) {
        TbUser user = new TbUser();
        // 发送 GET 请求
        try {
            String url = "http://localhost:8788/tb-user/listByOpenid?openid=" + openid;
            URI uri = URI.create(url);
            HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            // 检查响应码
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine())!= null) {
                    response.append(inputLine);
                }
                in.close();

                // 解析 JSON 响应
                Gson gson = new Gson();
                user = gson.fromJson(response.toString(), TbUser.class);
                return user;
            } else {
                System.out.println("请求失败，响应码: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
