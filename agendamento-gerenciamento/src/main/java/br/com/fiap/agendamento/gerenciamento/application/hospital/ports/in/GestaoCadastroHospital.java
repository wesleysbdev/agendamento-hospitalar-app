package br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.HospitalCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;

import java.util.UUID;

public interface GestaoCadastroHospital {
    Hospital cadastrar(HospitalCadastroDTO hospitalCadastroDTO, UsuarioAutenticado usuarioAutenticado);

    Hospital alterarDadosHospital(UUID hospitalUuid, HospitalCadastroDTO hospitalEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    Hospital ativarHospital(UUID hospitalUuid, UsuarioAutenticado usuarioAutenticado);

    Hospital inativarHospital(UUID hospitalUuid, UsuarioAutenticado usuarioAutenticado);

    Hospital excluirHospital(UUID hospitalUuid, UsuarioAutenticado usuarioAutenticado);
}
