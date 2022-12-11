package com.example.demo.shared.infra;

import com.example.demo.shared.domain.DomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@RequiredArgsConstructor
@Component
public class IntegrationEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationEventPublisher.class);

    private final IntegrationEventRegistry registry;
    private final IntegrationEventRepositoyJPA integrationEventRepositoyJPA;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void domainEventListener(DomainEvent domainEvent) {
        LOGGER.info("Escuchando evento de dominio {}", domainEvent.getClass());
        registry.getEventFromDomain(domainEvent).ifPresent(event -> {
            try {
                verifyExchange(event.exchange());
                rabbitTemplate.convertAndSend(event.getExchangeName(), event.getEventName(), event);
                LOGGER.info("evento enviado a Rabbit {}", event);
            } catch (Exception e) {
                String payload = jsonEncode(event);
                integrationEventRepositoyJPA.save(new IntegrationEventEntity(event, payload));
                LOGGER.info("error enviando evento a rabbit: evento guardado {}", event);
            }
        });
    }

    @Scheduled(fixedDelay = 30 * 1000)
    public void publishPendingIntegrationEvents() {
        // se puede hacer "paginable" por las dudas (limit, offset, sort)
        List<IntegrationEventEntity> pendingEvents = integrationEventRepositoyJPA.findAll();
        pendingEvents.forEach(event -> {
            try {
                rabbitTemplate.convertAndSend(event.getExchange(), event.getRoutingKey(), event.getPayload());
                integrationEventRepositoyJPA.delete(event);
            } catch (Exception e) {
                LOGGER.error("no se pudo enviar el evento {}", event);
            }
        });
    }

    private void verifyExchange(String exchange) throws Exception {
        rabbitTemplate.execute((ChannelCallback<Object>) channel -> channel.exchangeDeclarePassive(exchange));
    }

    private String jsonEncode(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
