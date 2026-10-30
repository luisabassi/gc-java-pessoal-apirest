package com.example.apirestwithspring.services;

import com.example.apirestwithspring.dto.PokeApiPokemon;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PokeApiClient {

    private final RestClient client = RestClient.builder()
            .baseUrl("https://pokeapi.co/api/v2")
            .build();

    public PokeApiPokemon buscar(String nomeOuId) {
        return client.get()
                .uri("/pokemon/{nomeOuId}", nomeOuId)
                .retrieve()
                .body(PokeApiPokemon.class);
    }
}