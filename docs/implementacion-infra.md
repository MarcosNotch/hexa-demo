[< Inicio](../README.md)
# Implementacion en infra

En el ejemplo se pueden encontrar 
[configuraciones a distintas bases de datos](../src/main/java/com/example/demo/shared/infra/config/db).
Para el versionado de las mismas se utiliza liquibase. 
[Configuracion para distintas bases de datos](../src/main/java/com/example/demo/shared/infra/config/db/LiquibaseConfig.java)

En cuanto a la persistencia se utiliza JPA para modelar las entidades y conectarse a BBDD.
los nombres de tablas y campos deben ser en ***snake_case***

En este paquete tambien podemos encontrar la implementacion de CommandBus y QueryBus
(
[CommandBusImpl](../src/main/java/com/example/demo/shared/infra/CommandBusImpl.java),
[QueryBusImpl](../src/main/java/com/example/demo/shared/infra/QueryBusImpl.java)
) los cuales deben ser revisados para una posible mejora.
Si bien actualmente estan en el contexto shared, 
seria optimo que exista una implementacion por cada "bounded-context".

En cuanto a los events podemos encontrar las clases 
[EventBusImpl](../src/main/java/com/example/demo/shared/infra/EventBusImpl.java)
que se encarga de publicar los eventos dentro de la aplicacion
y
[IntegrationEventPublisher](../src/main/java/com/example/demo/shared/infra/IntegrationEventPublisher.java)
que publica los eventos de integracion en un broker ***RabbitMQ*** cuya configuracion podemos encontrar 
[aqui](../src/main/java/com/example/demo/shared/infra/config/amqp)
