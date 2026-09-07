package br.com.fiap.agendamento.gerenciamento.application.hospital.usecases;

import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.edicao.HospitalEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoEditarHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out.HospitalRepository;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.exception.HospitalNaoEncontradoException;

import java.util.UUID;

public class EditarHospitalUseCase implements GestaoEditarHospital {

    private final HospitalRepository hospitalRepository;

    public EditarHospitalUseCase(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public Hospital alterarDadosHospital(HospitalEdicaoDTO hospitalEdicaoDTO) {
        Hospital hospital = buscarHospitalPorUuid(hospitalEdicaoDTO.uuid());
        hospital = new Hospital(
                hospital.getUuid(),
                hospitalEdicaoDTO.nome(),
                hospitalEdicaoDTO.endereco(),
                hospitalEdicaoDTO.telefone(),
                hospital.isAtivo(),
                hospital.isExcluido(),
                hospitalEdicaoDTO.diaSemanaInicio(),
                hospitalEdicaoDTO.diaSemanaFim(),
                hospitalEdicaoDTO.horaInicio(),
                hospitalEdicaoDTO.horaFim(),
                hospitalEdicaoDTO.tempoLimiteCancelamento(),
                hospitalEdicaoDTO.tempoToleranciaPosConsulta(),
                hospitalEdicaoDTO.tempoMinimoConsulta()
        );
        return hospitalRepository.salvar(hospital);
    }

    @Override
    public Hospital ativarHospital(UUID hospitalUuid) {
        Hospital hospital = buscarHospitalPorUuid(hospitalUuid);
        hospital.ativar();
        return hospitalRepository.salvar(hospital);
    }

    @Override
    public Hospital inativarHospital(UUID hospitalUuid) {
        Hospital hospital = buscarHospitalPorUuid(hospitalUuid);
        hospital.inativar();
        return hospitalRepository.salvar(hospital);
    }

    @Override
    public Hospital excluirHospital(UUID hospitalUuid) {
        Hospital hospital = buscarHospitalPorUuid(hospitalUuid);
        hospital.excluir();
        return hospitalRepository.salvar(hospital);
    }

    private Hospital buscarHospitalPorUuid(UUID uuid) {
        return hospitalRepository.buscarPorUuid(uuid)
                .orElseThrow(() -> new HospitalNaoEncontradoException("Hospital não encontrado."));
    }
}
