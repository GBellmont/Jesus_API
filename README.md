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
- Controller: **LivrosController**
- Endpoint: **GET** - `htt://localhost:8090/jesus-api/livros`

- **Request**: `SEM_REQUISICAO`

- **Response**:
```json
[
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
]
```

- Diagrama:
![GET-livros](Diagramas/GET-livros.png)
