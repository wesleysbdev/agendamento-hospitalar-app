package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;

public interface GeradorTokenAutenticacao {
    String gerar(Usuario usuario);
}
