-- Table: public.notificacao
CREATE TABLE  public.notificacao
(
    id uuid NOT NULL,
    assunto character varying(200) COLLATE pg_catalog."default" NOT NULL,
    criado_em timestamp(6) without time zone NOT NULL,
    data_envio timestamp(6) without time zone,
    destinatario character varying(200) COLLATE pg_catalog."default" NOT NULL,
    enviada boolean NOT NULL,
    mensagem text COLLATE pg_catalog."default" NOT NULL,
    tipo character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT notificacao_pkey PRIMARY KEY (id),
    CONSTRAINT notificacao_tipo_check CHECK (tipo::text = ANY (ARRAY['EMAIL'::character varying, 'SMS'::character varying, 'PUSH'::character varying]::text[])),
    CONSTRAINT ck_notificacao_data_envio CHECK (enviada = false OR data_envio IS NOT NULL)
    );