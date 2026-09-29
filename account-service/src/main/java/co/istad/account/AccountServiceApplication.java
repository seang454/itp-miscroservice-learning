package co.istad.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(
//			@Value("${service.name}") String name,
//			@Value("${secret.weak-password}") String weakPassword,
//			@Value("${secret.strong-password}") String strongPassword
//			){
//		return args -> {
//			System.out.println("Name "+name);
//			System.out.println("Weak Password "+weakPassword);
//			System.out.println("Strong Password "+strongPassword);
//		};
//
//	}

}
