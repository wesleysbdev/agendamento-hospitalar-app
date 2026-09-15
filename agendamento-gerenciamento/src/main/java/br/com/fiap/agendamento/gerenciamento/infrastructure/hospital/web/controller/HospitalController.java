package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.HospitalDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoCadastroHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoConsultaHospital;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalResponse;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.mapper.HospitalMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("hospitais")
@AllArgsConstructor
public class HospitalController {

    private final GestaoCadastroHospital cadastroHospital;
    private final GestaoConsultaHospital consultaHospital;
    private final HospitalMapper mapper;
    private final SecurityContextProvider contextProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HospitalResponse cadastrarHospital(@RequestBody @Valid HospitalRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        var hospitalCadastroDTO = mapper.paraDTO(request);
        Hospital hospital = cadastroHospital.cadastrar(hospitalCadastroDTO, usuarioAutenticado);
        return mapper.paraResponse(hospital);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HospitalResponse> listarHospitais() {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<HospitalDTO> hospitais = consultaHospital.listarHospitais(usuarioAutenticado);
        return hospitais.stream().map(mapper::paraResponse).toList();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public HospitalResponse consultarPorUuid(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        HospitalDTO hospital = consultaHospital.buscarHospitalPorUuid(uuid, usuarioAutenticado);
        return mapper.paraResponse(hospital);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public HospitalResponse alterarHospital(@PathVariable UUID uuid, @RequestBody @Valid HospitalRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        var hospitalDTO = mapper.paraDTO(request);
        Hospital hospital = cadastroHospital.alterarDadosHospital(uuid, hospitalDTO, usuarioAutenticado);
        return mapper.paraResponse(hospital);
    }

    @PatchMapping("/{uuid}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarHospital(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        cadastroHospital.ativarHospital(uuid, usuarioAutenticado);
    }

    @PatchMapping("/{uuid}/inativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarHospital(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        cadastroHospital.inativarHospital(uuid, usuarioAutenticado);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirHospital(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        cadastroHospital.excluirHospital(uuid, usuarioAutenticado);
    }
}
