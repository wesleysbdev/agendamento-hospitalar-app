package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.persistence.consumer;

import br.com.fiap.agendamento.notificacao.application.dto.NotificacaoConsultaDTO;
import br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter.SMSAdapter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SMSConsumer {

    private final SMSAdapter smsAdapter;

    public SMSConsumer(SMSAdapter smsAdapter) {
        this.smsAdapter = smsAdapter;
    }

    @RabbitListener(queues = "sms.queue")
    public void receber(NotificacaoConsultaDTO notificacao) {
        smsAdapter.enviar(notificacao);
    }
}
