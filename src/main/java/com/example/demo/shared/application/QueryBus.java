package com.example.demo.shared.application;

import com.example.demo.shared.domain.Query;

public interface QueryBus {

    <R> R handle(Query<R> query);
}
