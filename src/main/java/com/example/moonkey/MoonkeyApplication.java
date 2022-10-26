package com.example.moonkey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class MoonkeyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoonkeyApplication.class, args);
    }

}
