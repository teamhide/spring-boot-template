package com.teamhide.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
            "com.teamhide.template.api",
            "com.teamhide.template.application",
            "com.teamhide.template.domain",
            "com.teamhide.template.core",
            "com.teamhide.template.infra",
            "com.teamhide.template.persistence",
        })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
