package com.example.demo.producto.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductoRepositoryJPA extends JpaRepository<ProductoEntity, UUID> {

    @Query(value = "SELECT nextval('producto.producto_seq')", nativeQuery = true)
    Long generateId();

    Optional<ProductoEntity> findById(Long id);

    boolean existsByCodigo(String codigo);

}
