package br.com.fiap.agendamento.gerenciamento.application.hospital.usecases;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.HospitalDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoConsultaHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out.HospitalRepository;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.exception.HospitalNaoEncontradoException;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.TipoUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioNaoAutorizadoException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ConsultaHospitalUseCase implements GestaoConsultaHospital {

    private final HospitalRepository hospitalRepository;

    public ConsultaHospitalUseCase(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public static void verificaPermissao(TipoUsuario tipoUsuario, Set<TipoUsuario> tiposPermitidos) {
        if (!tiposPermitidos.contains(tipoUsuario)) {
            throw new UsuarioNaoAutorizadoException();
        }
    }

    @Override
    public List<HospitalDTO> listarHospitais(UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO, TipoUsuario.MEDICO, TipoUsuario.PACIENTE));
        List<Hospital> hospitais = hospitalRepository.listar();
        return hospitais.stream().map(this::converterParaDTO).toList();
    }

    @Override
    public HospitalDTO buscarHospitalPorId(UUID uuid, UsuarioAutenticado usuarioAutenticado) {
        verificaPermissao(usuarioAutenticado.tipo(), Set.of(TipoUsuario.ADMINISTRADOR, TipoUsuario.ENFERMEIRO, TipoUsuario.MEDICO, TipoUsuario.PACIENTE));
        Hospital hospital = buscarHospitalPorUuid(uuid);
        return converterParaDTO(hospital);
    }

    private HospitalDTO converterParaDTO(Hospital hospital) {
        return new HospitalDTO(
                hospital.getId(),
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
        return hospitalRepository.buscarPorId(uuid)
                .orElseThrow(() -> new HospitalNaoEncontradoException("Hospital não encontrado."));
    }
}
