# Módulo Histórico

## Visão Geral

O módulo `historico` é responsável por fornecer uma camada de leitura sobre as consultas através de GraphQL. Este módulo **NÃO** possui sua própria tabela ou entidade persistente - ele lê diretamente da tabela `consulta` existente no módulo `gerenciamento`.

## Arquitetura

### Decisão Arquitetural

O projeto segue uma **única fonte de dados** para consultas:

- **Módulo gerenciamento**: Responsável pela criação, alteração e demais operações de negócio relacionadas às consultas (CRUD completo)
- **Módulo historico**: Responsável SOMENTE pela leitura das consultas através de GraphQL

Ambos os módulos utilizam a mesma tabela física `consulta`, porém:
- **gerenciamento**: Utiliza `ConsultaModel` para escrita
- **historico**: Utiliza `ConsultaReadModel` para leitura (entidade JPA própria do módulo historico)

**Importante**: O módulo `historico` NÃO depende de classes de infraestrutura do módulo `gerenciamento`. Ele possui sua própria entidade JPA (`ConsultaReadModel`) que mapeia diretamente à tabela `consulta` existente.

### Estrutura de Pacotes

```
agendamento-historico/
├── src/main/java/br/com/fiap/agendamento/historico/
│   ├── domain/consulta/
│   │   ├── entity/ConsultaHistorico.java (DTO/read model)
│   │   └── enums/StatusConsulta.java (enum local)
│   ├── application/consulta/
│   │   ├── ports/in/
│   │   │   ├── BuscarHistoricoPacienteUseCase.java
│   │   │   └── BuscarConsultasFuturasUseCase.java
│   │   ├── ports/out/
│   │   │   └── ConsultaReadPort.java
│   │   └── service/
│   │       ├── BuscarHistoricoPacienteService.java
│   │       └── BuscarConsultasFuturasService.java
│   └── infrastructure/
│       ├── persistence/
│       │   ├── model/ConsultaReadModel.java (entidade JPA própria)
│       │   ├── repository/ConsultaReadRepository.java
│       │   ├── adapter/ConsultaReadRepositoryAdapter.java
│       │   └── mapper/ConsultaHistoricoMapper.java
│       └── graphql/
│           ├── schema.graphqls
│           └── resolver/ConsultaHistoricoResolver.java
└── src/main/resources/
    └── graphql/schema.graphqls
```

### Fluxo de Dados

```
GraphQL Query
       ↓
ConsultaHistoricoResolver
       ↓
Use Case (BuscarHistoricoPacienteService / BuscarConsultasFuturasService)
       ↓
ConsultaReadPort
       ↓
ConsultaReadRepositoryAdapter
       ↓
ConsultaReadRepository (próprio do módulo historico)
       ↓
ConsultaReadModel (entidade JPA própria do historico)
       ↓
Tabela consulta
```

## GraphQL API

### Endpoint

- **URL**: `/graphql`
- **Método**: POST
- **Content-Type**: application/json

### Queries Disponíveis

#### 1. Histórico do Paciente

Retorna todas as consultas de um paciente.

```graphql
query {
    historicoPaciente(pacienteId: "uuid-do-paciente") {
        id
        pacienteId
        agendaId
        data
        horario
        status
        criadoEm
    }
}
```

#### 2. Consultas Futuras do Paciente

Retorna apenas as consultas futuras de um paciente (data/horário > data/horário atual).

```graphql
query {
    consultasFuturas(pacienteId: "uuid-do-paciente") {
        id
        pacienteId
        agendaId
        data
        horario
        status
    }
}
```

### Seleção Flexível de Campos

GraphQL permite selecionar apenas os campos necessários:

```graphql
query {
    historicoPaciente(pacienteId: "uuid-do-paciente") {
        id
        data
        horario
        status
    }
}
```

```graphql
query {
    consultasFuturas(pacienteId: "uuid-do-paciente") {
        id
        data
        horario
    }
}
```

### Tipos GraphQL

#### ConsultaHistorico

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | ID! | Identificador único da consulta |
| pacienteId | ID! | Identificador do paciente |
| agendaId | ID! | Identificador da agenda |
| data | String! | Data da consulta (ISO-8601) |
| horario | String! | Horário da consulta (ISO-8601) |
| status | String! | Status da consulta (AGENDADA, CONFIRMADA, CANCELADA, AUSENTE, REALIZADA) |
| criadoEm | String! | Data/hora de criação (ISO-8601) |

## Segurança

### Autorização

O módulo utiliza Spring Security com JWT. As regras de autorização são:

- **ROLE_ADMINISTRADOR**: Pode visualizar histórico de qualquer paciente
- **ROLE_ENFERMEIRO**: Pode visualizar histórico de qualquer paciente
- **ROLE_MEDICO**: Pode visualizar histórico de qualquer paciente
- **ROLE_PACIENTE**: Pode visualizar APENAS seu próprio histórico

### Validação

O GraphQL resolver valida se um paciente está tentando acessar o histórico de outro paciente. Se detectado, lança uma exceção.

## Casos de Uso

### BuscarHistoricoPacienteUseCase

Responsável por buscar todas as consultas de um paciente, independente do status ou data.

### BuscarConsultasFuturasUseCase

Responsável por buscar apenas as consultas futuras de um paciente. Uma consulta é considerada futura se:

- `data > data atual`, OU
- `data == data atual` E `horario > horario atual`

## Reutilização de Componentes

### ConsultaReadModel

O módulo historico possui sua própria entidade JPA `ConsultaReadModel` que mapeia diretamente à tabela `consulta` existente. Esta entidade:

- Contém apenas os campos necessários para leitura (id, pacienteId, agendaId, data, horario, status, criadoEm)
- Não possui relacionamentos LAZY (usa apenas IDs)
- É uma entidade de leitura simples e otimizada

### ConsultaReadRepository

O módulo historico possui seu próprio repository `ConsultaReadRepository` que:

- Estende `JpaRepository<ConsultaReadModel, UUID>`
- Possui método `findByPacienteId` para buscar consultas por paciente
- Não depende de repositories do módulo gerenciamento

### StatusConsulta

O enum `StatusConsulta` é definido localmente no módulo historico para evitar dependências. Os valores são idênticos aos do módulo gerenciamento para garantir consistência.

## Testes

O módulo possui testes para:

1. **Use Cases**: Testes unitários para `BuscarHistoricoPacienteUseCase` e `BuscarConsultasFuturasUseCase`
2. **Persistence Adapter**: Testes para `ConsultaReadRepositoryAdapter` incluindo lógica de filtragem de consultas futuras
3. **GraphQL**: Testes de integração para as queries GraphQL

## Migrações

O módulo historico **NÃO** possui migrations Flyway, pois não cria tabelas. A tabela `consulta` já existe no módulo gerenciamento.

## Dependências

- spring-boot-starter-graphql
- spring-boot-starter-graphql-test
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-test
- postgresql (runtime)
- mapstruct
- lombok

## Exemplos de Uso

### Exemplo 1: Histórico Completo

```graphql
query {
    historicoPaciente(pacienteId: "123e4567-e89b-12d3-a456-426614174000") {
        id
        pacienteId
        agendaId
        data
        horario
        status
        criadoEm
    }
}
```

Resposta:

```json
{
  "data": {
    "historicoPaciente": [
      {
        "id": "123e4567-e89b-12d3-a456-426614174001",
        "pacienteId": "123e4567-e89b-12d3-a456-426614174000",
        "agendaId": "123e4567-e89b-12d3-a456-426614174002",
        "data": "2026-09-20",
        "horario": "10:00:00",
        "status": "AGENDADA",
        "criadoEm": "2026-09-16T10:00:00"
      },
      {
        "id": "123e4567-e89b-12d3-a456-426614174003",
        "pacienteId": "123e4567-e89b-12d3-a456-426614174000",
        "agendaId": "123e4567-e89b-12d3-a456-426614174004",
        "data": "2026-09-15",
        "horario": "14:00:00",
        "status": "REALIZADA",
        "criadoEm": "2026-09-10T14:00:00"
      }
    ]
  }
}
```

### Exemplo 2: Consultas Futuras

```graphql
query {
    consultasFuturas(pacienteId: "123e4567-e89b-12d3-a456-426614174000") {
        id
        data
        horario
        status
    }
}
```

Resposta:

```json
{
  "data": {
    "consultasFuturas": [
      {
        "id": "123e4567-e89b-12d3-a456-426614174001",
        "data": "2026-09-20",
        "horario": "10:00:00",
        "status": "AGENDADA"
      }
    ]
  }
}
```

### Exemplo 3: Seleção Parcial de Campos

```graphql
query {
    historicoPaciente(pacienteId: "123e4567-e89b-12d3-a456-426614174000") {
        id
        data
        status
    }
}
```

Resposta:

```json
{
  "data": {
    "historicoPaciente": [
      {
        "id": "123e4567-e89b-12d3-a456-426614174001",
        "data": "2026-09-20",
        "status": "AGENDADA"
      }
    ]
  }
}
```

## Considerações Importantes

1. **Sem Duplicação de Dados**: Não existe tabela `consulta_historico` ou entidade `ConsultaHistoricoModel`
2. **Read-Only**: O módulo historico não realiza operações de escrita
3. **Single Source of Truth**: A tabela `consulta` é a única fonte de dados para ambos os módulos
4. **Desacoplamento**: O módulo historico NÃO depende de classes de infraestrutura do módulo gerenciamento
5. **Entidade Própria**: O módulo historico possui sua própria entidade JPA `ConsultaReadModel` que mapeia à tabela `consulta`
6. **Performance**: `ConsultaReadModel` não possui relacionamentos LAZY, evitando problemas de LazyInitializationException
7. **Segurança**: Integração com Spring Security existente no projeto
8. **Independência**: O módulo historico pode ser compilado e executado sem o código fonte do módulo gerenciamento
