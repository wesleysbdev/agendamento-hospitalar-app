package br.com.fiap.agendamento.gerenciamento.domain.hospital.entity;

import br.com.fiap.agendamento.gerenciamento.domain.hospital.exception.HospitalDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

public class Hospital {

    private final UUID uuid;
    private String nome;
    private String endereco;
    private Telefone telefone;
    private boolean ativo;
    private boolean excluido;
    private DayOfWeek diaSemanaInicio;
    private DayOfWeek diaSemanaFim;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Duration tempoLimiteCancelamento;
    private Duration tempoToleranciaPosConsulta;
    private Duration tempoMinimoConsulta;

    public Hospital(
            UUID uuid,
            String nome,
            String endereco,
            Telefone telefone,
            boolean ativo,
            boolean excluido,
            DayOfWeek diaSemanaInicio,
            DayOfWeek diaSemanaFim,
            LocalTime horaInicio,
            LocalTime horaFim,
            Duration tempoLimiteCancelamento,
            Duration tempoToleranciaPosConsulta,
            Duration tempoMinimoConsulta
    ) {
        this.uuid = uuid;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.ativo = ativo;
        this.excluido = excluido;
        this.diaSemanaInicio = diaSemanaInicio;
        this.diaSemanaFim = diaSemanaFim;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.tempoLimiteCancelamento = tempoLimiteCancelamento;
        this.tempoToleranciaPosConsulta = tempoToleranciaPosConsulta;
        this.tempoMinimoConsulta = tempoMinimoConsulta;
    }

    public void inativar() {
        this.ativo = false;
    }

    public void ativar() {
        if (excluido) {
            throw new HospitalDadosInvalidosException(
                    "Hospital excluído não pode ser ativado."
            );
        }
        this.ativo = true;
    }

    public void excluir() {
        this.excluido = true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public DayOfWeek getDiaSemanaInicio() {
        return diaSemanaInicio;
    }

    public DayOfWeek getDiaSemanaFim() {
        return diaSemanaFim;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public Duration getTempoLimiteCancelamento() {
        return tempoLimiteCancelamento;
    }

    public Duration getTempoToleranciaPosConsulta() {
        return tempoToleranciaPosConsulta;
    }

    public Duration getTempoMinimoConsulta() {
        return tempoMinimoConsulta;
    }
}
