package com.challenge.secretnotepad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // (scanBasePackages = "com.challenge.shared")
public class SecretNotepadApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecretNotepadApplication.class, args);
    }

}
