package com.example.demo.shared.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IntegrationEventRepositoyJPA extends JpaRepository<IntegrationEventEntity, UUID> {

}
