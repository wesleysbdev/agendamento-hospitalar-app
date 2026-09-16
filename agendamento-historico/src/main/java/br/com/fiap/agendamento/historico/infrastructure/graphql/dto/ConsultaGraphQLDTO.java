package br.com.fiap.agendamento.historico.infrastructure.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaGraphQLDTO {

    private String id;
    private PacienteGraphQLDTO paciente;
    private MedicoGraphQLDTO medico;
    private HospitalGraphQLDTO hospital;
    private String data;
    private String horario;
    private String status;
    private String criadoEm;
}
