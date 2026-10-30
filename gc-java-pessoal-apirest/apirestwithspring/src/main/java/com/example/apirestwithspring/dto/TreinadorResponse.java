package com.example.apirestwithspring.dto;

import com.example.apirestwithspring.entities.Treinador;

import java.util.List;

public record TreinadorResponse(Long id, String nome, String cidade, List<PokemonResumo> pokemons) {

    public record PokemonResumo(Long id, String name) {}

    public static TreinadorResponse from(Treinador t) {
        List<PokemonResumo> pokemons = t.getPokemons().stream()
                .map(p -> new PokemonResumo(p.getId(), p.getName()))
                .toList();
        return new TreinadorResponse(t.getId(), t.getNome(), t.getCidade(), pokemons);
    }
}