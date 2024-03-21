
# Web Fórum gameficado

Implementação de uma API REST para um web fórum com cadastro de usuários, criação de tópicos, adição de comentários, curtidas e um sistema de gamificação baseado na interação dos usuários com estes elementos do fórum conduzido através do acúmulo de pontos e obtenção de badges exclusivas ao alcançar determinadas condições ou estágios.

## Padrões de projeto utilizados

- **Singleton + Strategy + Static Factory Method**: Para centralizar a obtenção da instância de armazenamento dos achievements e desacoplar a estratégia de armazenamento, fornecendo uma interface única e de fácil utilização.
- **Template Method**: Para diferenciar como cada subclasse de Achievement deveria se comportar ao ser adicionada.
- **Proxy**: Para invocação das funcionalidades de gamificação de forma coesa e transparente à aplicação.
- **Observer**: Para monitoramento do estado dos pontos do usuário e obtenção das devidas badges ao aumular determinada quantidade de pontos (INVENTOR e PART OF THE COMMUNITY)

## Database

H2 console endpoint:

http://localhost:8080/h2

## Testes

Testes unitários e de integração nas camadas de repository, service e controller (nesta utilizando o MockMvc)

## Documentação

Springdoc-openapi Swagger-ui endpoint:

http://localhost:8080/swagger-ui
