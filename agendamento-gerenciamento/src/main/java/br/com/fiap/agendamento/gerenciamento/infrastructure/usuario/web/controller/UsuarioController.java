package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.AdministradorCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.EnfermeiroCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.MedicoCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta.UsuarioDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoCadastroUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoConsultaUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.*;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper.UsuarioRequestMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper.UsuarioResponseMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final GestaoCadastroUsuario cadastroUsuario;
    private final GestaoConsultaUsuario consultaUsuario;
    private final UsuarioRequestMapper requestMapper;
    private final UsuarioResponseMapper responseMapper;
    private final SecurityContextProvider contextProvider;

    @PostMapping(value = "/pacientes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarPaciente(@RequestBody @Valid PacienteRequest request) {
        PacienteCadastroDTO pacienteDTO = requestMapper.paraDTO(request);
        Usuario usuario = cadastroUsuario.cadastrarPaciente(pacienteDTO);
        return requestMapper.paraResponse(usuario);
    }

    @PostMapping("/medicos")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarMedico(@RequestBody @Valid MedicoRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        MedicoCadastroDTO dto = requestMapper.paraDTO(request);
        Usuario usuario = cadastroUsuario.cadastrarMedico(dto, usuarioAutenticado);
        return requestMapper.paraResponse(usuario);
    }

    @PostMapping("/administrador")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarAdministrador(@RequestBody @Valid AdministradorRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        AdministradorCadastroDTO dto = requestMapper.paraDTO(request);
        Usuario usuario = cadastroUsuario.cadastrarAdministrador(dto, usuarioAutenticado);
        return requestMapper.paraResponse(usuario);
    }

    @PostMapping("/enfermeiro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarEnfermeiro(@RequestBody @Valid EnfermeiroRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        EnfermeiroCadastroDTO dto = requestMapper.paraDTO(request);
        Usuario usuario = cadastroUsuario.cadastrarEnfermeiro(dto, usuarioAutenticado);
        return requestMapper.paraResponse(usuario);
    }

    @GetMapping
    public List<UsuarioResponse> listarTodos() {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<UsuarioDTO> usuarios = consultaUsuario.listarUsuarios(usuarioAutenticado);
        return usuarios.stream().map(responseMapper::paraResponse).toList();
    }

    @GetMapping("/{uuid}")
    public UsuarioResponse consultarPorUuid(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        UsuarioDTO usuario = consultaUsuario.buscarUsuarioPorUuid(uuid, usuarioAutenticado);
        return responseMapper.paraResponse(usuario);
    }

}
