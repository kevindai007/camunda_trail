package com.kevindai.base.camunda_trail;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableProcessApplication
@SpringBootApplication
public class CamundaTrailApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaTrailApplication.class, args);
    }

}
