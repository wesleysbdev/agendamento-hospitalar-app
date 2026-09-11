package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.*;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;

import java.util.UUID;

public interface GestaoEditarUsuario {
    Usuario ativarUsuario(UUID usuarioUuid, UsuarioAutenticado usuarioAutenticado);

    Usuario inativarUsuario(UUID usuarioUuid, UsuarioAutenticado usuarioAutenticado);

    Usuario excluirUsuario(UUID usuarioUuid, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarDadosAdministrador(UUID usuarioUuid, AdministradorEdicaoDTO administradorEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarDadosPaciente(UUID usuarioUuid, PacienteEdicaoDTO pacienteEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarDadosEnfermeiro(UUID usuarioUuid, EnfermeiroEdicaoDTO enfermeiroEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarDadosMedico(UUID usuarioUuid, MedicoEdicaoDTO medicoEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario alterarSenhaUsuario(UUID usuarioUuid, AlteracaoSenhaDTO alteracaoSenha, UsuarioAutenticado usuarioAutenticado);
}
