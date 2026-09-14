package br.com.fiap.agendamento.historico.domain.historico.entity;

import br.com.fiap.agendamento.historico.domain.historico.enums.EstadoConsulta;

import java.time.LocalDateTime;
import java.util.UUID;

public class HistoricoConsulta {

    private final UUID consultaUuid;
    private final LocalDateTime horario;
    private final EstadoConsulta estado;
    private final PacienteInfo paciente;
    private final MedicoInfo medico;
    private final HospitalInfo hospital;

    public HistoricoConsulta(UUID consultaUuid, LocalDateTime horario, EstadoConsulta estado,
                            PacienteInfo paciente, MedicoInfo medico, HospitalInfo hospital) {
        this.consultaUuid = consultaUuid;
        this.horario = horario;
        this.estado = estado;
        this.paciente = paciente;
        this.medico = medico;
        this.hospital = hospital;
    }

    public UUID getConsultaUuid() {
        return consultaUuid;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public EstadoConsulta getEstado() {
        return estado;
    }

    public PacienteInfo getPaciente() {
        return paciente;
    }

    public MedicoInfo getMedico() {
        return medico;
    }

    public HospitalInfo getHospital() {
        return hospital;
    }

    public static class PacienteInfo {
        private final UUID uuid;
        private final String nome;

        public PacienteInfo(UUID uuid, String nome) {
            this.uuid = uuid;
            this.nome = nome;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getNome() {
            return nome;
        }
    }

    public static class MedicoInfo {
        private final UUID uuid;
        private final String nome;
        private final String crm;

        public MedicoInfo(UUID uuid, String nome, String crm) {
            this.uuid = uuid;
            this.nome = nome;
            this.crm = crm;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getNome() {
            return nome;
        }

        public String getCrm() {
            return crm;
        }
    }

    public static class HospitalInfo {
        private final UUID uuid;
        private final String nome;
        private final String endereco;

        public HospitalInfo(UUID uuid, String nome, String endereco) {
            this.uuid = uuid;
            this.nome = nome;
            this.endereco = endereco;
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
    }
}
