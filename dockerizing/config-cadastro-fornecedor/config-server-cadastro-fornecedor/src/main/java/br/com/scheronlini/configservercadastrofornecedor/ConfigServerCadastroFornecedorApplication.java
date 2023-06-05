package br.com.scheronlini.configservercadastrofornecedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerCadastroFornecedorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerCadastroFornecedorApplication.class, args);
	}

}
