-- SEQUENCE: public.notificacao_id_seq
CREATE SEQUENCE public.notificacao_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Table: public.notificacao
CREATE TABLE  public.notificacao
(
    id bigint NOT NULL DEFAULT nextval('public.notificacao_id_seq'),
    assunto character varying(200) COLLATE pg_catalog."default" NOT NULL,
    criado_em timestamp(6) without time zone NOT NULL,
    data_envio timestamp(6) without time zone,
    destinatario character varying(200) COLLATE pg_catalog."default" NOT NULL,
    enviada boolean NOT NULL,
    mensagem text COLLATE pg_catalog."default" NOT NULL,
    tipo character varying(20) COLLATE pg_catalog."default" NOT NULL,
    uuid uuid NOT NULL,
    CONSTRAINT notificacao_pkey PRIMARY KEY (id),
    CONSTRAINT uk_notificacao_uuid UNIQUE (uuid),
    CONSTRAINT notificacao_tipo_check CHECK (tipo::text = ANY (ARRAY['EMAIL'::character varying, 'SMS'::character varying, 'PUSH'::character varying]::text[])),
    CONSTRAINT ck_notificacao_data_envio CHECK (enviada = false OR data_envio IS NOT NULL)
    );