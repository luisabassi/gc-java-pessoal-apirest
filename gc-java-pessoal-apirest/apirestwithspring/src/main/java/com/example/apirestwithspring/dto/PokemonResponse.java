package com.example.apirestwithspring.dto;

import com.example.apirestwithspring.entities.Pokemon;
import com.example.apirestwithspring.entities.Tipo;

import java.util.Comparator;
import java.util.List;

public record PokemonResponse(Long id, String name, double height,
                              TreinadorResumo treinador, List<String> tipos) {

    public record TreinadorResumo(Long id, String nome) {}

    public static PokemonResponse from(Pokemon p) {
        TreinadorResumo t = p.getTreinador() == null ? null
                : new TreinadorResumo(p.getTreinador().getId(), p.getTreinador().getNome());
        List<String> tipos = p.getTipos().stream()
                .map(Tipo::getNome)
                .sorted(Comparator.naturalOrder())
                .toList();
        return new PokemonResponse(p.getId(), p.getName(), p.getHeight(), t, tipos);
    }
}