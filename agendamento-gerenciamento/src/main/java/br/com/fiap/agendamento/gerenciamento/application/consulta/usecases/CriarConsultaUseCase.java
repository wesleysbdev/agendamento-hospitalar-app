package br.com.fiap.agendamento.gerenciamento.application.consulta.usecases;

import br.com.fiap.agendamento.gerenciamento.application.consulta.dto.ConsultaEvent;
import br.com.fiap.agendamento.gerenciamento.application.consulta.enums.ConsultaEventType;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.in.GestaoCadastroConsulta;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaEventPublisher;
import br.com.fiap.agendamento.gerenciamento.application.consulta.ports.out.ConsultaRepository;
import br.com.fiap.agendamento.gerenciamento.domain.consulta.entity.Consulta;

public class CriarConsultaUseCase implements GestaoCadastroConsulta {

    private final ConsultaRepository consultaRepository;
    private final ConsultaEventPublisher consultaEventPublisher;

    public CriarConsultaUseCase(
            ConsultaRepository consultaRepository,
            ConsultaEventPublisher consultaEventPublisher
    ) {
        this.consultaRepository = consultaRepository;
        this.consultaEventPublisher = consultaEventPublisher;
    }

    @Override
    public Consulta cadastrarConsulta(Consulta consulta) {
        Consulta consultaSalva = consultaRepository.salvar(consulta);
        consultaEventPublisher.publicar(
                new ConsultaEvent(
                        ConsultaEventType.CONSULTA_CRIADA,
                        consultaSalva.getUuid(),
                        consultaSalva.getPaciente().getUuid(),
                        consultaSalva.getHorario()
                )
        );
        return consultaSalva;
    }
}
