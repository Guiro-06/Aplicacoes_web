# Aplicacoes Web - Imagem Pecas

Back-end desenvolvido seguindo os roteiros das Aulas 03, 06, 07 e 08.

## Tecnologias

- Java 21+
- Spring Boot 4.1.0
- Spring Web
- Spring Data JPA / Hibernate
- PostgreSQL 16
- Docker Compose
- pgAdmin
- Lombok

## Como executar

1. Abra o Docker Desktop.
2. Na raiz do projeto, execute:

```bash
docker compose up -d
```

3. Abra o projeto no IntelliJ e execute `ImagemPecasApplication`.

4. A API ficará disponível em:

```text
http://localhost:8080
```

## Endpoints

### Upload

```text
POST /v1/images
```

`multipart/form-data` com:

- `file`
- `name`
- `tags`

### Buscar imagens

```text
GET /v1/images
GET /v1/images?extension=JPG
GET /v1/images?query=teste
GET /v1/images?extension=JPG&query=teste
```

### Buscar/renderizar por ID

```text
GET /v1/images/{id}
```

## pgAdmin

```text
http://localhost:15432
```

Login: `admin@admin.com`

Senha: `admin`

Servidor PostgreSQL:

- Host: `db`
- Port: `5432`
- Database: `imagelite`
- User: `postgres`
- Password: `postgres`
