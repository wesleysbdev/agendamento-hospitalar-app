package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.consumer;

import br.com.fiap.agendamento.notificacao.application.dto.NotificacaoConsultaDTO;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter.EmailAdapter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {
    private final EmailAdapter emailAdapter;

    public EmailConsumer(EmailAdapter emailAdapter) {
        this.emailAdapter = emailAdapter;
    }

    @RabbitListener(queues = "email.queue")
    public void receber(NotificacaoConsultaDTO notificacao) {
        emailAdapter.enviar(notificacao);
    }
}
