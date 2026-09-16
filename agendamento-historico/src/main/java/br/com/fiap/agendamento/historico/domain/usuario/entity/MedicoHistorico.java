package br.com.fiap.agendamento.historico.domain.usuario.entity;

import java.util.UUID;

public class MedicoHistorico {

    private final UUID id;
    private final String nome;
    private final String email;

    public MedicoHistorico(UUID id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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
}
