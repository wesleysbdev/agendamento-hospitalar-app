package br.com.fiap.agendamento.notificacao.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificacaoRabbitMQConfig {

    public static final String EMAIL_QUEUE = "email.queue";
    public static final String SMS_QUEUE = "sms.queue";
    public static final String PUSH_QUEUE = "push.queue";

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(SMS_QUEUE);
    }

    @Bean
    public Queue pushQueue() {
        return new Queue(PUSH_QUEUE);
    }

    @Bean
    public Binding emailBinding(FanoutExchange fanoutExchange, Queue emailQueue) {
        return BindingBuilder.bind(emailQueue).to(fanoutExchange);
    }

    @Bean
    public Binding smsBinding(FanoutExchange fanoutExchange, Queue smsQueue) {
        return BindingBuilder.bind(smsQueue).to(fanoutExchange);
    }

    @Bean
    public Binding pushBinding(FanoutExchange fanoutExchange, Queue pushQueue) {
        return BindingBuilder.bind(pushQueue).to(fanoutExchange);
    }
}
