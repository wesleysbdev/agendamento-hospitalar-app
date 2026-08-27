package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.MedicoCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoCadastroUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.MedicoRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.PacienteRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.UsuarioResponse;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper.UsuarioRequestMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final GestaoCadastroUsuario cadastroUsuario;
    private final UsuarioRequestMapper mapper;
    private final SecurityContextProvider contextProvider;

    @PostMapping(value = "/pacientes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarPaciente(@RequestBody @Valid PacienteRequest request) {
        PacienteCadastroDTO pacienteDTO = mapper.paraDTO(request);
        Usuario usuario = cadastroUsuario.cadastrarPaciente(pacienteDTO);
        return mapper.paraResponse(usuario);
    }

    @PostMapping("/medicos")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarMedico(@RequestBody @Valid MedicoRequest request) {
        MedicoCadastroDTO dto = mapper.paraDTO(request);
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        Usuario usuario = cadastroUsuario.cadastrarMedico(dto, usuarioAutenticado);
        return mapper.paraResponse(usuario);
    }

}
