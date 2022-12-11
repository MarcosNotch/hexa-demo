package com.example.demo.shared.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Aggregate {

    private transient final List<DomainEvent> domainEvents = new ArrayList<>();

    public final List<DomainEvent> pullEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }

    protected final void push(DomainEvent event) {
        domainEvents.add(event);
    }

}
