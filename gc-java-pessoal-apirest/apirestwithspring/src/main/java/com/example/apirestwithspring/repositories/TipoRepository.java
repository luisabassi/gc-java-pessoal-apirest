package com.example.apirestwithspring.repositories;

import com.example.apirestwithspring.entities.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoRepository extends JpaRepository<Tipo, Long> {

    Optional<Tipo> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}