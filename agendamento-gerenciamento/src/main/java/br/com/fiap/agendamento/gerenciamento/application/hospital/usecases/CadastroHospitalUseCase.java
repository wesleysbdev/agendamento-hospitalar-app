package br.com.fiap.agendamento.gerenciamento.application.hospital.usecases;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.HospitalCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoCadastroHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out.HospitalRepository;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.exception.HospitalNaoEncontradoException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.vo.Telefone;

import java.util.UUID;

public class CadastroHospitalUseCase implements GestaoCadastroHospital {

    private final HospitalRepository hospitalRepository;

    public CadastroHospitalUseCase(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public Hospital cadastrar(HospitalCadastroDTO hospitalCadastroDTO, UsuarioAutenticado usuarioAutenticado) {
        Hospital hospital = new Hospital(
                UUID.randomUUID(),
                hospitalCadastroDTO.nome(),
                hospitalCadastroDTO.endereco(),
                new Telefone(hospitalCadastroDTO.telefone()),
                true,
                false,
                hospitalCadastroDTO.diaSemanaInicio(),
                hospitalCadastroDTO.diaSemanaFim(),
                hospitalCadastroDTO.horaInicio(),
                hospitalCadastroDTO.horaFim(),
                hospitalCadastroDTO.tempoLimiteCancelamento(),
                hospitalCadastroDTO.tempoToleranciaPosConsulta(),
                hospitalCadastroDTO.tempoMinimoConsulta()
        );

        return hospitalRepository.salvar(hospital);
    }

    @Override
    public Hospital alterarDadosHospital(UUID hospitalUuid, HospitalCadastroDTO hospitalEdicaoDTO, UsuarioAutenticado usuarioAutenticado) {
        Hospital hospital = buscarHospitalPorUuid(hospitalUuid);
        hospital.alterarDados(
                hospitalEdicaoDTO.nome(),
                hospitalEdicaoDTO.endereco(),
                new Telefone(hospitalEdicaoDTO.telefone()),
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
        return hospitalRepository.buscarPorId(uuid)
                .orElseThrow(() -> new HospitalNaoEncontradoException("Hospital não encontrado."));
    }
}
