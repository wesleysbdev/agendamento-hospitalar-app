package br.com.fiap.agendamento.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.fiap.agendamento"})
@ComponentScan(basePackages = {"br.com.fiap.agendamento"})
@EnableJpaRepositories(basePackages = "br.com.fiap.agendamento")
public class AgendamentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgendamentoApplication.class, args);
    }

}