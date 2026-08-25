package br.com.fiap.agendamento.gerenciamento.domain.constants;

import java.util.Set;

public abstract class UsuarioConstants {

    public static final Set<String> UNIDADES_FEDERATIVAS = Set.of(
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO",
            "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
            "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    );

    public static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static final String REGEX_APENAS_NUMEROS = "^\\d+$";

    public static final String REGEX_SENHA_FORTE = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    
    public static final String REGEX_TELEFONE_BR = "^\\d{10,11}$";
}
