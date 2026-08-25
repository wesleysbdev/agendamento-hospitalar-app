package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.*;

public interface GestaoEditarUsuario {
    void mudarEstadoDoUsuario(AlterarEstadoDTO alterarEstadoDTO, UsuarioAutenticado usuarioAutenticado);

    void alterarDadosAdministrador(AdministradorEdicaoDTO administradorEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    void alterarDadosPaciente(PacienteEdicaoDTO pacienteEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    void alterarDadosEnfermeiro(EnfermeiroEdicaoDTO enfermeiroEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    void alterarDadosMedico(MedicoEdicaoDTO medicoEdicaoDTO, UsuarioAutenticado usuarioAutenticado);

    void alterarSenhaUsuario(AlteracaoSenhaDTO alteracaoSenha, UsuarioAutenticado usuarioAutenticado);
}
