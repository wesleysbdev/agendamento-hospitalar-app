package br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.cadastro.HospitalCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;

public interface GestaoCadastroHospital {
    Hospital cadastrar(HospitalCadastroDTO hospitalCadastroDTO, UsuarioAutenticado usuarioAutenticado);
}
