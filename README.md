# Sistema Bancário Digital

Sistema bancário baseado em microsserviços desenvolvido em Java + Spring Boot para gerenciamento de clientes, contas e transações bancárias.

## Arquitetura

O sistema foi dividido em 3 microsserviços:

### customer-service
Responsável por:
- Cadastro de clientes
- Remoção de clientes
- Validação de CPF
- Garantia de CPF único
- Consulta de cliente por CPF

### account-service
Responsável por:
- Criação de conta por CPF
- Consulta de saldo
- Consulta de conta
- Bloqueio/desbloqueio
- Atualização de saldo
- Comunicação com customer-service

### transaction-service
Responsável por:
- Depósito
- Saque
- Extrato por período
- Controle de limite diário
- Validação de saldo
- Impedir saldo negativo
- Comunicação com account-service

---

# Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- Docker
- Docker Compose
- Swagger / OpenAPI
- Apidog
- Lombok
- Validation API

---

# Pré-requisitos

Instalar:

### Java 21

Verificar:

```bash
java -version
```

---

### Maven

Verificar:

```bash
mvn -version
```

---

### Docker Desktop

Verificar:

```bash
docker --version
docker compose version
```

---

### VS Code (opcional)

Extensões recomendadas:

- Extension Pack for Java
- Spring Boot Extension Pack
- Lombok Support

---

# Configuração do ambiente

Clone:

```bash
git clone [LINK_REPOSITORIO]
```

Entrar:

```bash
cd sistema-banco-d2w
```

---

# Configurar variáveis de ambiente

Criar arquivo:

```txt
.env
```

Conteúdo:

```env
DB_NAME=di2win_bank
DB_USER=di2win
DB_PASSWORD=di2win123

CUSTOMER_SERVICE_URL=http://customer-service:8081
ACCOUNT_SERVICE_URL=http://account-service:8082
```

---

# Rodar aplicação com Docker

Construir e subir:

```bash
docker compose up --build
```

ou:

```bash
docker compose up --build -d
```

Parar:

```bash
docker compose down
```

Apagar volumes:

```bash
docker compose down -v
```

---

# Serviços disponíveis

## Customer Service

Porta:

```txt
8081
```

Swagger:

```txt
http://localhost:8081/swagger-ui/index.html
```

OpenAPI:

```txt
http://localhost:8081/v3/api-docs
```

---

## Account Service

Porta:

```txt
8082
```

Swagger:

```txt
http://localhost:8082/swagger-ui/index.html
```

OpenAPI:

```txt
http://localhost:8082/v3/api-docs
```

---

## Transaction Service

Porta:

```txt
8083
```

Swagger:

```txt
http://localhost:8083/swagger-ui/index.html
```

OpenAPI:

```txt
http://localhost:8083/v3/api-docs
```

---

# Testes da API

Os endpoints foram organizados e documentados no Apidog.

Projeto:

```txt
[4kn4ft8v8b.apidog.io]
```

IMPORTANTE:

A senha do projeto do Apidog será enviada via e-mail.

Para acessar corretamente a documentação e utilizar recursos do Apidog via navegador, instalar a extensão:

Apidog Browser Extension:

https://chromewebstore.google.com/

(ou pesquisar "Apidog" na Chrome Web Store)

A extensão auxilia em autenticação, testes e sincronização do workspace.

---

# Fluxos implementados

## Cliente

- Criar cliente
- Buscar cliente por CPF
- Remover cliente
- Validar CPF
- Impedir CPF duplicado

---

## Conta

- Criar conta usando CPF
- Consultar saldo
- Consultar dados da conta
- Bloquear conta
- Desbloquear conta

---

## Transações

### Depósito

Permitido apenas para:

- Conta ativa
- Conta desbloqueada

---

### Saque

Permitido apenas para:

- Conta ativa
- Conta desbloqueada
- Saldo suficiente
- Limite diário respeitado

---

### Extrato

Consulta por:

- Data inicial
- Data final

---

# Regras de negócio implementadas

- CPF obrigatório
- CPF válido
- CPF único
- Conta não pode ficar negativa
- Limite diário de saque
- Conta bloqueável/desbloqueável
- Conta criada apenas para clientes existentes

---

# Estrutura do projeto

```txt
sistema-banco-d2w/
│
├── customer-service/
├── account-service/
├── transaction-service/
├── docker-compose.yml
├── .env
└── README.md
```

---

# Observações

Este projeto foi desenvolvido utilizando arquitetura baseada em microsserviços, separando responsabilidades entre clientes, contas e transações para facilitar escalabilidade, manutenção e evolução futura do sistema.
