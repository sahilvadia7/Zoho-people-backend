package com.zoho.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZohoOrganizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZohoOrganizationApplication.class, args);
	}

}
