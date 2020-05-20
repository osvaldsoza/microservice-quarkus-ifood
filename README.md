# microservice-quarkus-ifood
## A aplicação irá possuir 3 microserviços:
# Cadastro:
  Restaurante;
  Pratos;
# Marktplace:
  Realizará os pedidos;
# Pedidos:
  Confirmar pedidos;
  Pedidos anteriores;
  Pedidos em andamento;
  Entrega;
### Stacks ###
Keyclock - para autenticação dos usuários
Postgres
MongoDb
Flyway - migração e evolução do banco
Vert.X - serviço reativos
Tracing com Jaeger
Debezium, Kafkak e Quarkus - para conectar os serviços
MapStruct - para tratamento dos DTO's
Pronetheus e Grafana - para gráfico e dashboards
Docker
