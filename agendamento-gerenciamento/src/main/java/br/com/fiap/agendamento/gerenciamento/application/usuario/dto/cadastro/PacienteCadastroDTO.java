package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro;

public record PacienteCadastroDTO(
        String nome,
        String email,
        String senha,
        String telefone
) implements UsuarioCadastroDTO {
}
