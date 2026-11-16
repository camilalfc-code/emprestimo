package com.projeto.emprestimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.projeto")
@EntityScan("com.projeto")
@EnableJpaRepositories("com.projeto")
public class EmprestimoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmprestimoApplication.class, args);
	}

}

/*
você precisou acrescentar o scanBasePackages, o @EntityScan e o @EnableJpaRepositories na EmprestimoApplication
por causa do pacote AtividadeSpring ficar ao lado do emprestimo.
Isso mostra que você entendeu por que deu 404 e como resolveu.
 */