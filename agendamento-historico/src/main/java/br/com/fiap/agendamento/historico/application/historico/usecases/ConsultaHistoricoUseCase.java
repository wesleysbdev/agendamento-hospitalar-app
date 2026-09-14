package br.com.fiap.agendamento.historico.application.historico.usecases;

import br.com.fiap.agendamento.historico.application.historico.dto.HistoricoConsultaDTO;
import br.com.fiap.agendamento.historico.application.historico.dto.HospitalHistoricoDTO;
import br.com.fiap.agendamento.historico.application.historico.dto.MedicoHistoricoDTO;
import br.com.fiap.agendamento.historico.application.historico.dto.PacienteHistoricoDTO;
import br.com.fiap.agendamento.historico.application.historico.ports.in.ConsultaHistorico;
import br.com.fiap.agendamento.historico.application.historico.ports.out.HistoricoRepository;
import br.com.fiap.agendamento.historico.domain.historico.entity.HistoricoConsulta;
import br.com.fiap.agendamento.historico.domain.historico.exception.HistoricoNaoEncontradoException;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ConsultaHistoricoUseCase implements ConsultaHistorico {

    private final HistoricoRepository historicoRepository;

    @Override
    public List<HistoricoConsultaDTO> buscarHistoricoPaciente(UUID pacienteUuid) {
        List<HistoricoConsulta> historico = historicoRepository.buscarPorPacienteUuid(pacienteUuid);

        if (historico.isEmpty()) {
            throw new HistoricoNaoEncontradoException("Histórico não encontrado para o paciente: " + pacienteUuid);
        }

        return historico.stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistoricoConsultaDTO> buscarConsultasFuturas(UUID pacienteUuid) {
        LocalDateTime agora = LocalDateTime.now();
        List<HistoricoConsulta> consultasFuturas = historicoRepository.buscarPorPacienteUuidEHorarioPosterior(pacienteUuid, agora);

        if (consultasFuturas.isEmpty()) {
            throw new HistoricoNaoEncontradoException("Não há consultas futuras para o paciente: " + pacienteUuid);
        }

        return consultasFuturas.stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    private HistoricoConsultaDTO paraDTO(HistoricoConsulta historico) {
        PacienteHistoricoDTO pacienteDTO = new PacienteHistoricoDTO(
                historico.getPaciente().getUuid(),
                historico.getPaciente().getNome()
        );

        MedicoHistoricoDTO medicoDTO = new MedicoHistoricoDTO(
                historico.getMedico().getUuid(),
                historico.getMedico().getNome(),
                historico.getMedico().getCrm()
        );

        HospitalHistoricoDTO hospitalDTO = new HospitalHistoricoDTO(
                historico.getHospital().getUuid(),
                historico.getHospital().getNome(),
                historico.getHospital().getEndereco()
        );

        return new HistoricoConsultaDTO(
                historico.getConsultaUuid(),
                historico.getHorario(),
                historico.getEstado(),
                pacienteDTO,
                medicoDTO,
                hospitalDTO
        );
    }
}
