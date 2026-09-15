package br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaDTO;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoCadastroConsulta;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoConsultaConsulta;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto.ConsultaRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.dto.ConsultaResponse;
import br.com.fiap.agendamento.gerenciamento.infrastructure.consulta.web.mapper.ConsultaMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("consulta")
@AllArgsConstructor
public class ConsultaController {

    private final GestaoCadastroConsulta cadastroConsulta;
    private final GestaoConsultaConsulta gestaoConsulta;
    private final ConsultaMapper mapper;
    private final SecurityContextProvider contextProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse agendarConsulta(@RequestBody @Valid ConsultaRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        ConsultaCadastroDTO consultaDTO = mapper.paraDTO(request);
        Consulta consulta = cadastroConsulta.cadastrarConsulta(consultaDTO, usuarioAutenticado);
        return mapper.paraResponse(consulta);
    }

    @PostMapping("{uuid}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ConsultaResponse cancelarConsulta(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        Consulta consulta = cadastroConsulta.cancelarConsulta(uuid, usuarioAutenticado);
        return mapper.paraResponse(consulta);
    }

    @PostMapping("{uuid}/confirmar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ConsultaResponse confirmarConsulta(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        Consulta consulta = cadastroConsulta.confirmarConsulta(uuid, usuarioAutenticado);
        return mapper.paraResponse(consulta);
    }

    @PostMapping("{uuid}/realizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ConsultaResponse realizarConsulta(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        Consulta consulta = cadastroConsulta.realizarConsulta(uuid, usuarioAutenticado);
        return mapper.paraResponse(consulta);
    }

    @PostMapping("{uuid}/ausentar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ConsultaResponse marcarComoAusente(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        Consulta consulta = cadastroConsulta.marcarComoAusente(uuid, usuarioAutenticado);
        return mapper.paraResponse(consulta);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaResponse> consulta() {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<ConsultaDTO> consultas = gestaoConsulta.listarConsultar(usuarioAutenticado);
        return consultas.stream().map(mapper::paraResponse).toList();
    }

    @GetMapping("{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaResponse consulta(@RequestParam UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        ConsultaDTO consulta = gestaoConsulta.buscarPorId(uuid, usuarioAutenticado);
        return mapper.paraResponse(consulta);
    }

    @GetMapping("pacientes/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaResponse> consultaPorPaciente(@RequestParam UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<ConsultaDTO> consultas = gestaoConsulta.listarConsultasPorPaciente(uuid, usuarioAutenticado);
        return consultas.stream().map(mapper::paraResponse).toList();
    }

    @GetMapping("medicos/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaResponse> consultaPorMedico(@RequestParam UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<ConsultaDTO> consultas = gestaoConsulta.listarConsultasMedico(uuid, usuarioAutenticado);
        return consultas.stream().map(mapper::paraResponse).toList();
    }

}
