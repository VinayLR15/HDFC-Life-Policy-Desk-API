package com.hdfclife;

import com.hdfclife.config.HdfcProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(HdfcProperties.class)
public class HdfcLifePolicyDeskApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                HdfcLifePolicyDeskApiApplication.class,
                args
        );
    }
}