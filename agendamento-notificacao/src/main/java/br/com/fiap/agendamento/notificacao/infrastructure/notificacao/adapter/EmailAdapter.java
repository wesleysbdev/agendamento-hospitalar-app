package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter;

import br.com.fiap.agendamento.notificacao.application.dto.NotificacaoConsultaDTO;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.EnvioNotificacao;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class EmailAdapter implements EnvioNotificacao {

    private final Logger log =
            LoggerFactory.getLogger(EmailAdapter.class);

    public void enviar(NotificacaoConsultaDTO notificacao) {
        log.info(
                "E-mail enviado - destinatario: {}, assunto: {}, mensagem: {}",
                notificacao.pacienteId(),
                "Consulta agendada",
                "Consulta agendada para : " + notificacao.dataConsulta()
        );
    }
}
