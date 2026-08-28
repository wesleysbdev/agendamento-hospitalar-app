package br.com.fiap.agendamento.gerenciamento.infrastructure.hospital.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

//@Entity
//@Table(name = "hospital")
//@Getter
//@Setter
public class HospitalModel {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_seq")
//    @SequenceGenerator(name = "hospital_seq", sequenceName = "hospital_id_seq", allocationSize = 1)
//    private Long id;
//
//    @Column(nullable = false, unique = true, length = 36)
//    private UUID uuid;
//
//    @Column(nullable = false, length = 200)
//    private String nome;
//
//    @Column(nullable = false, length = 255)
//    private String endereco;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "dia_semana_inicio", nullable = false, length = 20)
//    private DayOfWeek diaSemanaInicio;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "dia_semana_fim", nullable = false, length = 20)
//    private DayOfWeek diaSemanaFim;
//
//    @Column(name = "hora_inicio", nullable = false)
//    private LocalTime horaInicio;
//
//    @Column(name = "hora_fim", nullable = false)
//    private LocalTime horaFim;
//
//    @Column(name = "tempo_limite_cancelamento_minutos", nullable = false)
//    private Integer tempoLimiteCancelamentoMinutos;
//
//    @Column(name = "tempo_tolerancia_pos_consulta_minutos", nullable = false)
//    private Integer tempoToleranciaPosConsultaMinutos;
//
//    @Column(name = "tempo_minimo_consulta_minutos", nullable = false)
//    private Integer tempoMinimoConsultaMinutos;
//
//    @Column(nullable = false)
//    private boolean ativo;
}
