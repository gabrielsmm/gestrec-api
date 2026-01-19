# Gestor de Reservas de Recursos (GestRec)

## 1. Descrição do Problema

Em diversos contextos organizacionais existe a necessidade de gerenciar a reserva de recursos compartilhados, como salas de reunião, equipamentos ou outros ativos.  
Quando esse controle é feito de forma manual ou descentralizada, surgem problemas como conflitos de horário, falta de rastreabilidade e dificuldade de manutenção das informações.

O sistema **Gestor de Recursos** foi proposto para resolver esse problema de forma simples e organizada, centralizando o controle de recursos e suas respectivas reservas.

---

## 2. Objetivo do Sistema

O objetivo do sistema é permitir o **cadastro, consulta e reserva de recursos**, garantindo que as regras de negócio sejam respeitadas, como evitar conflitos de horário e impedir a reserva de recursos inativos.

---

## 3. Estilo Arquitetural Adotado

O sistema foi desenvolvido utilizando o estilo arquitetural **Monolítico**, organizado internamente segundo os princípios da **Clean Architecture**.

### Justificativa

- O escopo do projeto é pequeno e bem definido.
- Um monólito reduz a complexidade de infraestrutura.
- A Clean Architecture permite separar claramente regras de negócio de detalhes técnicos, mesmo em um sistema simples.
- Facilita testes, manutenção e evolução futura.

---

## 4. Arquitetura do Sistema

A arquitetura segue os princípios da **Clean Architecture**, organizando o sistema em camadas com dependências direcionadas para o núcleo do domínio.

A aplicação é organizada nas seguintes camadas:

- **Domain**: contém o modelo de domínio e regras de negócio centrais, sem dependência de frameworks.
- **Application (Use Cases)**: orquestra os casos de uso e aplica regras de negócio de forma coordenada.
- **Adapters**: fazem a adaptação entre o mundo externo (HTTP, banco de dados, segurança) e o domínio.
- **Infrastructure**: concentra configurações técnicas e integração com frameworks.

### Diagrama Simplificado



---

## 5. Decisões Arquiteturais

### Clean Architecture

Foi adotada para garantir:
- Separação clara de responsabilidades
- Baixo acoplamento entre camadas
- Independência das regras de negócio em relação a frameworks

### DDD (Domain-Driven Design)

Conceitos de DDD foram aplicados de forma leve:
- Modelagem explícita do domínio
- Entidades representando conceitos do mundo real (Recurso, Reserva, TipoRecurso)
- Regras de negócio centralizadas no domínio

### SOLID

Os princípios SOLID foram utilizados como boas práticas no design do código:
- Classes com responsabilidades bem definidas
- Dependência de abstrações
- Facilidade de manutenção e evolução

### Alternativas Consideradas

- Arquitetura em microserviços foi considerada, mas descartada devido ao escopo reduzido do projeto e ao aumento de complexidade desnecessário.
- Uma arquitetura em camadas tradicional foi considerada, porém a Clean Architecture oferece melhor controle de dependências.

---

## 6. Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- Swagger / OpenAPI
- H2 Database
- Maven
- Lombok
- MapStruct

---

## 7. Funcionalidades Principais

- Cadastro de tipos de recursos
- Cadastro de recursos
- Criação de reservas
- Cancelamento de reservas
- Consulta de reservas por recurso e período
- Autenticação de usuários via JWT
- Autorização baseada em perfil de usuário
- Proteção de endpoints sensíveis

### Regras de Negócio

- Não permitir reservas com conflito de horário para o mesmo recurso
- Recursos inativos não podem ser reservados
- A data de início da reserva deve ser anterior à data de fim
- Apenas usuários autenticados podem realizar reservas
- Determinadas operações são restritas a perfis específicos

---

## 8. Segurança

A aplicação utiliza autenticação baseada em JWT (JSON Web Token).

- O login gera um token de acesso
- O token deve ser enviado no header Authorization
- O controle de acesso é realizado com base no perfil do usuário
- A segurança é integrada ao Spring Security

## 9. Instruções para Execução

Clonar o repositório

`git clone https://github.com/gabrielsmm/gestrec-api.git`

Importar o projeto na IDE de preferência (`IntelliJ IDEA` / `Eclipse`)

Executar a aplicação Spring Boot:

- via IDE: executar a classe com `@SpringBootApplication`
- via terminal: `mvn spring-boot:run`

Acessar a API (exemplos):

- `http://localhost:8080/recursos`
- `http://localhost:8080/reservas`
- `http://localhost:8080/tipos-recursos`

## 10. Integrantes

- Gabriel da Silva Mendes de Moraes