package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;

import java.util.UUID;

public interface GestaoEditarUsuario {
    Usuario mudarEstadoDoUsuario(AlterarEstadoDTO alterarEstadoDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario excluirUsuario(UUID usuarioUuid, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarDadosAdministrador(AdministradorEdicaoDTO administradorEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarDadosPaciente(PacienteEdicaoDTO pacienteEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarDadosEnfermeiro(EnfermeiroEdicaoDTO enfermeiroEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarDadosMedico(MedicoEdicaoDTO medicoEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarSenhaUsuario(AlteracaoSenhaDTO alteracaoSenha, UsuarioAutenticado usuarioAutenticado);
}
