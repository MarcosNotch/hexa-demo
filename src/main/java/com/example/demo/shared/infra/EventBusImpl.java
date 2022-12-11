package com.example.demo.shared.infra;

import com.example.demo.shared.domain.DomainEvent;
import com.example.demo.shared.domain.EventBus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EventBusImpl implements EventBus {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(List<DomainEvent> domainEvents) {
        domainEvents.forEach(publisher::publishEvent);
    }

}
