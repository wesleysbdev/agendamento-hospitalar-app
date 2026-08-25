package br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.EnfermeiroCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.MedicoCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.AdministradorCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;

public interface GestaoCadastroUsuario {
    void cadastrarAdministrador(AdministradorCadastroDTO usuarioCadastro, UsuarioAutenticado usuarioAutenticado);

    void cadastrarMedico(MedicoCadastroDTO medicoCadastro, UsuarioAutenticado usuarioAutenticado);

    void cadastrarEnfermeiro(EnfermeiroCadastroDTO enfermeiroCadastroDTO, UsuarioAutenticado usuarioAutenticado);

    void cadastrarPaciente(PacienteCadastroDTO pacienteCadastro);
}
