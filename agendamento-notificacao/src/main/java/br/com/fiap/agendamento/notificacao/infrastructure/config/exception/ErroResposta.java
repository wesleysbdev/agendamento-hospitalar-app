package br.com.fiap.agendamento.notificacao.infrastructure.config.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResposta(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        List<CampoErro> campos
) {
    public record CampoErro(String campo, String mensagem) {
    }
}
