package br.com.fiap.agendamento.gerenciamento.application.hospital.usecases;

import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.cadastro.HospitalCadastroDTO;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in.GestaoCadastroHospital;
import br.com.fiap.agendamento.gerenciamento.application.hospital.ports.out.HospitalRepository;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;

import java.util.UUID;

public class CadastroHospitalUseCase implements GestaoCadastroHospital {

    private final HospitalRepository hospitalRepository;

    public CadastroHospitalUseCase(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public Hospital cadastrar(HospitalCadastroDTO hospitalCadastroDTO) {
        Hospital hospital = new Hospital(
                UUID.randomUUID(),
                hospitalCadastroDTO.nome(),
                hospitalCadastroDTO.endereco(),
                hospitalCadastroDTO.telefone(),
                true,
                false,
                hospitalCadastroDTO.diaSemanaInicio(),
                hospitalCadastroDTO.diaSemanaFim(),
                hospitalCadastroDTO.horaInicio(),
                hospitalCadastroDTO.horaFim(),
                hospitalCadastroDTO.tempoLimiteCancelamento(),
                hospitalCadastroDTO.tempoToleranciaPosConsulta(),
                hospitalCadastroDTO.tempoMinimoConsulta()
        );

        return hospitalRepository.salvar(hospital);
    }
}
