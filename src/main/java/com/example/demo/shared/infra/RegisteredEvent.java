package com.example.demo.shared.infra;

import com.example.demo.shared.domain.DomainEvent;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RegisteredEvent {

    Class<? extends DomainEvent> domainEvent();
}
