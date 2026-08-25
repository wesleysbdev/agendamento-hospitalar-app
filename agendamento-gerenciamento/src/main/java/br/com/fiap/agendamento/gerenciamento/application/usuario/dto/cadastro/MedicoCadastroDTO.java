package br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro;

public record MedicoCadastroDTO(
        String nome,
        String email,
        String senha,
        String crm
) implements UsuarioCadastroDTO {
}
