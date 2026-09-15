-- Table: public.usuario
CREATE TABLE public.usuario
(
    tipo character varying(31) COLLATE pg_catalog."default" NOT NULL,
    id uuid NOT NULL,
    ativo boolean NOT NULL,
    criado_em timestamp(6) without time zone NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    excluido boolean NOT NULL,
    nome character varying(200) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(255) COLLATE pg_catalog."default" NOT NULL,
    crm character varying(10) COLLATE pg_catalog."default",
    telefone character varying(13) COLLATE pg_catalog."default",
    CONSTRAINT usuario_pkey PRIMARY KEY (id),
    CONSTRAINT uk_usuario_crm UNIQUE (crm),
    CONSTRAINT uk_usuario_email UNIQUE (email),
    CONSTRAINT usuario_tipo_check CHECK (tipo::text = ANY (ARRAY['ADMINISTRADOR'::character varying, 'ENFERMEIRO'::character varying, 'MEDICO'::character varying, 'PACIENTE'::character varying]::text[]))
    );

-- Table: public.hospital
CREATE TABLE public.hospital
(
    id uuid NOT NULL,
    ativo boolean NOT NULL,
    dia_semana_fim character varying(9) COLLATE pg_catalog."default" NOT NULL,
    dia_semana_inicio character varying(9) COLLATE pg_catalog."default" NOT NULL,
    endereco character varying(255) COLLATE pg_catalog."default" NOT NULL,
    excluido boolean NOT NULL,
    hora_fim time(0) without time zone NOT NULL,
    hora_inicio time(0) without time zone NOT NULL,
    nome character varying(200) COLLATE pg_catalog."default" NOT NULL,
    telefone character varying(20) COLLATE pg_catalog."default" NOT NULL,
    tempo_limite_cancelamento_minutos integer NOT NULL,
    tempo_minimo_consulta_minutos integer NOT NULL,
    tempo_tolerancia_pos_consulta_minutos integer NOT NULL,
    CONSTRAINT hospital_pkey PRIMARY KEY (id),
    CONSTRAINT hospital_dia_semana_fim_check CHECK (dia_semana_fim::text = ANY (ARRAY['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying]::text[])),
    CONSTRAINT hospital_dia_semana_inicio_check CHECK (dia_semana_inicio::text = ANY (ARRAY['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying]::text[])),
    CONSTRAINT ck_hospital_tempo_limite_cancelamento CHECK (tempo_limite_cancelamento_minutos >= 0),
    CONSTRAINT ck_hospital_tempo_tolerancia CHECK (tempo_tolerancia_pos_consulta_minutos >= 0),
    CONSTRAINT ck_hospital_tempo_minimo_consulta CHECK (tempo_minimo_consulta_minutos > 0),
    CONSTRAINT ck_hospital_horario CHECK (hora_fim > hora_inicio)
);

-- Table: public.agenda
CREATE TABLE public.agenda
(
    id uuid NOT NULL,
    criado_em timestamp(6) without time zone NOT NULL,
    hospital_id uuid NOT NULL,
    medico_id uuid NOT NULL,
    CONSTRAINT agenda_pkey PRIMARY KEY (id),
    CONSTRAINT fk_agenda_hospital FOREIGN KEY (hospital_id)
    REFERENCES public.hospital (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION,
    CONSTRAINT fk_agenda_medico FOREIGN KEY (medico_id)
    REFERENCES public.usuario (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION,
    CONSTRAINT uk_agenda_medico_hospital UNIQUE (medico_id, hospital_id)
    );

-- Table: public.agenda_horario
CREATE TABLE public.agenda_horario
(
    id uuid NOT NULL,
    criado_em timestamp(6) without time zone NOT NULL,
    dia_semana character varying(9) COLLATE pg_catalog."default" NOT NULL,
    horario time(0) without time zone NOT NULL,
    agenda_id uuid NOT NULL,
    CONSTRAINT agenda_horario_pkey PRIMARY KEY (id),
    CONSTRAINT uk_agenda_horario_dia_horario UNIQUE (agenda_id, dia_semana, horario),
    CONSTRAINT fk_agenda_horario_agenda FOREIGN KEY (agenda_id)
    REFERENCES public.agenda (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE CASCADE,
    CONSTRAINT agenda_horario_dia_semana_check CHECK (dia_semana::text = ANY (ARRAY['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying]::text[]))
    );

-- Table: public.consulta
CREATE TABLE public.consulta
(
    id uuid NOT NULL,
    criado_em timestamp(6) without time zone NOT NULL,
    data date NOT NULL,
    horario time(0) without time zone NOT NULL,
    status character varying(10) COLLATE pg_catalog."default" NOT NULL,
    agenda_id uuid NOT NULL,
    paciente_id uuid NOT NULL,
    CONSTRAINT consulta_pkey PRIMARY KEY (id),
    CONSTRAINT fk_consulta_agenda FOREIGN KEY (agenda_id)
    REFERENCES public.agenda (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION,
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id)
    REFERENCES public.usuario (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION,
    CONSTRAINT consulta_status_check CHECK (status::text = ANY (ARRAY['AGENDADA'::character varying, 'CONFIRMADA'::character varying, 'CANCELADA'::character varying, 'AUSENTE'::character varying, 'REALIZADA'::character varying]::text[]))
    );

-- index: uk_consulta_horario_ativo
CREATE UNIQUE INDEX uk_consulta_horario_ativo
    ON consulta (agenda_id, data, horario)
    WHERE status IN ('AGENDADA', 'CONFIRMADA');