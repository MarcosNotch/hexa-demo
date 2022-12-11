package com.example.demo.shared.infra.config.amqp;

import lombok.Data;

@Data
public class RabbitBinding {

    private String exchange;
    private String queue;
    private String routingKey;

}
