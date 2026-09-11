package br.com.fiap.agendamento.gerenciamento.application.hospital.usecases;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.consulta.HospitalDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoConsultaHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out.HospitalRepository;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.exception.HospitalNaoEncontradoException;

import java.util.List;
import java.util.UUID;

public class ConsultaHospitalUseCase implements GestaoConsultaHospital {

    private final HospitalRepository hospitalRepository;

    public ConsultaHospitalUseCase(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public List<HospitalDTO> listarHospitais(UsuarioAutenticado usuarioAutenticado) {
        List<Hospital> hospitais = hospitalRepository.listar();
        return hospitais.stream().map(this::converterParaDTO).toList();
    }

    @Override
    public HospitalDTO buscarHospitalPorUuid(UUID uuid, UsuarioAutenticado usuarioAutenticado) {
        Hospital hospital = buscarHospitalPorUuid(uuid);
        return converterParaDTO(hospital);
    }

    private HospitalDTO converterParaDTO(Hospital hospital) {
        return new HospitalDTO() {
            @Override
            public UUID uuid() {
                return hospital.getUuid();
            }

            @Override
            public String nome() {
                return hospital.getNome();
            }

            @Override
            public String endereco() {
                return hospital.getEndereco();
            }

            @Override
            public br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone telefone() {
                return hospital.getTelefone();
            }

            @Override
            public boolean ativo() {
                return hospital.isAtivo();
            }

            @Override
            public java.time.DayOfWeek diaSemanaInicio() {
                return hospital.getDiaSemanaInicio();
            }

            @Override
            public java.time.DayOfWeek diaSemanaFim() {
                return hospital.getDiaSemanaFim();
            }

            @Override
            public java.time.LocalTime horaInicio() {
                return hospital.getHoraInicio();
            }

            @Override
            public java.time.LocalTime horaFim() {
                return hospital.getHoraFim();
            }

            @Override
            public java.time.Duration tempoLimiteCancelamento() {
                return hospital.getTempoLimiteCancelamento();
            }

            @Override
            public java.time.Duration tempoToleranciaPosConsulta() {
                return hospital.getTempoToleranciaPosConsulta();
            }

            @Override
            public java.time.Duration tempoMinimoConsulta() {
                return hospital.getTempoMinimoConsulta();
            }
        };
    }

    private Hospital buscarHospitalPorUuid(UUID uuid) {
        return hospitalRepository.buscarPorUuid(uuid)
                .orElseThrow(() -> new HospitalNaoEncontradoException("Hospital não encontrado."));
    }
}
