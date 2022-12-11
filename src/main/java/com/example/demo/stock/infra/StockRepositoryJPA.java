package com.example.demo.stock.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepositoryJPA extends JpaRepository<StockEntity, UUID> {

    Optional<StockEntity> findFirstByProductoId(Long producto);

}
