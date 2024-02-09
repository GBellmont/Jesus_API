# Jesus API

### Decrição
Projeto de manuseio e distribuição de serviços para aplicativos cristãos.

---

### Container(s):
```text
- REDIS -> docker run -d --name redis -p 6379:6379 redis
```

#### Executando redis-cli no container `redis`:
Após a execução do comando que roda o container `redis` no item **Container(s)** deve se executar o seguinte comando:
```text
docker exec -it redis bash
```

Assim você estará dentro do container do redis e poderá executar:
````text
redis-cli
````

Após isto alguns comandos que podemser úteis:
- keys * -> lista as chaves atuais salvas no redis;
- get CHAVE -> lista as propriedades da chave informada;
- del CHAVE, ...CHAVE -> exclui as chaves informadas;

---

### Serviços

#### [PAGINADO]Consulta de Livros da Bíblia
- Controller: **LivrosController**
- Endpoint: **GET** - `htt://localhost:8090/jesus-api/livros/{index}/{numeroItens}`

- **Path Variable(s)**:
    - index - Integer - _Número da página_
    - numeroItens - Integer - _Número de itens por página_

- **Response**:
```json
{
    "index": 0,
    "numeroItens": 3,
    "itens": [
        {
            "abreviacao": {
                "portugues": "gn",
                "ingles": "gn"
            },
            "autor": "Moisés",
            "numeroCapitulos": 50,
            "grupo": "Pentateuco",
            "testamento": "VT",
            "nome": "Gênesis"
        },
        {...}
    ],
    "primeiraPagina": true,
    "ultimaPagina": false
}
```

- Diagrama:
![GET-livros](Diagramas/GET-livros.png)


#### Consulta de Livro da Bíblia Por Abreviação
- Controller: **LivrosController**
- Endpoint: **GET** - `htt://localhost:8090/jesus-api/livros/{abreviacao}`

- **Path Variable(s)**: 
   - abreviacao - String - _Dado fornecido no endpoint de listagem de livros_ 

- **Response**:
```json
{
    "abreviacao": {
        "portugues": "gn",
        "ingles": "gn"
    },
    "autor": "Moisés",
    "numeroCapitulos": 50,
    "comentario": "Autor: Uma vez que este livro anônimo integra o Pentateuco unificado,...",
    "grupo": "Pentateuco",
    "nome": "Gênesis",
    "testamento": "VT"
}
```

- Diagrama:
![GET-livros-abreviacao](Diagramas/GET-livros-abreviacao.png)


#### Consulta de Versões da Bíblia
- Controller: **VersoesController**
- Endpoint: **GET** - `htt://localhost:8090/jesus-api/versoes`

- **Path Variable(s)**: NO_PATH_VARIABLES

- **Response**:
```json
{
  "versoes": [
    {
      "codigoVersao": "acf",
      "numeroVersos": 31106,
      "nomeCompleto": "Almeida Corrigida Fiel",
      "descricao": "A versão Almeida Corrigida Fiel | acf (revisado segundo o novo acordo ortográfico da língua portuguesa em 2011) é uma tradução fiel do Velho Testamento em Hebraico (Massorético) e do Novo Testamento em Grego (Textus Receptus), segundo o método de tradução formal (traduzindo cada palavra e mantendo a beleza linguística)."
    },
    {...}
  ]
}
```

- Diagrama:
![GET-versoes](Diagramas/GET-versoes.png)
