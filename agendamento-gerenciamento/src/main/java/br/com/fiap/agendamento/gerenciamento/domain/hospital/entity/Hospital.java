package br.com.fiap.agendamento.gerenciamento.domain.hospital.entity;

import br.com.fiap.agendamento.gerenciamento.domain.hospital.exception.HospitalDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Email;
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
        validarIdentificador(uuid);
        validarDadosObrigatorios(nome, endereco, telefone, diaSemanaInicio, diaSemanaFim, horaInicio, horaFim, tempoLimiteCancelamento, tempoToleranciaPosConsulta, tempoMinimoConsulta);
        validarDadosCadastrais(diaSemanaInicio, diaSemanaFim, horaInicio, horaFim);
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

    public void alterarDados(
            String nome,
            String endereco,
            Telefone telefone,
            DayOfWeek diaSemanaInicio,
            DayOfWeek diaSemanaFim,
            LocalTime horaInicio,
            LocalTime horaFim,
            Duration tempoLimiteCancelamento,
            Duration tempoToleranciaPosConsulta,
            Duration tempoMinimoConsulta
    ) {
        validarDadosObrigatorios(nome, endereco, telefone, diaSemanaInicio, diaSemanaFim, horaInicio, horaFim, tempoLimiteCancelamento, tempoToleranciaPosConsulta, tempoMinimoConsulta);
        validarDadosCadastrais(diaSemanaInicio, diaSemanaFim, horaInicio, horaFim);
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.diaSemanaInicio = diaSemanaInicio;
        this.diaSemanaFim = diaSemanaFim;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.tempoLimiteCancelamento = tempoLimiteCancelamento;
        this.tempoToleranciaPosConsulta = tempoToleranciaPosConsulta;
        this.tempoMinimoConsulta = tempoMinimoConsulta;
    }

    private static void validarIdentificador(UUID uuid) {
        if (uuid == null || uuid.toString().isBlank()) {
            throw new UsuarioDadosInvalidosException("UUID é obrigatório.");
        }
    }

    private void validarDadosObrigatorios(String nome, String endereco, Telefone telefone, DayOfWeek diaSemanaInicio, DayOfWeek diaSemanaFim, LocalTime horaInicio, LocalTime horaFim, Duration tempoLimiteCancelamento, Duration tempoToleranciaPosConsulta, Duration tempoMinimoConsulta) {
        if (nome == null || nome.isBlank()) {
            throw new HospitalDadosInvalidosException("O nome do hospital é obrigatório.");
        }

        if (endereco == null || endereco.isBlank()) {
            throw new HospitalDadosInvalidosException("O endereço do hospital é obrigatório.");
        }

        if (telefone == null) {
            throw new HospitalDadosInvalidosException("O telefone do hospital é obrigatório.");
        }

        if (diaSemanaInicio == null) {
            throw new HospitalDadosInvalidosException("O dia da semana inicial é obrigatório.");
        }

        if (diaSemanaFim == null) {
            throw new HospitalDadosInvalidosException("O dia da semana final é obrigatório.");
        }

        if (horaInicio == null) {
            throw new HospitalDadosInvalidosException("A hora inicial é obrigatória.");
        }

        if (horaFim == null) {
            throw new HospitalDadosInvalidosException("A hora final é obrigatória.");
        }

        if (tempoLimiteCancelamento == null || tempoLimiteCancelamento.isNegative()) {
            throw new HospitalDadosInvalidosException("O tempo limite de cancelamento não pode ser negativo.");
        }

        if (tempoToleranciaPosConsulta == null || tempoToleranciaPosConsulta.isNegative()) {
            throw new HospitalDadosInvalidosException("O tempo de tolerância após a consulta não pode ser negativo.");
        }

        if (tempoMinimoConsulta == null || tempoMinimoConsulta.isZero() || tempoMinimoConsulta.isNegative()) {
            throw new HospitalDadosInvalidosException("O tempo mínimo de consulta deve ser maior que zero.");
        }
    }

    private void validarDadosCadastrais(DayOfWeek diaSemanaInicio, DayOfWeek diaSemanaFim, LocalTime horaInicio, LocalTime horaFim) {
        if (diaSemanaFim.compareTo(diaSemanaInicio) <= 0) {
//            < 0  -> duration1 é menor
//            = 0  -> são iguais
//            > 0  -> duration1 é maior
            throw new HospitalDadosInvalidosException("O dia da semana final deve ser posterior ao dia da semana inicial.");
        }

        if (!horaFim.isAfter(horaInicio)) {
            throw new HospitalDadosInvalidosException("A hora final deve ser posterior à hora inicial.");
        }
    }

    public void inativar() {
        this.ativo = false;
    }

    public void ativar() {
        if (excluido) {
            throw new HospitalDadosInvalidosException("Hospital excluído não pode ser ativado.");
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
