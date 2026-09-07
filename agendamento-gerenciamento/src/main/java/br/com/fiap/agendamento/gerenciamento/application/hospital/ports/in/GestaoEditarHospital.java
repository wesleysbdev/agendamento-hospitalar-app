package br.com.fiap.agendamento.gerenciamento.application.hospital.ports.in;

import br.com.fiap.agendamento.gerenciamento.application.hospital.dto.edicao.HospitalEdicaoDTO;
import br.com.fiap.agendamento.gerenciamento.domain.hospital.entity.Hospital;

import java.util.UUID;

public interface GestaoEditarHospital {
    Hospital alterarDadosHospital(HospitalEdicaoDTO hospitalEdicaoDTO);

    Hospital ativarHospital(UUID hospitalUuid);

    Hospital inativarHospital(UUID hospitalUuid);

    Hospital excluirHospital(UUID hospitalUuid);
}
