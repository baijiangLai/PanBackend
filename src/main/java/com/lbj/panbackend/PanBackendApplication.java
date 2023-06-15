package com.lbj.panbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.lbj.panbackend"})
@MapperScan(basePackages = {"com.lbj.panbackend.mappers"})
@EnableTransactionManagement
@EnableScheduling
public class PanBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanBackendApplication.class, args);
    }

}
