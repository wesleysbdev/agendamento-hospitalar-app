package br.com.fiap.agendamento.gerenciamento.domain.usuario.entity;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Crm;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;

import java.util.UUID;

public final class Medico extends Usuario {
    private Crm crm;

    public Medico(UUID uuid, String nome, Email email, String senha, boolean ativo, Crm crm, boolean excluido) {
        super(uuid, nome, email, senha, ativo, excluido);
        this.crm = crm;
    }

    public void alterarDados(String nome, Email email, Crm crm) {
        super.alterarDados(nome, email);
        this.crm = crm;
    }

    public Crm getCrm() {
        return crm;
    }

    @Override
    public TipoUsuario getTipo() {
        return TipoUsuario.MEDICO;
    }
}
