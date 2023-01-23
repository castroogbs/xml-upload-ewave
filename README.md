
# Backend e Database do projeto XML-UPLOAD:

## Passo a passo de instalacao / uso:
Ir até o site [container-registry.oracle.com](http://container-registry.oracle.com), clicar em database e depois selecionar a versão enterprise

aceite os termos

feito isso, de volta ao terminal,

no terminal rodar o comando `docker login container-registry.oracle.com` , utilizar o login e senha da conta da oracle utilizados para aceitar os termos

depois de efetuado o login, rodar o comando `docker-compose up -d`

> esse passo a passo é necessário para que seja possível utilizar a `imagem 12.2.0.1-slim do Oracle Databe Enterprise Edition`
> 


> ℹ️ Na primeira vez que o comando for executado, o container da api irá fechar, devido a um problema nas informações de usuário e senha da conexão.
>
>Isso ocorre devido a senha que é definida automaticamente quando o container de database é inicializado pela primeira vez. Para resolver isso, seguir o passo a passo abaixo e, após finalizadas as configurações, executar o comando `docker container start xml-upload-api`.
>
>Desta vez, o container da API irá inicializar normalmente e a conexão será bem sucedida.
>

### modificando a senha padrão para o usuário SYS:

depois que o container de database já estiver rodando e com status `healthy`, executar a sequencia de comandos abaixo para mudar a senha do usuário SYS

>ℹ️ Para saber se o container já está com status `healthy`, executar o comando `docker container ls`, e verificar se na colunas `STATUS` aparece a informação `healthy`.
>
>Caso apareca, continuar com o passo-a-passo, caso contrário, aguarde por alguns segundos e execute o comando `docker container ls` novamente.
>
>Faça isso, até que o status mude para `healthy`, quando isso acontecer você poderá prosseguir.
>

conectar no terminal do container:

```bash
docker exec -it xml-upload-db /bin/bash
```

logar no database como sysdba

```bash
sqlplus / as sysdba
```

alterar a senha do usuário executando o comando abaixo:

```sql
ALTER USER SYS IDENTIFIED BY rootpass;
```

Feito isso, pode fechar as duas instancias:
```bash
    exit
    exit
```

Agora ja será possível conectar no database do container, através das informações abaixo:

Host: localhost

Port: 1521

Database: ORCLCDB `[ SID ]`

Username: SYS `Role: SYSDBA`

Password: rootpass


>ℹ️ Note que, a senha escolhida `rootpass` e a mesma senha definida no `application.properties` do backend java, sendo assim, e recomendado mante-la como sugerido para evitar maiores problemas.
>