package br.com.fiap.agendamento.historico.application.historico.dto;

import java.util.UUID;

public class HospitalHistoricoDTO {
    private UUID uuid;
    private String nome;
    private String endereco;

    public HospitalHistoricoDTO() {}

    public HospitalHistoricoDTO(UUID uuid, String nome, String endereco) {
        this.uuid = uuid;
        this.nome = nome;
        this.endereco = endereco;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
