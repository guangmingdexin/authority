package com.authority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author guangmingdexin
 */
@SpringBootApplication
public class AuthorityApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthorityApplication.class, args);
    }

}
