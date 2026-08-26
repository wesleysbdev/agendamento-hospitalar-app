package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.EnfermeiroCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.MedicoCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.AdministradorCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;

public interface GestaoCadastroUsuario {
    Usuario cadastrarAdministrador(AdministradorCadastroDTO usuarioCadastro, UsuarioAutenticado usuarioAutenticado);

    Usuario cadastrarMedico(MedicoCadastroDTO medicoCadastro, UsuarioAutenticado usuarioAutenticado);

    Usuario cadastrarEnfermeiro(EnfermeiroCadastroDTO enfermeiroCadastroDTO, UsuarioAutenticado usuarioAutenticado);

    Usuario cadastrarPaciente(PacienteCadastroDTO pacienteCadastro);
}
