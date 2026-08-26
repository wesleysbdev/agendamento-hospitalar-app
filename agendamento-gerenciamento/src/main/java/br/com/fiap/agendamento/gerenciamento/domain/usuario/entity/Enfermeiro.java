package br.com.fiap.agendamento.gerenciamento.domain.usuario.entity;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;

import java.util.UUID;

public final class Enfermeiro extends Usuario {

    public Enfermeiro(UUID uuid, String nome, Email email, String senha, boolean ativo, boolean excluido) {
        super(uuid, nome, email, senha, ativo, excluido);
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.ENFERMEIRO;
    }
}
