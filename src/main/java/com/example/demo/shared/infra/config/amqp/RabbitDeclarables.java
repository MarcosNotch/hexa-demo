package com.example.demo.shared.infra.config.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class RabbitDeclarables {

    private final RabbitConfigProperties properties;

    @Bean
    public Declarables declarables() {
        List<Declarable> declarables = new ArrayList<>();
        declarables.addAll(exchanges());
        declarables.addAll(queues());
        declarables.addAll(bindings());

        return new Declarables(declarables);
    }

    private List<Exchange> exchanges() {
        return properties.getExchanges().stream()
                .map(TopicExchange::new)
                .collect(Collectors.toList());
    }

    private List<Queue> queues() {
        return properties.getQueues().stream()
                .map(q -> {
                    Queue queue = new Queue(q.getName(), q.isDurable(), q.isExclusive(), q.isAutoDelete());
                    if(q.getDeadLetterExchange() != null) {
                        queue.addArgument("x-dead-letter-exchange", q.getDeadLetterExchange());
                    }
                    if(q.getDeadLetterRoutingKey() != null) {
                        queue.addArgument("x-dead-letter-routing-key", q.getDeadLetterRoutingKey());
                    }
                    return queue;
                })
                .collect(Collectors.toList());
    }

    private List<Binding> bindings() {
        return properties.getBindings().stream()
                .map(b -> new Binding(b.getQueue(), Binding.DestinationType.QUEUE, b.getExchange(), b.getRoutingKey(), null))
                .collect(Collectors.toList());
    }

}
