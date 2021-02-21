# Rodando com Docker

Para criar a imagem docker é necessário que o Docker esteja instalado e autenticado.

Criar a imagem docker rodar:
```
mvn -f pom.xml -Pprod verify jib:dockerBuild -DskipTests
```

Para rodar:
```
docker-compose -f src/main/docker/app.yml up -d
```

> jib/entrypoint.sh

Caso tenha o erro "bash: /entrypoint.sh: /bin/sh^M: bad interpreter: No such file or directory" 

Deve-se rodar no gitbash na pasta em que o arquivo entrypoint.sh está o comando abaixo
```    
sed -i -e 's/\r$//' entrypoint.sh
```

Isto é necessário porque o arquivo tem que estar no formato do Linux.

Após ajustar o arquivo criar a imagem novamente.

## Enviando a imagem para o repositório

Rodar o comando:

``` 
docker push renatoalejusto/archstock:latest
``` 

Com isso a imagem irá estar disponível para implantação.

Puxa a imagem do repositório remoto para a máquina local:
``` 
docker pull [NOME_TAG_DOCKER_HUB]
``` 
Inicia o container para a imagem, inicia a aplicação de fato:
``` 
docker container run -it --name renatoalejusto/archstock -p 127.0.0.1:8081:8081 -e "SPRING_PROFILES_ACTIVE=dev" -v /logs/externalLogs/:/logs -d [NOME_TAG_DOCKER_HUB]
```
Exemplo:

``` 
docker container run -it --name renatoalejusto/archstock -p 127.0.0.1:8081:8081 -e "SPRING_PROFILES_ACTIVE=dev" -d archstock:latest 
``` 
Para validar se o container está rodando e pegar o nome do container, execute:
``` 
docker ps
``` 
Comando para parar a aplicação
``` 
docker stop archstock
``` 
Comando para remover o container
``` 
docker rm archstock
``` 

Comandos:
-p: Faz o bind para a porta local com a porta do servidor no Docker.
-d: Executa o container em background
--name: Nomeia o container, não é ideal quando subir em escala
