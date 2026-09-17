package br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;

import java.util.UUID;

public interface GestaoCadastroConsulta {

    Consulta cadastrarConsulta(ConsultaCadastroDTO consultaCadastroDTO, UsuarioAutenticado usuarioAutenticado);

    Consulta cancelarConsulta(UUID consultaId, UsuarioAutenticado usuarioAutenticado);

    Consulta realizarConsulta(UUID consultaId, UsuarioAutenticado usuarioAutenticado);

    Consulta confirmarConsulta(UUID consultaId, UsuarioAutenticado usuarioAutenticado);

    Consulta marcarComoAusente(UUID consultaId, UsuarioAutenticado usuarioAutenticado);
}
