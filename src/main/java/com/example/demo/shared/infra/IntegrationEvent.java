package com.example.demo.shared.infra;

import com.example.demo.shared.domain.DomainEvent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class IntegrationEvent<T extends DomainEvent> {

    private final UUID eventId;
    private final LocalDateTime timestamp;
    private final String eventName;
    private final String exchangeName;

    public IntegrationEvent(T domainEvent) {
        this.eventId = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
        this.eventName = eventName();
        this.exchangeName = exchange();
    }

    public abstract String eventName();

    public abstract String exchange();

}
