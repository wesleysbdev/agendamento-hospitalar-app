package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.adapter;

import br.com.fiap.agendamento.notificacao.application.dto.NotificacaoConsultaDTO;
import br.com.fiap.agendamento.notificacao.application.notificacao.ports.out.EnvioNotificacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SMSAdapter implements EnvioNotificacao {

    private final Logger log =
            LoggerFactory.getLogger(SMSAdapter.class);

    @Override
    public void enviar(NotificacaoConsultaDTO notificacao) {
        log.info(
                "SMS enviado - destinatario: {}, mensagem: {}",
                notificacao.pacienteId(),
                "Consulta agendada para : " + notificacao.dataConsulta()
        );
    }
}
