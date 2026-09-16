package br.com.fiap.agendamento.historico.domain.usuario.entity;

import java.util.UUID;

public class PacienteHistorico {

    private final UUID id;
    private final String nome;
    private final String email;
    private final String telefone;

    public PacienteHistorico(UUID id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
}
