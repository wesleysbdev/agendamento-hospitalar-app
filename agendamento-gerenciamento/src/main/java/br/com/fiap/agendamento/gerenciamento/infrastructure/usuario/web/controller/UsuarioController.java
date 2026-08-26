package br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.usuario.dto.cadastro.PacienteCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.in.GestaoCadastroUsuario;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.dto.PacienteRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.usuario.web.mapper.UsuarioRequestMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final GestaoCadastroUsuario cadastroUsuario;
    private final UsuarioRequestMapper mapper;

    @PostMapping("/pacientes")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarPaciente(@RequestBody @Valid PacienteRequest request) {
        PacienteCadastroDTO pacienteDTO = mapper.toDTO(request);
        cadastroUsuario.cadastrarPaciente(pacienteDTO);
    }

}
