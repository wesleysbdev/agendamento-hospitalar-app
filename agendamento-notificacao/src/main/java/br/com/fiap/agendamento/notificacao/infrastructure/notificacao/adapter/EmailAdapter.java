package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter;

import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.EnvioNotificacao;
import br.com.fiap.agendamento.notificacao.domain.notificacao.entity.Notificacao;
import br.com.fiap.agendamento.notificacao.domain.notificacao.enums.TipoNotificacao;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailAdapter implements EnvioNotificacao {

    private final JavaMailSender mailSender;

    public EmailAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviar(Notificacao notificacao) {
        if (notificacao.getTipo() == TipoNotificacao.EMAIL) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notificacao.getDestinatario());
            message.setSubject(notificacao.getAssunto());
            message.setText(notificacao.getMensagem());
            mailSender.send(message);
        }
    }
}
