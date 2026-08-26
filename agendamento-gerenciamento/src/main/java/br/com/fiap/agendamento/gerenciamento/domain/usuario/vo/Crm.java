package br.com.fiap.agendamento.gerenciamento.domain.usuario.vo;

import br.com.fiap.agendamento.gerenciamento.domain.usuario.enums.Uf;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.exception.UsuarioDadosInvalidosException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Crm(
        String numero,
        Uf uf
) {
    // Regex que captura: de 3 a 7 números, separador opcional (hífen ou barra), e 2 letras
    private static final Pattern CRM_PATTERN = Pattern.compile("^(\\d{3,7})[-/]?([A-Z]{2})$");
    // Validação do formato do número (apenas dígitos, de 3 a 7 caracteres)
    private static final Pattern CRM_NUMERO_PATTERN = Pattern.compile("^\\d{3,7}$");


    public Crm {
        if (numero == null || uf == null) {
            throw new UsuarioDadosInvalidosException("Número do CRM e UF não podem ser nulos.");
        }

        String numeroLimpo = numero.trim();

        if (!CRM_NUMERO_PATTERN.matcher(numeroLimpo).matches()) {
            throw new UsuarioDadosInvalidosException("O número do CRM deve conter de 3 a 7 dígitos numéricos.");
        }

        numero = numeroLimpo;
    }

    public static Crm criarDeTextoCompleto(String crmCompleto) {
        if (crmCompleto == null || crmCompleto.isBlank()) {
            throw new UsuarioDadosInvalidosException("O CRM informado não pode estar vazio.");
        }

        String textoNormalizado = crmCompleto.trim().toUpperCase().replaceAll("\\s+", "");
        Matcher matcher = CRM_PATTERN.matcher(textoNormalizado);

        if (!matcher.matches()) {
            throw new UsuarioDadosInvalidosException("Formato de CRM inválido. Use padrões como '123456-SP' ou '12345-RJ'.");
        }

        String numeroExtraido = matcher.group(1);
        String ufExtraida = matcher.group(2);

        return new Crm(numeroExtraido, Uf.valueOf(ufExtraida.toUpperCase()));
    }

    @Override
    public String toString() {
        return numero + "-" + uf;
    }
}
