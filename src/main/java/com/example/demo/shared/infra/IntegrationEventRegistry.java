package com.example.demo.shared.infra;

import com.example.demo.shared.domain.DomainEvent;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class IntegrationEventRegistry {

    private final Map<Class, Class<? extends IntegrationEvent>> registry;

    public IntegrationEventRegistry() {
        this.registry = new HashMap<>();
        Reflections reflections = new Reflections();
        Set<Class<?>> integrationEvents = reflections.getTypesAnnotatedWith(RegisteredEvent.class);
        for (Class<?> integrationEvent : integrationEvents) {
            Class<?> domainEvent = integrationEvent.getAnnotation(RegisteredEvent.class).domainEvent();
            registry.put(domainEvent, (Class<? extends IntegrationEvent>) integrationEvent);
        }
    }

    public Optional<IntegrationEvent<?>> getEventFromDomain(DomainEvent domainEvent) {
        return Optional.ofNullable(registry.get(domainEvent.getClass())).map(event -> {
            try {
                return event.getConstructor(domainEvent.getClass()).newInstance(domainEvent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }



}
