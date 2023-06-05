package br.com.scheronlini.namingserveradm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NamingServerAdmApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamingServerAdmApplication.class, args);
	}

}
