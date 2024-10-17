## Como rodar o projeto

### Para rodar o projeto, siga os passos abaixo:

- Na pasta raiz do projeto (greenway-app), terá um Dockerfile que irá conter todas as informações necessárias para rodar a aplicação, basta executar os comando abaixo para criar e executar a imagem da aplicação:
    ```bash 
    docker build -t greenway-app .
    ``` 
- Na mesma pasta irei deixar o arquivo "**_insomnia-trabalho-fiap.json_**" com as rotas da aplicação, basta importar o arquivo no insomnia e utilizar as rotas para testar a aplicação.

- Verifique nas rotas os ids utilizados nas requisições para garantir que os dados estão sendo passados corretamente para evitar erros.

- Para conectar no banco de dados localmente basta cadastrar a conexão no DBeaver com as seguintes informações:
    - Host: db-fiap-api-dev.postgres.database.azure.com
    - Port: 5432
    - Database: postgres
    - User: postgres
    - Password: Fiap@2024