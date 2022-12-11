package com.example.demo.shared.infra.config.amqp;

import lombok.Data;

@Data
public class RabbitQueue {

    private String name;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;
    private String deadLetterExchange;
    private String deadLetterRoutingKey;

}
