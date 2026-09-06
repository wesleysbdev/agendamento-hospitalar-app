package br.com.fiap.agendamento.notificacao.infrastructure.config;

import br.com.fiap.agendamento.notificacao.application.notificacao.ports.in.GestaoNotificacao;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.EnvioNotificacao;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.NotificacaoRepository;
import br.com.fiap.agendamento.notificacao.application.notificacao.usecases.EnvioNotificacaoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GestaoNotificacao gestaoNotificacao(NotificacaoRepository repository, EnvioNotificacao envioNotificacao) {
        return new EnvioNotificacaoUseCase(repository, envioNotificacao);
    }
}
