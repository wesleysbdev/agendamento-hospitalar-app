package br.com.fiap.agendamento.notificacao.infrastructure.config;

import br.com.fiap.agendamento.notificacao.application.notificacao.ports.in.GestaoNotificacao;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.EnvioNotificacao;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.NotificacaoRepository;
import br.com.fiap.agendamento.notificacao.application.notificacao.usecases.EnvioNotificacaoUseCase;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter.EmailAdapter;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter.PushAdapter;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter.SMSAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificacaoUseCaseConfig {

    @Bean
    public GestaoNotificacao gestaoNotificacao(NotificacaoRepository repository, EnvioNotificacao envioNotificacao) {
        return new EnvioNotificacaoUseCase(repository);
    }

    @Bean
    public EnvioNotificacao envioNotificacao() {
        return new SMSAdapter();
    }

    @Bean
    public EnvioNotificacao envioNotificacaoPush() {
        return new PushAdapter();
    }

    @Bean
    public EnvioNotificacao envioNotificacaoEmail() {
        return new EmailAdapter();
    }
}
