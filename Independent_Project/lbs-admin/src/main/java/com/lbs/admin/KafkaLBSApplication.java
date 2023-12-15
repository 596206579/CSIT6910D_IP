package com.lbs.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Eric Xue
 * @date 2021/05/14
 */
@SpringBootApplication(scanBasePackages = "com.lbs")
public class KafkaLBSApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaLBSApplication.class, args);
    }
}
