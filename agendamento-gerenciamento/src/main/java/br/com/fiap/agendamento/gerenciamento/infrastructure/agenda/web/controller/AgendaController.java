package br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoCadastroAgenda;
import br.com.fiap.agendamento.gerenciamento.application.agenda.ports.in.GestaoConsultaAgenda;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.mapper.AgendaRequestMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.agenda.web.mapper.AgendaResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("agendas")
@AllArgsConstructor
public class AgendaController {

    private final GestaoCadastroAgenda cadastroAgenda;
    private final GestaoConsultaAgenda consultaAgenda;
    private final AgendaRequestMapper requestMapper;
    private final AgendaResponseMapper responseMapper;
    private final SecurityContextProvider contextProvider;

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public AgendaResponse cadastrarAgenda(@RequestBody @Valid AgendaRequest request) {
//        var agendaCadastroDTO = requestMapper.paraDTO(request);
//        Agenda agenda = cadastroAgenda.cadastrar(agendaCadastroDTO);
//        return requestMapper.paraResponse(agenda);
//    }
//
//    @GetMapping
//    public List<AgendaResponse> listarAgendas() {
//        List<AgendaDTO> agendas = consultaAgenda.listarAgendas();
//        return agendas.stream().map(responseMapper::paraResponse).toList();
//    }
//
//    @GetMapping("/{uuid}")
//    public AgendaResponse consultarPorUuid(@PathVariable UUID uuid) {
//        AgendaDTO agenda = consultaAgenda.buscarAgendaPorUuid(uuid);
//        return responseMapper.paraResponse(agenda);
//    }
//
//    @GetMapping("/medico/{medicoUuid}")
//    public List<AgendaResponse> buscarPorMedico(@PathVariable UUID medicoUuid) {
//        List<AgendaDTO> agendas = consultaAgenda.buscarAgendasPorMedico(medicoUuid);
//        return agendas.stream().map(responseMapper::paraResponse).toList();
//    }
//
//    @GetMapping("/hospital/{hospitalUuid}")
//    public List<AgendaResponse> buscarPorHospital(@PathVariable UUID hospitalUuid) {
//        List<AgendaDTO> agendas = consultaAgenda.buscarAgendasPorHospital(hospitalUuid);
//        return agendas.stream().map(responseMapper::paraResponse).toList();
//    }
}
