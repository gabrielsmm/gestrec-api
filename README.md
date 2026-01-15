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
- Banco de dados H2
- Maven

---

## 7. Funcionalidades Principais

- Cadastro de tipos de recursos
- Cadastro de recursos
- Criação de reservas
- Cancelamento de reservas
- Consulta de reservas por recurso e período

### Regras de Negócio

- Não permitir reservas com conflito de horário para o mesmo recurso
- Recursos inativos não podem ser reservados
- A data de início da reserva deve ser anterior à data de fim

---

## 8. Instruções para Execução

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

## 9. Integrantes

- Gabriel da Silva Mendes de Moraes