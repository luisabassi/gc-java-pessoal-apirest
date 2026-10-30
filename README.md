# API REST Pokémon

API REST feita com Spring Boot que gerencia **Pokémons**, **Treinadores** e **Tipos**.
Os pokémons iniciais são importados da [PokeAPI](https://pokeapi.co/) e salvos em um banco H2 em memória, e as entidades se relacionam entre si.

Projeto remodelado a partir da API da aula anterior, que guardava os dados em uma lista em memória. Agora a API usa repositórios JPA e relacionamentos entre entidades.

## Tecnologias

- Java 21
- Spring Boot 4.1.1 (Web MVC, Data JPA, RestClient)
- H2 (banco em memória)
- Lombok
- Maven

## Modelo de dados

```
Treinador 1 ────── N Pokemon N ────── N Tipo
```

| Relacionamento | Tipo | Observação |
|---|---|---|
| Pokemon → Treinador | N:1 | O treinador é opcional (um pokémon pode estar sem treinador) |
| Pokemon ↔ Tipo | N:N | Tabela de junção `pokemon_tipo`; `Pokemon` é o lado dono |

## Estrutura do projeto

```
src/main/java/com/example/apirestwithspring
├── config         DataLoader (carga inicial)
├── controllers    PokemonController, TreinadorController, TipoController
├── dto            Requests e Responses (entidades não são expostas diretamente)
├── entities       Pokemon, Treinador, Tipo
├── repositories   PokemonRepository, TreinadorRepository, TipoRepository
└── services       PokemonService, TreinadorService, TipoService, PokeApiClient
```

## Como rodar

Pré-requisito: Java 21.

```bash
./mvnw spring-boot:run
```

No Windows: `mvnw.cmd spring-boot:run`. Também dá para rodar a classe `ApirestwithspringApplication` direto pela IDE.

A API sobe em `http://localhost:8080`.

Na inicialização, o `DataLoader` importa os 50 primeiros pokémons da PokeAPI (com seus tipos) e cria alguns treinadores de exemplo. É preciso ter internet para a importação. Sem ela, a aplicação sobe normalmente, mas sem os pokémons importados. Como o banco é em memória, os dados são recriados a cada execução.

## Endpoints

Base: `/pokeApi`

### Pokémons

| Método | Rota | Descrição |
|---|---|---|
| GET | `/pokemons` | Lista os pokémons. Filtro opcional: `?tipo=fire` |
| GET | `/pokemons/{id}` | Busca um pokémon por id |
| POST | `/pokemons` | Cria um pokémon |
| PUT | `/pokemons/{id}` | Atualiza um pokémon (inclusive trocar de treinador) |
| DELETE | `/pokemons/{id}` | Remove um pokémon |
| GET | `/pokemon/{nome}` | Consulta direta na PokeAPI (não usa o banco) |

Exemplo de corpo para `POST` e `PUT`:

```json
{
  "name": "meupokemon",
  "height": 7,
  "treinadorId": 1,
  "tipoIds": [1, 2]
}
```

### Treinadores

| Método | Rota | Descrição |
|---|---|---|
| GET | `/treinadores` | Lista os treinadores com seus pokémons |
| GET | `/treinadores/{id}` | Busca um treinador por id |
| GET | `/treinadores/{id}/pokemons` | Lista os pokémons de um treinador |
| POST | `/treinadores` | Cria um treinador (`{"nome": "...", "cidade": "..."}`) |
| PUT | `/treinadores/{id}` | Atualiza um treinador |
| DELETE | `/treinadores/{id}` | Remove um treinador |

### Tipos

| Método | Rota | Descrição |
|---|---|---|
| GET | `/tipos` | Lista os tipos |
| POST | `/tipos` | Cria um tipo (`{"nome": "..."}`) |
| DELETE | `/tipos/{id}` | Remove um tipo |

## Regras e códigos de resposta

- `404`: recurso não encontrado.
- `400`: dados inválidos, como `name` ausente ou `treinadorId`/`tipoIds` que não existem.
- `409`: tipo duplicado, ou tentativa de remover um tipo que está em uso por algum pokémon.
- Ao remover um treinador, os pokémons dele **não** são apagados e ficam sem treinador.

## Testes e commits

Antes de fazer o commit, testei tudo (endpoints, relacionamentos e casos de erro) e só depois commitei. Os commits foram feitos **por pacote** (entities, repositories, dto, services, controllers e config), para deixar o histórico mais organizado e fácil de acompanhar.
