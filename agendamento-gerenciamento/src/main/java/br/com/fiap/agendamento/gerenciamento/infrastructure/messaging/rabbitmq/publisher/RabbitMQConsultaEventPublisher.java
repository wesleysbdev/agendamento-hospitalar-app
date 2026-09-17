package br.com.fiap.agendamento.gerenciamento.infrastructure.messaging.rabbitmq.publisher;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaEvent;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaEventPublisher;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.GerenciamentoRabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsultaEventPublisher implements ConsultaEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQConsultaEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicar(ConsultaEvent event) {
        rabbitTemplate.convertAndSend(GerenciamentoRabbitMQConfig.EXCHANGE_NAME, "", event);
    }

    @Override
    public void publicarAtualizacao(ConsultaEvent event) {
        rabbitTemplate.convertAndSend(GerenciamentoRabbitMQConfig.EXCHANGE_NAME, "", event);
    }
}
