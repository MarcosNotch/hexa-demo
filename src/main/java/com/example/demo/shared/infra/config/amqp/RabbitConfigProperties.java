package com.example.demo.shared.infra.config.amqp;

import com.example.demo.shared.infra.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;


@Configuration
@ConfigurationProperties("rabbitmq")
@PropertySource(value = "classpath:amqp/rabbit.yaml", factory = YamlPropertySourceFactory.class)
public class RabbitConfigProperties {

    private List<String> exchanges;
    private List<RabbitQueue> queues;
    private List<RabbitBinding> bindings;

    public List<String> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<String> exchanges) {
        this.exchanges = exchanges;
    }

    public List<RabbitQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<RabbitQueue> queues) {
        this.queues = queues;
    }

    public List<RabbitBinding> getBindings() {
        return bindings;
    }

    public void setBindings(List<RabbitBinding> bindings) {
        this.bindings = bindings;
    }
}
