package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro;

public record AdministradorCadastroDTO(
        String nome,
        String email,
        String senha
) implements UsuarioCadastroDTO {
}
