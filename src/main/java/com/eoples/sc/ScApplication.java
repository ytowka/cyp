package com.eoples.sc;

import com.eoples.sc.models.Phone;
import com.eoples.sc.repo.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ScApplication {
	private static final String getPhoneCommand = "getPhone";


	public static void main(String[] args) {
		SpringApplication.run(ScApplication.class, args);
	}

}
