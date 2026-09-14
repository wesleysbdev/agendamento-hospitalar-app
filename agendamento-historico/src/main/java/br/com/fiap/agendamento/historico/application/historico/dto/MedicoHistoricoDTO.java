package br.com.fiap.agendamento.historico.application.historico.dto;

import java.util.UUID;

public class MedicoHistoricoDTO {
    private UUID uuid;
    private String nome;
    private String crm;

    public MedicoHistoricoDTO() {}

    public MedicoHistoricoDTO(UUID uuid, String nome, String crm) {
        this.uuid = uuid;
        this.nome = nome;
        this.crm = crm;
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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }
}
