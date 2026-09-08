package br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaHistoricoDTO;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;

import java.util.List;
import java.util.UUID;

public interface GestaoHistoricoConsulta {

    List<ConsultaHistoricoDTO> buscarHistoricoPorPaciente(UUID pacienteUuid, UsuarioAutenticado usuarioAutenticado);

    List<ConsultaHistoricoDTO> buscarConsultasFuturasPorPaciente(UUID pacienteUuid, UsuarioAutenticado usuarioAutenticado);
}
