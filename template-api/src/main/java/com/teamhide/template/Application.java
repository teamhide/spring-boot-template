package com.teamhide.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
            "com.teamhide.template.api",
            "com.teamhide.template.application",
            "com.teamhide.template.domain",
            "com.teamhide.template.core",
        })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
