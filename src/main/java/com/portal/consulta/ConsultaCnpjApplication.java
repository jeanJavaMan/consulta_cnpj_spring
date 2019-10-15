package com.portal.consulta;

import com.portal.consulta.controller.IndexController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.jeanderson.consulta", "controller"})
public class ConsultaCnpjApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultaCnpjApplication.class, args);
    }
}
