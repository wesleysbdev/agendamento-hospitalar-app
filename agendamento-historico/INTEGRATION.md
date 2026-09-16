# Integração do Módulo de Histórico - agendamento-historico

## Visão Geral

Este módulo fornece funcionalidades de consulta de histórico de atendimentos médicos através de GraphQL, seguindo os princípios de Clean Architecture.

## Estrutura do Módulo

```
agendamento-historico/
├── src/main/java/br/com/fiap/agendamento/historico/
│   ├── domain/                    # Camada de Domínio
│   │   ├── consulta/
│   │   │   ├── entity/           # ConsultaHistorico
│   │   │   ├── enums/            # StatusConsulta
│   │   │   └── ports/out/        # ConsultaHistoricoRepositoryPort
│   │   ├── usuario/
│   │   │   └── entity/           # PacienteHistorico, MedicoHistorico
│   │   └── hospital/
│   │       └── entity/           # HospitalHistorico
│   ├── application/              # Camada de Aplicação (Use Cases)
│   │   └── consulta/
│   │       ├── ports/in/         # Interfaces de Use Cases
│   │       └── usecases/         # Implementações dos Use Cases
│   └── infrastructure/           # Camada de Infraestrutura
│       ├── config/               # Configuração Spring
│       ├── graphql/              # GraphQL Resolvers e DTOs
│       │   ├── dto/
│       │   ├── mapper/
│       │   └── resolver/
│       └── persistence/          # Acesso a dados
│           ├── adapter/
│           ├── mapper/
│           ├── model/
│           └── repository/
└── src/main/resources/
    └── graphql/
        └── schema.graphqls       # Schema GraphQL
```

## Integração com o Módulo Bootstrap

### 1. Adicionar Dependência no pom.xml do Bootstrap

No arquivo `agendamento-bootstrap/pom.xml`, adicione:

```xml
<dependency>
    <groupId>br.com.fiap</groupId>
    <artifactId>agendamento-historico</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. Configuração do Component Scan

Certifique-se de que a classe principal do módulo bootstrap (`AgendamentoApplication`) inclua o pacote do módulo de histórico no `@ComponentScan`:

```java
@SpringBootApplication
@ComponentScan(basePackages = {
    "br.com.fiap.agendamento.bootstrap",
    "br.com.fiap.agendamento.gerenciamento",
    "br.com.fiap.agendamento.historico",
    "br.com.fiap.agendamento.notificacao"
})
@EntityScan(basePackages = {
    "br.com.fiap.agendamento.gerenciamento.infrastructure",
    "br.com.fiap.agendamento.historico.infrastructure"
})
@EnableJpaRepositories(basePackages = {
    "br.com.fiap.agendamento.gerenciamento.infrastructure",
    "br.com.fiap.agendamento.historico.infrastructure"
})
public class AgendamentoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgendamentoApplication.class, args);
    }
}
```

### 3. Configuração de Segurança

O módulo utiliza `@PreAuthorize` nos resolvers GraphQL. Certifique-se de que o módulo bootstrap possui a configuração de segurança do Spring Security habilitada com os roles necessários:
- `MEDICO`
- `ENFERMEIRO`
- `PACIENTE`

## Queries GraphQL Disponíveis

Após a integração, as seguintes queries estarão disponíveis:

```graphql
type Query {
    historicoPorPaciente(pacienteId: ID!): [Consulta]
    consultasFuturasPorPaciente(pacienteId: ID!): [Consulta]
    consultasPassadasPorPaciente(pacienteId: ID!): [Consulta]
    consultaPorId(id: ID!): Consulta
}
```

### Exemplo de Uso

```graphql
query {
    historicoPorPaciente(pacienteId: "uuid-do-paciente") {
        id
        data
        horario
        status
        paciente {
            nome
            email
            telefone
        }
        medico {
            nome
            email
        }
        hospital {
            nome
        }
    }
}
```

## Acesso ao Banco de Dados

O módulo de histórico **NÃO cria novas tabelas**. Ele utiliza as tabelas existentes do módulo de gerenciamento:
- `consulta`
- `usuario` (com discriminador para PACIENTE, MEDICO)
- `agenda`
- `hospital`

O acesso é feito através de queries JPQL que fazem JOIN entre as tabelas existentes.

## Independência de Módulos

Este módulo é **totalmente independente** e não possui dependências diretas de outros módulos de negócio (agendamento, notificacao, etc.). A única dependência é através do banco de dados compartilhado.

## Testes

Para testar o módulo isoladamente, utilize o banco de dados H2 configurado no scope de test:

```bash
mvn test -pl agendamento-historico
```

## Considerações Importantes

1. **Apenas Leitura**: Este módulo fornece apenas queries de leitura. Não há mutations para criação/edição de consultas (essas operações pertencem ao módulo de gerenciamento).

2. **Controle de Acesso**: As queries estão protegidas por `@PreAuthorize` garantindo que apenas usuários autenticados com os roles apropriados possam acessar o histórico.

3. **Performance**: As queries utilizam JOINs otimizados para minimizar o número de acessos ao banco de dados.
