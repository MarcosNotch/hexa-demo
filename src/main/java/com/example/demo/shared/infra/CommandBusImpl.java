package com.example.demo.shared.infra;

import com.example.demo.shared.application.CommandBus;
import com.example.demo.shared.application.CommandHandler;
import com.example.demo.shared.domain.Command;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class CommandBusImpl implements CommandBus {

    private final Map<Class, CommandHandler> handlers;

    public CommandBusImpl(List<CommandHandler> commandHandlers) {
        this.handlers = new HashMap<>();
        commandHandlers.forEach(handler -> {
            Class<?> commandClass = getCommandClass(handler);
            handlers.put(commandClass, handler);
        });
    }

    @Override
    public void handle(Command command) {
        if(!handlers.containsKey(command.getClass())) {
            throw new RuntimeException(String.format("No handler found for %s", command.getClass().getName()));
        }
        handlers.get(command.getClass()).handle(command);
    }

    private Class<?> getCommandClass(CommandHandler handler) {
        try {
            String className = handler.getClass().getName().substring(0, handler.getClass().getName().indexOf("$"));
            return (Class<?>) ((ParameterizedType) Class.forName(className).getGenericInterfaces()[0]).getActualTypeArguments()[0];
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
