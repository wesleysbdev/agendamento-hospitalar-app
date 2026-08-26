package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.autenticacao.AutenticacaoDTO;

public interface GestaoAutenticacao {
    String login(AutenticacaoDTO autenticacao);
}
