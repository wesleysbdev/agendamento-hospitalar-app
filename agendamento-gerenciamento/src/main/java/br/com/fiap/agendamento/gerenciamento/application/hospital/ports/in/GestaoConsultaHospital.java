package br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.consulta.HospitalDTO;

import java.util.List;
import java.util.UUID;

public interface GestaoConsultaHospital {
    List<HospitalDTO> listarHospitais(UsuarioAutenticado usuarioAutenticado);

    HospitalDTO buscarHospitalPorUuid(UUID uuid, UsuarioAutenticado usuarioAutenticado);
}
