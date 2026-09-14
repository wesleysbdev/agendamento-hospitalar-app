package br.com.fiap.agendamento.historico.application.historico.dto;

import java.util.UUID;

public class PacienteHistoricoDTO {
    private UUID uuid;
    private String nome;

    public PacienteHistoricoDTO() {}

    public PacienteHistoricoDTO(UUID uuid, String nome) {
        this.uuid = uuid;
        this.nome = nome;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
