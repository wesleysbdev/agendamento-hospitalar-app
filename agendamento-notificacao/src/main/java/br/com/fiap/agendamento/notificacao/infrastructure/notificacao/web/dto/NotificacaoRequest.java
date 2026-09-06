package br.com.fiap.agendamento.notificacao.infrastructure.notificacao.web.dto;

import br.com.fiap.agendamento.notificacao.domain.notificacao.enums.TipoNotificacao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NotificacaoRequest(
        @NotBlank(message = "O destinatário é obrigatório")
        @Email(message = "O destinatário deve ser um e-mail válido")
        String destinatario,
        @NotBlank(message = "O assunto é obrigatório")
        String assunto,
        @NotBlank(message = "A mensagem é obrigatória")
        String mensagem,
        TipoNotificacao tipo
) {
}
