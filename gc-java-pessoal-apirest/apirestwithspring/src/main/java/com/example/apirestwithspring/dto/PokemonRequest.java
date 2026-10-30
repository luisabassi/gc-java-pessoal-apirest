package com.example.apirestwithspring.dto;

import java.util.Set;

public record PokemonRequest(String name, double height, Long treinadorId, Set<Long> tipoIds) {
}