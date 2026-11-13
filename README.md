# Minhas Finanças: API (Spring Boot)

API REST de controle financeiro pessoal: cadastro e autenticação de usuários, lançamentos de receitas e despesas,
mudança de status (pendente, efetivado, cancelado) e cálculo de saldo. Feita a partir de um curso de Spring Boot com React.

**Front-end (React):** [DennerRR/minhasFinancas-app](https://github.com/DennerRR/minhasFinancas-app)

## Endpoints principais

| Método | Rota | O que faz |
|---|---|---|
| POST | `/api/usuarios/salvar` | Cadastra um usuário |
| POST | `/api/usuarios/autenticar` | Faz login |
| GET | `/api/usuarios/saldo/{id}` | Saldo do usuário |
| POST | `/api/lancamentos/salvar` | Cria um lançamento |
| PUT | `/api/lancamentos/atualizar/{id}` | Edita um lançamento |
| PUT | `/api/lancamentos/atualizarStatus/{id}` | Muda o status |
| DELETE | `/api/lancamentos/deletar/{id}` | Exclui um lançamento |

A documentação interativa fica no Swagger (`/swagger-ui.html`) com a API rodando.

## Qualidade

Testes automatizados com JUnit e o perfil `test` usando banco H2 em memória: repositórios, serviços e o recurso de usuários.

```bash
cd minhasfinancas && ./mvnw test
```

## Como executar

Pré-requisitos: Java 8 ou superior e PostgreSQL (o projeto usa a porta 5433 e o banco `minhasfinancas`).

```bash
export DB_USER=postgres
export DB_PASSWORD=sua_senha
cd minhasfinancas && ./mvnw spring-boot:run
```

**Tecnologias:** Java, Spring Boot 2.5, Spring Data JPA, PostgreSQL, H2, Lombok, Swagger (Springfox), JUnit.
