[< Inicio](../README.md)
# Eventos de integración

Los eventos de integración se encuentran implementados en la capa infra
y se decidio separar de los eventos de dominio (aunque implique duplicidad de código y "sobreingenieria") 
ya que estos últimos deberian
ser agnósticos a detalles tecnicos como por ejemplo el nombre de los exchanges, routing keys, etc.

Estos eventos tienen que ser englobados por su bounded context.
Para que funcionen correctamente se tienen que anotar con la etiqueta
`@RegisteredEvent` y tienen que extender de la clase `IntegrationEvent`.
ej:
```java
import com.example.demo.producto.domain.events.ProductoCreadoEvent;
import com.example.demo.shared.infra.IntegrationEvent;
import com.example.demo.shared.infra.RegisteredEvent;
import lombok.Getter;

@Getter
@RegisteredEvent(domainEvent = EventoDeDominio.class)
public class EventoDeDominioIntegrationEvent extends IntegrationEvent<EventoDeDominio> {

    private final static String EVENT_NAME = "dominio.accion";
    private final static String EXCHANGE_NAME = "x-dominio";

    private final Long dominioId;

    public ProductoCreadoIntegrationEvent(EventoDeDominio domainEvent) {
        super(domainEvent);
        this.dominioId = domainEvent.getDominio().getId();
    }

    @Override
    public String eventName() {
        return EVENT_NAME;
    }

    @Override
    public String exchange() {
        return EXCHANGE_NAME;
    }
}
```

Estos eventos son injectados de forma automatica utilizando la libreria `Reflections` en la clase
[IntegrationEventRegistry](../src/main/java/com/example/demo/shared/infra/IntegrationEventRegistry.java)
y luego publicados por 
[IntegrationEventPublisher](../src/main/java/com/example/demo/shared/infra/IntegrationEventPublisher.java)
aplicando algo similar al ***outbox pattern***.