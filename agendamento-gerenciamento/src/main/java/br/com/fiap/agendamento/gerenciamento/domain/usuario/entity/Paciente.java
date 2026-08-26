package br.com.fiap.agendamento.gerenciamento.domain.usuario.entity;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

import java.util.UUID;

public final class Paciente extends Usuario {

    private Telefone telefone;

    public Paciente(UUID uuid, String nome, Email email, Telefone telefone, String senha, boolean ativo, boolean excluido) {
        super(uuid, nome, email, senha, ativo, excluido);
        this.telefone = telefone;
    }

    public void alterarDados(String nome, Email email, Telefone telefone) {
        super.alterarDados(nome, email);
        this.telefone = telefone;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.PACIENTE;
    }
}
