package br.com.fiap.agendamento.notificacao.application.notificacao.dto;

import br.com.fiap.agendamento.notificacao.domain.notificacao.enums.TipoNotificacao;

public record NotificacaoDTO(
        String destinatario,
        String assunto,
        String mensagem,
        TipoNotificacao tipo
) {
}
