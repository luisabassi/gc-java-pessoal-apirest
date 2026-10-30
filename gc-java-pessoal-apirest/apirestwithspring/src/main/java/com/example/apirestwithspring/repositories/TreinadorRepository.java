package com.example.apirestwithspring.repositories;

import com.example.apirestwithspring.entities.Treinador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinadorRepository extends JpaRepository<Treinador, Long> {
}