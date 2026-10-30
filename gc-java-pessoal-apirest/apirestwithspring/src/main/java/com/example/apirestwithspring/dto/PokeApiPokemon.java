package com.example.apirestwithspring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokeApiPokemon(Integer id, String name, double height, List<TypeSlot> types) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TypeSlot(Integer slot, TypeInfo type) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TypeInfo(String name) {}
}