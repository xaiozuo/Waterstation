package com.waterstation.waterstation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.waterstation.waterstation.mapper")
public class WaterstationApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaterstationApplication.class, args);
    }

}
