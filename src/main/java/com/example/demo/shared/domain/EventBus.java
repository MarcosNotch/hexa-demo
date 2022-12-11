package com.example.demo.shared.domain;

import java.util.List;

public interface EventBus {

    void publish(List<DomainEvent> domainEvents);

}
