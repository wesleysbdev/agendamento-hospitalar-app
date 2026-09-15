package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.AdministradorCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.EnfermeiroCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.MedicoCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.consulta.UsuarioDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.edicao.*;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoCadastroUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoConsultaUsuario;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoEditarUsuario;
import br.com.fiap.agendamento.gerenciamento.domain.usuario.entity.Usuario;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.UsuarioResponse;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.cadastro.AdministradorCadastroRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.cadastro.EnfermeiroCadastroRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.cadastro.MedicoCadastroRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.cadastro.PacienteCadastroRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.edicao.*;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final GestaoCadastroUsuario cadastroUsuario;
    private final GestaoEditarUsuario edicaoUsuario;
    private final GestaoConsultaUsuario consultaUsuario;
    private final UsuarioMapper mapper;
    private final SecurityContextProvider contextProvider;

    @PostMapping(value = "/pacientes")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarPaciente(@RequestBody @Valid PacienteCadastroRequest request) {
        PacienteCadastroDTO pacienteDTO = mapper.paraDTO(request);
        Usuario usuario = cadastroUsuario.cadastrarPaciente(pacienteDTO);
        return mapper.paraResponse(usuario);
    }

    @PostMapping("/medicos")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarMedico(@RequestBody @Valid MedicoCadastroRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        MedicoCadastroDTO dto = mapper.paraDTO(request);
        Usuario usuario = cadastroUsuario.cadastrarMedico(dto, usuarioAutenticado);
        return mapper.paraResponse(usuario);
    }

    @PostMapping("/administradores")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarAdministrador(@RequestBody @Valid AdministradorCadastroRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        AdministradorCadastroDTO dto = mapper.paraDTO(request);
        Usuario usuario = cadastroUsuario.cadastrarAdministrador(dto, usuarioAutenticado);
        return mapper.paraResponse(usuario);
    }

    @PostMapping("/enfermeiros")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastrarEnfermeiro(@RequestBody @Valid EnfermeiroCadastroRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        EnfermeiroCadastroDTO dto = mapper.paraDTO(request);
        Usuario usuario = cadastroUsuario.cadastrarEnfermeiro(dto, usuarioAutenticado);
        return mapper.paraResponse(usuario);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponse> listarTodos() {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<UsuarioDTO> usuarios = consultaUsuario.listarUsuarios(usuarioAutenticado);
        return usuarios.stream().map(mapper::paraResponse).toList();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse consultarPorId(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        UsuarioDTO usuario = consultaUsuario.buscarUsuarioPorId(uuid, usuarioAutenticado);
        return mapper.paraResponse(usuario);
    }

    @PutMapping(value = "/pacientes/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse editarPaciente(@PathVariable UUID uuid, @RequestBody @Valid PacienteEdicaoRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        PacienteEdicaoDTO pacienteDTO = mapper.paraDTO(request);
        Usuario usuario = edicaoUsuario.alterarDadosPaciente(uuid, pacienteDTO, usuarioAutenticado);
        return mapper.paraResponse(usuario);
    }

    @PutMapping("/medicos/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse editarMedico(@PathVariable UUID uuid, @RequestBody @Valid MedicoEdicaoRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        MedicoEdicaoDTO dto = mapper.paraDTO(request);
        Usuario usuario = edicaoUsuario.alterarDadosMedico(uuid, dto, usuarioAutenticado);
        return mapper.paraResponse(usuario);
    }

    @PutMapping("/administrador/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse editarAdministrador(@PathVariable UUID uuid, @RequestBody @Valid AdministradorEdicaoRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        AdministradorEdicaoDTO dto = mapper.paraDTO(request);
        Usuario usuario = edicaoUsuario.alterarDadosAdministrador(uuid, dto, usuarioAutenticado);
        return mapper.paraResponse(usuario);
    }

    @PutMapping("/enfermeiro/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse editarEnfermeiro(@PathVariable UUID uuid, @RequestBody @Valid EnfermeiroEdicaoRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        EnfermeiroEdicaoDTO dto = mapper.paraDTO(request);
        Usuario usuario = edicaoUsuario.alterarDadosEnfermeiro(uuid, dto, usuarioAutenticado);
        return mapper.paraResponse(usuario);
    }

    @PatchMapping("/{uuid}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable UUID uuid, @RequestBody @Valid AlterarSenhaRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        edicaoUsuario.alterarSenhaUsuario(
                uuid,
                new AlteracaoSenhaDTO(request.novaSenha()),
                usuarioAutenticado
        );
    }

    @PatchMapping("/{uuid}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarUsuario(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        edicaoUsuario.ativarUsuario(uuid, usuarioAutenticado);
    }

    @PatchMapping("/{uuid}/inativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarUsuario(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        edicaoUsuario.inativarUsuario(uuid, usuarioAutenticado);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        edicaoUsuario.excluirUsuario(uuid, usuarioAutenticado);
    }

}
