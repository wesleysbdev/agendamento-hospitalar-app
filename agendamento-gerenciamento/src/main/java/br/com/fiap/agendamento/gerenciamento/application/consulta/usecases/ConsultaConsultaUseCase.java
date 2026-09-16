package br.com.fiap.agendamento.gerenciamento.application.consulta.usecases;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaDTO;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoConsultaConsulta;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.application.dto.UsuarioAutenticado;
import br.com.fiap.agendamento.gerenciamento.application.usuario.ports.out.UsuarioRepository;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.exception.ConsultaNaoEncontradaException;

import java.util.List;
import java.util.UUID;

public class ConsultaConsultaUseCase implements GestaoConsultaConsulta {

    private final ConsultaRepository repository;
    private final UsuarioRepository usuarioRepository;

    public ConsultaConsultaUseCase(ConsultaRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<ConsultaDTO> listarConsultar(UsuarioAutenticado usuarioAutenticado) {
        return repository.buscarTodos();
    }

    @Override
    public ConsultaDTO buscarPorId(UUID id, UsuarioAutenticado usuarioAutenticado) {
        Consulta consulta = buscarConsultaPorId(id);
        return converterParaDTO(consulta);
    }

    @Override
    public List<ConsultaDTO> listarConsultasPorPaciente(UUID id, UsuarioAutenticado usuarioAutenticado) {
        return repository.buscarPorPacienteId(id);
    }

    @Override
    public List<ConsultaDTO> listarConsultasMedico(UUID id, UsuarioAutenticado usuarioAutenticado) {
        return repository.buscarPorMedicoId(id);
    }

    private Consulta buscarConsultaPorId(UUID id) {
        return repository.buscarPorId(id).orElseThrow(() -> new ConsultaNaoEncontradaException());
    }

    private ConsultaDTO converterParaDTO(Consulta consulta) {
        return new ConsultaDTO(
                consulta.getId(),
                consulta.getData().atTime(consulta.getHorario()),
                consulta.getAgenda().getMedico().getNome(),
                consulta.getAgenda().getHospital().getNome(),
                consulta.getAgenda().getHospital().getEndereco(),
                consulta.getStatus()
        );
    }
}
