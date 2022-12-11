package com.example.demo.shared.infra;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "integration_event", schema = "producto")
public class IntegrationEventEntity {

    @Id
    @Column(updatable = false)
    @Type(type = "uuid-char")
    protected UUID uuid;

    private String exchange;

    @Column(name = "routing_key")
    private String routingKey;

    private String payload;

    public IntegrationEventEntity(IntegrationEvent<?> event, String payload) {
        this.uuid = UUID.randomUUID();
        this.exchange = event.exchange();
        this.routingKey = event.eventName();
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IntegrationEventEntity that = (IntegrationEventEntity) o;
        return uuid != null && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
