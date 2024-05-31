package com.waterstation.waterstation.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @author Administrator
 */
@EnableScheduling
@Component
public class TaskCountResetTask {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Scheduled(cron = "0 0 0 * * *")
    public void resetTaskCount() {jdbc:postgresql://localhost:5432/waterstation
//        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/waterstation", "postgres", "12345678");
    try (Connection connection = DriverManager.getConnection(url, username, password);
         PreparedStatement statement = connection.prepareStatement("UPDATE tb_user SET task_count = 0")) {
        statement.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}

