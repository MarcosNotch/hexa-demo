package com.example.demo.shared.infra;

import com.example.demo.shared.application.QueryBus;
import com.example.demo.shared.application.QueryHandler;
import com.example.demo.shared.domain.Query;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class QueryBusImpl implements QueryBus {

    private final Map<Class, QueryHandler> handlers;

    public QueryBusImpl(List<QueryHandler> queryHandlers) {
        this.handlers = new HashMap<>();
        queryHandlers.forEach(handler -> {
            Class<?> queryClass = getQueryClass(handler);
            handlers.put(queryClass, handler);
        });
    }

    @Override
    public <R> R handle(Query<R> query) {
        if(!handlers.containsKey(query.getClass())) {
            throw new RuntimeException(String.format("No handler found for %s", query.getClass().getName()));
        }
        return (R) handlers.get(query.getClass()).handle(query);
    }

    private Class<?> getQueryClass(QueryHandler handler) {
        try {
            return Class.forName(handler.getClass().getName())
                    .getDeclaredMethods()[0]
                    .getParameterTypes()[0];
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
