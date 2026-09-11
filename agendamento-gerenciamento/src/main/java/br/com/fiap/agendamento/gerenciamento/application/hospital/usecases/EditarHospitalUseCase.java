package br.com.fiap.agendamento.gerenciamento.application.hospital.usecases;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.cadastro.HospitalCadastroDTO;
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
    public Hospital alterarDadosHospital(UUID hospitalUuid, HospitalCadastroDTO hospitalEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        Hospital hospital = buscarHospitalPorUuid(hospitalUuid);
        hospital.alterarDados(
                hospitalEdicaoDTO.nome(),
                hospitalEdicaoDTO.endereco(),
                hospitalEdicaoDTO.telefone(),
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
    public Hospital ativarHospital(UUID hospitalUuid, UsuarioAutenticado usuarioAutenticado) {
        Hospital hospital = buscarHospitalPorUuid(hospitalUuid);
        hospital.ativar();
        return hospitalRepository.salvar(hospital);
    }

    @Override
    public Hospital inativarHospital(UUID hospitalUuid, UsuarioAutenticado usuarioAutenticado) {
        Hospital hospital = buscarHospitalPorUuid(hospitalUuid);
        hospital.inativar();
        return hospitalRepository.salvar(hospital);
    }

    @Override
    public Hospital excluirHospital(UUID hospitalUuid, UsuarioAutenticado usuarioAutenticado) {
        Hospital hospital = buscarHospitalPorUuid(hospitalUuid);
        hospital.excluir();
        return hospitalRepository.salvar(hospital);
    }

    private Hospital buscarHospitalPorUuid(UUID uuid) {
        return hospitalRepository.buscarPorUuid(uuid)
                .orElseThrow(() -> new HospitalNaoEncontradoException("Hospital não encontrado."));
    }
}
