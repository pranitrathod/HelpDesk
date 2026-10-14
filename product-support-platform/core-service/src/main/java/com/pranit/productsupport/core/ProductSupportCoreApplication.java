package com.pranit.productsupport.core;
import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class ProductSupportCoreApplication { public static void main(String[] args) { SpringApplication.run(ProductSupportCoreApplication.class, args); } }
