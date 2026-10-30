package com.example.apirestwithspring.repositories;

import com.example.apirestwithspring.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    Optional<Pokemon> findByNameIgnoreCase(String name);

    List<Pokemon> findByTreinadorId(Long treinadorId);

    @Query("select distinct p from Pokemon p join p.tipos t where lower(t.nome) = lower(:nome)")
    List<Pokemon> buscarPorTipo(@Param("nome") String nome);
}