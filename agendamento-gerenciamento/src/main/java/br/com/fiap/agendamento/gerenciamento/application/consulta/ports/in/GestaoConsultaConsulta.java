package br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaDTO;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;

import java.util.List;
import java.util.UUID;

public interface GestaoConsultaConsulta {
    List<ConsultaDTO> listarConsultar(UsuarioAutenticado usuarioAutenticado);

    ConsultaDTO buscarPorId(UUID id, UsuarioAutenticado usuarioAutenticado);

    List<ConsultaDTO> listarConsultasPorPaciente(UUID id, UsuarioAutenticado usuarioAutenticado);

    List<ConsultaDTO> listarConsultasMedico(UUID id, UsuarioAutenticado usuarioAutenticado);
}
