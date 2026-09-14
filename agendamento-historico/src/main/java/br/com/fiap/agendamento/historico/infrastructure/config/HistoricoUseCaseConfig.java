package br.com.fiap.agendamento.historico.infrastructure.config;

import br.com.fiap.agendamento.historico.application.historico.ports.in.ConsultaHistorico;
import br.com.fiap.agendamento.historico.application.historico.ports.out.HistoricoRepository;
import br.com.fiap.agendamento.historico.application.historico.usecases.ConsultaHistoricoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoricoUseCaseConfig {

    @Bean
    public ConsultaHistorico consultaHistorico(HistoricoRepository historicoRepository) {
        return new ConsultaHistoricoUseCase(historicoRepository);
    }
}
