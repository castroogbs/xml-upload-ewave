<h1 align="center"> EWAVE - XML Upload Challenge </h1>

<p align="center">
    <img src="https://img.shields.io/badge/docker_build-ok-blue" />
    <img src="https://img.shields.io/badge/springboot-2-green" />
    <img src="https://img.shields.io/badge/maven-3-red" />
    <img src="https://img.shields.io/badge/Hibernate-ORM-orange" />
    <img src="https://img.shields.io/badge/DBMS-Oracle12c-orange" />
    <img src="https://img.shields.io/badge/angular-13-red" />
    <img src="https://img.shields.io/badge/angular material-13-red" />
    <img src="https://img.shields.io/badge/rxjs-7-red" />
    <img src="https://img.shields.io/badge/TypeScript-4-blue" />
</p>

<br>

## O que você irá encontrar?

Este projeto é composto por 3 serviços/containers Docker, gerenciados pelo `docker-compose`:
- Frontend: Feito em Angular, utilizando Angular Material e RxJS
- Backend: Feito em Java + SpringBoot
- Database: Oracle Database Enterprise Edition 12c

<br>

## Introdução:

### Frontend:
No frontend foram utilizados, os conceitos de `smart e dumb components` para dividir os componentes de acordo com suas responsabilidades. Sendo assim, na pasta `views`, ficaram os componentes "inteligentes", ou seja, aqueles que detêm informações e que irão passá-las para outros componentes. Já na pasta `components` ficaram os `dumb components` ou, os componentes cuja única função seria representar dados em tela.
Na pasta `models`, foram adicionadas as interfaces que representam os dados retornados pela API e na pasta `service`, os serviços que acessam a API propriamente dita, através de observables.

<p><u>Primeira forma de interação: </u></p>
Na interface o usuário irá encontrar duas formas de interação, a primeira através do upload de arquivos. Para isso, ele poderá clicar sobre o campo: **"Selecione os arquivos a serem importados:"**, após selecionar os arquivos ".xml" desejados e confirmar, poderá demorar alguns segundos até que o botão de `upload` fique disponível. (Esta demora ocorre porque o arquivo esta sendo processado, seguindo os requisitos)
<p align="center">
  <img src="https://user-images.githubusercontent.com/50057372/214128979-f9e4eef3-439e-4140-9971-0d20db0aec1b.png" width="1000" alt="" />
</p>

Feito isso, ele poderá clicar em upload e o procedimento irá começar. Note que, cada arquivo será enviado individualmente, e você poderá acompanha isso através da barra de progresso, que indicará o nome do arquivo que está sofrendo upload.

Após todos os arquivos terem sofrido upload, uma mensagem aparecerá, confirmando o sucesso da operação.

<p><u>Segunda forma de interação: </u></p>

Logo abaixo da sessão onde é realizado o upload dos arquivos, o usuário poderá pesquisar por uma das 4 regiões disponíveis no arquivo XML. São elas: N, NE, S ou SE.

Ao preencher o campo com a opção desejada, uma lista com 10 dos registros salvos no bando de dados será exibida.

<br>

### Backend:
No backend a ideia foi adaptar o padrão MVC utilizando conceitos de DDD, como os `repositories, services e entities`. Além disso, foram utilizados: JDBC e JPA para realizar a conexão com o Database.

A API disponibiliza 2 rotas:
- POST http://localhost:8080/api/v1/upload/file
    - Body: file (multipart/form-data)

- GET http://localhost:8080/api/v1/records/region/{name}
    - Route param: name

<br>

### Database:
No caso do database, optou-se por criar 2 tabelas. Uma para os `records (registros)` e outra para os `agents (agentes)`. Desta forma, caso em algum momento seja necessário adicionar mais informações para cada agente, isso poderá ser feito sem maiores problemas.

<br>

## Passo a passo de instalacao / uso:
1) Ir até o site [container-registry.oracle.com](http://container-registry.oracle.com), clicar em database e depois selecionar a versão enterprise

2) Aceitar os termos, para liberar o uso da imagem do database

    > esse passo a passo é necessário para que seja possível utilizar a `imagem 12.2.0.1-slim do Oracle Databe Enterprise Edition`
    > 

3) Feito isso, de volta ao terminal:

    No terminal rodar o comando `docker login container-registry.oracle.com` Utilizar o login e senha da conta da oracle utilizados para aceitar os termos no site.

4) Depois de efetuado o login, rodar o comando `docker-compose up -d`
    > ℹ️ Na primeira vez que o comando for executado, o container da api irá fechar devido a um problema nas informações de usuário e senha da conexão.
    >
    > Isso ocorre devido a senha que é definida automaticamente quando o container de database é inicializado pela primeira vez. Para resolver isso, seguir o passo a passo [Modificando a senha padrão para o usuário SYS](#modificando_o_usuario_sys).
    >

5)  Após finalizadas as configurações, executar o comando `docker container start xml-upload-api`

6) Neste ponto, os três containers já devem estar em funcionamento. Para realizar os testes, clique aqui: [acessar interface do projeto](http://localhost:8081)

<br>

<h3 id="modificando_o_usuario_sys">Modificando a senha padrão para o usuário SYS:<h3>

Depois que o container de database já estiver rodando e com status `healthy`, executar a sequencia de comandos abaixo para mudar a senha do usuário SYS

>ℹ️ Para saber se o container já está com status `healthy`, executar o comando `docker container ls`, e verificar se na colunas `STATUS` aparece a informação `healthy`.
>
>Caso apareca, continuar com o passo-a-passo, caso contrário, aguarde por alguns segundos e execute o comando `docker container ls` novamente.
>
>Faça isso, até que o status mude para `healthy`, quando isso acontecer você poderá prosseguir.
>

Acessar o terminal do container no modo interativo:

```bash
docker exec -it xml-upload-db /bin/bash
```

Logar no database como sysdba

```bash
sqlplus / as sysdba
```

Alterar a senha do usuário executando o comando abaixo:

```sql
ALTER USER SYS IDENTIFIED BY rootpass;
```

Feito isso, você já poderá fechar as duas instancias:
```bash
    exit
    exit
```

Agora ja será possível conectar no database do container e inicializar o container da API, através das informações abaixo:

> Note que, o container da API já está pré-configurado com as informações abaixo. Desde que o passo a passo seja seguido corretamente, nenhuma alteração a mais deve ser necessária.
>

Host: localhost

Port: 1521

Database: ORCLCDB `[ SID ]`

Username: SYS `Role: SYSDBA`

Password: rootpass


>ℹ️ Note que, a senha escolhida `rootpass` é a mesma senha definida no `application.properties` do backend java, sendo assim, é recomendado mantê-la como sugerido para evitar maiores problemas.
>