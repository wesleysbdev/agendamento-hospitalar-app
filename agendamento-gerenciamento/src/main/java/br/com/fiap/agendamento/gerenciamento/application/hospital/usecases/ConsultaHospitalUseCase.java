package br.com.fiap.agendamento.gerenciamento.application.hospital.usecases;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.HospitalDTO;
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
        return new HospitalDTO(
                hospital.getUuid(),
                hospital.getNome(),
                hospital.getEndereco(),
                hospital.getTelefone(),
                hospital.isAtivo(),
                hospital.getDiaSemanaInicio(),
                hospital.getDiaSemanaFim(),
                hospital.getHoraInicio(),
                hospital.getHoraFim(),
                hospital.getTempoLimiteCancelamento(),
                hospital.getTempoToleranciaPosConsulta(),
                hospital.getTempoMinimoConsulta()
        );
    }

    private Hospital buscarHospitalPorUuid(UUID uuid) {
        return hospitalRepository.buscarPorUuid(uuid)
                .orElseThrow(() -> new HospitalNaoEncontradoException("Hospital não encontrado."));
    }
}
