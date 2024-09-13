### Documentação da API
    - Disponível no endpoint http://localhost:8080/swagger-ui.html e http://localhost:8080/v3/api-docs

### Inicialização
- Inserir em application.yml os valores aws.access.key e aws.secret.key os valores da chave e secret da conta AWS
- Alterar a url das filas SQS em aws.sqs.fila.* para as disponibilizadas pela AWS
- Rodar o comando "docker compose up" na raiz do projeto para inicializar os containers da aplicação

### Testes da API
- Utilizar os valores "e154ddc7-48a8-4336-9199-73a56137e220" para codigo_vendedor e "a1e724f9-f98f-43ad-96b6-3d4c1256b164" para codigo_cobranca