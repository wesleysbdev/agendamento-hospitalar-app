package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.controller;

import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.consulta.HospitalDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoCadastroHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoConsultaHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoEditarHospital;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;
import br.com.fiap.agendamento.gerenciamento.infrastructure.config.security.SecurityContextProvider;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalEdicaoRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalRequest;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.dto.HospitalResponse;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.mapper.HospitalRequestMapper;
import br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.web.mapper.HospitalResponseMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("hospitais")
@AllArgsConstructor
public class HospitalController {

    private final GestaoCadastroHospital cadastroHospital;
    private final GestaoConsultaHospital consultaHospital;
    private final GestaoEditarHospital editarHospital;
    private final HospitalRequestMapper requestMapper;
    private final HospitalResponseMapper responseMapper;
    private final SecurityContextProvider contextProvider;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HospitalResponse cadastrarHospital(@RequestBody @Valid HospitalRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        var hospitalCadastroDTO = requestMapper.paraDTO(request);
        Hospital hospital = cadastroHospital.cadastrar(hospitalCadastroDTO);
        return requestMapper.paraResponse(hospital);
    }

    @GetMapping
    public List<HospitalResponse> listarHospitais() {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        List<HospitalDTO> hospitais = consultaHospital.listarHospitais();
        return hospitais.stream().map(responseMapper::paraResponse).toList();
    }

    @GetMapping("/{uuid}")
    public HospitalResponse consultarPorUuid(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        HospitalDTO hospital = consultaHospital.buscarHospitalPorUuid(uuid);
        return responseMapper.paraResponse(hospital);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public HospitalResponse alterarHospital(@RequestBody @Valid HospitalRequest request) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        var hospitalEdicaoDTO = requestMapper.paraDTO(request);
//        Hospital hospital = editarHospital.alterarDadosHospital(hospitalEdicaoDTO);
        return requestMapper.paraResponse(hospital);
    }

    @PatchMapping("/{uuid}/ativar")
    public HospitalResponse ativarHospital(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        Hospital hospital = editarHospital.ativarHospital(uuid);
        return requestMapper.paraResponse(hospital);
    }

    @PatchMapping("/{uuid}/inativar")
    public HospitalResponse inativarHospital(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        Hospital hospital = editarHospital.inativarHospital(uuid);
        return requestMapper.paraResponse(hospital);
    }

    @DeleteMapping("/{uuid}")
    public HospitalResponse excluirHospital(@PathVariable UUID uuid) {
        UsuarioAutenticado usuarioAutenticado = contextProvider.obterUsuarioAutenticado();
        Hospital hospital = editarHospital.excluirHospital(uuid);
        return requestMapper.paraResponse(hospital);
    }
}
