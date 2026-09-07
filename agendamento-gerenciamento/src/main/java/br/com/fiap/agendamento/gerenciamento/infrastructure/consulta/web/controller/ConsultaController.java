package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoConsultaAgenda;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoCadastroConsulta;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta.PacienteDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoConsultaUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.enums.ConsultaEstado;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto.ConsultaRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto.ConsultaResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequestMapping("consulta")
@AllArgsConstructor
public class ConsultaController {

    private final GestaoCadastroConsulta cadastroConsulta;
    private final GestaoConsultaUsuario consultaUsuario;
    private final GestaoConsultaAgenda consultaAgenda;
    private final SecurityContextProvider contextProvider;

    @PostMapping("agendar")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse agendarConsulta(@RequestBody @Valid ConsultaRequest request) {
        System.out.println("Agendamento de consulta");
//        var paciente = consultaUsuario.buscarUsuarioPorUuid(request.pacienteUuid(), contextProvider.obterUsuarioAutenticado());
//        var agenda = consultaAgenda.buscarAgendaPorUuid(request.agendaUuid());
        cadastroConsulta.cadastrarConsulta();
        return new ConsultaResponse(
                LocalTime.of(14, 30),
                "Dra. Maria Silva",
                "Hospital São Lucas",
                "Av. Paulista, 1000 - São Paulo, SP",
                ConsultaEstado.AGENDADA);
    }

    @PostMapping("atualizar/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse atualizarConsulta(@PathVariable UUID uuid) {
        System.out.println("Atualização de consulta");
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
