package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.consumer;

import br.com.fiap.agendamento.notificacao.application.dto.NotificacaoConsultaDTO;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter.PushAdapter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PushConsumer {

    private final PushAdapter pushAdapter;

    public PushConsumer(PushAdapter pushAdapter) {
        this.pushAdapter = pushAdapter;
    }

    @RabbitListener(queues = "push.queue")
    public void consumir(NotificacaoConsultaDTO notificacao) {
        pushAdapter.enviar(notificacao);
    }
}
