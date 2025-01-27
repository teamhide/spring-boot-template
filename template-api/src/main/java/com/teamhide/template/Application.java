package com.teamhide.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
            "com.teamhide.template.api",
            "com.teamhide.template.application",
            "com.teamhide.template.domain",
            "com.teamhide.template.core",
            "com.teamhide.template.persistence",
            "com.teamhide.template.clients",
        })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
