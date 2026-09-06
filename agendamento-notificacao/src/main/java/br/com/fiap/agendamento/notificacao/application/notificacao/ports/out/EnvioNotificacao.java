package br.com.fiap.agendamento.notificacao.application.notificacao.ports.out;

import br.com.fiap.agendamento.notificacao.domain.notificacao.entity.Notificacao;

public interface EnvioNotificacao {
    void enviar(Notificacao notificacao);
}
