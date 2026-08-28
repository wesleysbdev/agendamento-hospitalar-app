package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.controller;

import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.ConsultaEstado;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto.ConsultaRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto.ConsultaResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
public class ConsultaController {

    @PostMapping("agendar")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse agendarConsulta(@RequestBody @Valid ConsultaRequest request) {
        System.out.println("Agendamento de consulta");
        return new ConsultaResponse(
                LocalTime.of(14, 30),
                "Dra. Maria Silva",
                "Hospital São Lucas",
                "Av. Paulista, 1000 - São Paulo, SP",
                ConsultaEstado.AGENDADA);
    }

    @PostMapping("cancelar/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse cancelarConsulta(@PathVariable UUID uuid) {
        System.out.println("Cancelamento de consulta");
        return new ConsultaResponse(
                LocalTime.of(14, 30),
                "Dra. Maria Silva",
                "Hospital São Lucas",
                "Av. Paulista, 1000 - São Paulo, SP",
                ConsultaEstado.CANCELADA);
    }

    @PostMapping("confirmar/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse confirmarConsulta(@PathVariable UUID uuid) {
        System.out.println("Confirmação de consulta");
        return new ConsultaResponse(
                LocalTime.of(14, 30),
                "Dra. Maria Silva",
                "Hospital São Lucas",
                "Av. Paulista, 1000 - São Paulo, SP",
                ConsultaEstado.CANCELADA);
    }
}
