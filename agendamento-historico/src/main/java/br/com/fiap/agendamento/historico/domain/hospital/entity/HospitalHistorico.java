package br.com.fiap.agendamento.historico.domain.hospital.entity;

import java.util.UUID;

public class HospitalHistorico {

    private final UUID id;
    private final String nome;

    public HospitalHistorico(UUID id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
