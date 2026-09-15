package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.AgendaDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.dto.HorarioAgendaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoCadastroAgenda;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoConsultaAgenda;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.domain.agenda.entity.Agenda;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.AdicionarHorariosRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.AgendaRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.AgendaResponse;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.dto.HorarioAgendaRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.mapper.AgendaMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.mapper.HorarioAgendaMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("agendas")
@AllArgsConstructor
public class AgendaController {

    private final GestaoCadastroAgenda cadastroAgenda;
    private final AgendaMapper agendaMapper;
    private final HorarioAgendaMapper horarioMapper;
    private final GestaoConsultaAgenda consultaAgenda;
    private final SecurityContextProvider contextProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendaResponse cadastrarAgenda(@RequestBody @Valid AgendaRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        var agendaCadastroDTO = agendaMapper.paraDTO(request);
        Agenda agenda = cadastroAgenda.cadastrar(agendaCadastroDTO, usuarioAutenticado);
        return agendaMapper.paraResponse(agenda);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AgendaResponse> listarAgendas() {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<AgendaDTO> agendas = consultaAgenda.listarAgendas(usuarioAutenticado);
        return agendas.stream().map(agendaMapper::paraResponse).toList();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public AgendaResponse consultarPorUuid(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        AgendaDTO agenda = consultaAgenda.buscarAgendaPorUuid(uuid, usuarioAutenticado);
        return agendaMapper.paraResponse(agenda);
    }

    @GetMapping("/medico/{medicoUuid}")
    @ResponseStatus(HttpStatus.OK)
    public List<AgendaResponse> buscarPorMedico(@PathVariable UUID medicoUuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<AgendaDTO> agendas = consultaAgenda.buscarAgendasPorMedico(medicoUuid, usuarioAutenticado);
        return agendas.stream().map(agendaMapper::paraResponse).toList();
    }

    @GetMapping("/hospital/{hospitalUuid}")
    @ResponseStatus(HttpStatus.OK)
    public List<AgendaResponse> buscarPorHospital(@PathVariable UUID hospitalUuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<AgendaDTO> agendas = consultaAgenda.buscarAgendasPorHospital(hospitalUuid, usuarioAutenticado);
        return agendas.stream().map(agendaMapper::paraResponse).toList();
    }

    @PostMapping("/{uuid}/horarios")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionarHorarios(@PathVariable UUID uuid, @Valid @RequestBody AdicionarHorariosRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<HorarioAgendaCadastroDTO> horarios = horarioMapper.paraDTO(request.horarios());
        cadastroAgenda.adicionarHorarios(uuid, horarios, usuarioAutenticado);
    }

    @DeleteMapping("/{uuid}/horarios/{horarioUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerHorario(@PathVariable UUID uuid, @PathVariable UUID horarioUuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        cadastroAgenda.removerHorario(uuid, horarioUuid, usuarioAutenticado);
    }
}
