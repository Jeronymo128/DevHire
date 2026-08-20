# DevHire

DevHire é uma API REST para uma plataforma de recrutamento de desenvolvedores. Atualmente, o backend oferece cadastro de usuários, verificação de credenciais e gerenciamento de vagas. A aplicação foi desenvolvida com Spring Boot e utiliza PostgreSQL para persistência de dados.

Este repositório contém apenas o backend. Tokens de autenticação, regras de autorização e fluxos de candidatura a vagas ainda não estão implementados.

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Spring Web
- Spring Data JPA
- Spring Security
- Jakarta Bean Validation
- PostgreSQL
- Hash de senhas com BCrypt
- Maven

## Arquitetura

A aplicação segue uma arquitetura em camadas:

- **Controllers** expõem os endpoints REST e validam os corpos das requisições recebidas.
- **Services** concentram as regras de negócio de usuários, login e vagas, além de mapear entidades para DTOs de resposta.
- **Repositories** utilizam Spring Data JPA para acessar o banco de dados.
- **Models** representam as entidades `users` e `jobs` do banco de dados.
- **DTOs** definem os payloads de entrada e saída da API, mantendo os detalhes das entidades fora do contrato público.
- **Exception handling** centraliza as respostas para erros de validação, requisições malformadas, e-mails duplicados e credenciais inválidas.
- **Configuration** fornece a codificação de senhas com BCrypt e a configuração atual do filtro do Spring Security.

## Funcionalidades implementadas

- Cadastro de usuários com os perfis `CANDIDATE` ou `COMPANY`
- Validação do formato de e-mail e dos campos obrigatórios
- Detecção de e-mails duplicados
- Hash de senhas com BCrypt antes da persistência
- Verificação de credenciais por e-mail e senha
- Respostas de usuário sem exposição do hash da senha
- Criação, listagem, consulta, atualização e exclusão de vagas
- Tipos de vaga: `FULL_TIME`, `PART_TIME`, `INTERNSHIP` e `CONTRACT`
- Status de vaga: `OPEN` e `CLOSED`
- Tratamento consistente de erros de validação, JSON malformado, e-mails duplicados e credenciais inválidas

> Atualmente, o endpoint de login verifica as credenciais e retorna os dados do usuário. Ele não cria uma sessão nem emite um token. Todos os endpoints são públicos porque a autorização ainda não foi implementada.

## Endpoints da API

Por padrão, a API é executada em `http://localhost:8080`.

### Usuários e autenticação

| Método | Endpoint | Descrição | Sucesso |
| --- | --- | --- | --- |
| `POST` | `/api/users` | Cadastra um usuário | `200 OK` |
| `POST` | `/api/auth/login` | Verifica e-mail e senha | `200 OK` |

#### Cadastrar um usuário

```http
POST /api/users
Content-Type: application/json
```

```json
{
  "name": "Alex Morgan",
  "email": "alex@example.com",
  "password": "change-me",
  "role": "CANDIDATE"
}
```

Resposta de sucesso:

```json
{
  "id": 1,
  "name": "Alex Morgan",
  "email": "alex@example.com",
  "role": "CANDIDATE"
}
```

Os perfis permitidos são `CANDIDATE` e `COMPANY`. O cadastro de um e-mail já existente retorna `409 Conflict`.

#### Verificar credenciais

```http
POST /api/auth/login
Content-Type: application/json
```

```json
{
  "email": "alex@example.com",
  "password": "change-me"
}
```

Credenciais válidas retornam a mesma estrutura de resposta de usuário apresentada acima. Credenciais inválidas retornam `401 Unauthorized`.

### Vagas

| Método | Endpoint | Descrição | Sucesso |
| --- | --- | --- | --- |
| `GET` | `/api/jobs` | Lista todas as vagas | `200 OK` |
| `POST` | `/api/jobs` | Cria uma vaga | `200 OK` |
| `GET` | `/api/jobs/{id}` | Consulta uma vaga pelo ID | `200 OK` |
| `PUT` | `/api/jobs/{id}` | Substitui os dados atuais de uma vaga | `200 OK` |
| `DELETE` | `/api/jobs/{id}` | Exclui uma vaga | `204 No Content` |

As operações `GET`, `PUT` e `DELETE` para um ID de vaga inexistente retornam `404 Not Found`, quando aplicável.

#### Criar ou atualizar uma vaga

`POST /api/jobs` e `PUT /api/jobs/{id}` utilizam o seguinte corpo de requisição:

```json
{
  "title": "Junior Java Developer",
  "description": "Build and maintain backend services.",
  "location": "São Paulo, Brazil",
  "type": "FULL_TIME",
  "status": "OPEN"
}
```

Todos os campos são obrigatórios. Os valores permitidos para `type` são `FULL_TIME`, `PART_TIME`, `INTERNSHIP` e `CONTRACT`; os valores permitidos para `status` são `OPEN` e `CLOSED`.

Uma resposta de sucesso inclui o ID gerado ou já existente da vaga:

```json
{
  "id": 1,
  "title": "Junior Java Developer",
  "description": "Build and maintain backend services.",
  "location": "São Paulo, Brazil",
  "type": "FULL_TIME",
  "status": "OPEN"
}
```

### Respostas de erro

Erros de validação da requisição retornam `400 Bad Request` como um mapa de campos e mensagens:

```json
{
  "email": "Email must be valid",
  "password": "Password is required"
}
```

JSON malformado ou valores inválidos para enums também retornam `400 Bad Request`:

```json
{
  "error": "Invalid request data"
}
```

## Estrutura do projeto

```text
src/main/
├── java/com/devhire/
│   ├── config/       # Configuração de segurança e do password encoder
│   ├── controller/   # Controllers REST
│   ├── dto/          # Contratos de requisição e resposta
│   ├── enums/        # Perfis de usuário, tipos e status de vaga
│   ├── exception/    # Exceção customizada e tratamento global de erros da API
│   ├── model/        # Entidades JPA
│   ├── repository/   # Repositories do Spring Data JPA
│   ├── service/      # Regras de negócio e mapeamento de DTOs
│   └── DevHireApplication.java
└── resources/
    └── application.properties
```

## Requisitos

- JDK 21
- Maven 3.9 ou superior
- Uma instância do PostgreSQL em execução

## Executando localmente

1. Clone o repositório e acesse o diretório do projeto:

   ```bash
   git clone <repository-url>
   cd DevHire
   ```

2. Crie o banco de dados PostgreSQL esperado pela aplicação:

   ```sql
   CREATE DATABASE devhire;
   ```

3. Verifique se as configurações do banco de dados em `src/main/resources/application.properties` correspondem ao seu ambiente local. A configuração atual utiliza:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/devhire
   spring.datasource.username=postgres
   spring.datasource.password=${DB_PASSWORD}
   ```

4. Defina a variável de ambiente `DB_PASSWORD` com a senha do seu usuário do PostgreSQL.

   PowerShell:

   ```powershell
   $env:DB_PASSWORD="your-postgresql-password"
   ```

   Bash:

   ```bash
   export DB_PASSWORD="your-postgresql-password"
   ```

5. Inicie a aplicação:

   ```bash
   mvn spring-boot:run
   ```

A API estará disponível em `http://localhost:8080`. O Hibernate está configurado com `spring.jpa.hibernate.ddl-auto=update`, portanto as tabelas necessárias são criadas ou atualizadas durante a inicialização da aplicação.

## Roadmap

- Autenticação com JWT
- Autorização baseada em perfis
- Candidaturas a vagas
- Testes automatizados
- Documentação com Swagger/OpenAPI
