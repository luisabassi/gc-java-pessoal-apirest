package com.example.apirestwithspring.services;

import com.example.apirestwithspring.entities.Pokemon;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PokemonService {

    private static final int TOTAL_INICIAL = 50;

    private final RestClient client;

    private final List<Pokemon> pokemons = new ArrayList<>();
    private boolean carregado = false;

    public PokemonService(RestClient.Builder builder) {
        this.client = RestClient.builder().baseUrl("https://pokeapi.co/api/v2").build();
    }


    public String getPokemonByName (String pokemon_name){
       return  Objects.requireNonNull(client.get()
                       .uri("/pokemon/{pokemon_name}", pokemon_name)
                       .retrieve()
                       .body(Pokemon.class))
                       .toString();
    }

    public synchronized List<Pokemon> listAll() {
        carregarSeNecessario();
        return new ArrayList<>(pokemons);
    }

    public synchronized Pokemon findById(Integer id) {
        carregarSeNecessario();
        return pokemons.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> naoEncontrado(id));
    }

    public synchronized Pokemon create(Pokemon novo) {
        carregarSeNecessario();
        if (novo.name() == null || novo.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'name' é obrigatório");
        }
        Integer id = novo.id();
        if (id == null) {
            id = pokemons.stream().mapToInt(Pokemon::id).max().orElse(0) + 1;
        } else if (pokemons.stream().anyMatch(p -> p.id().equals(novo.id()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um pokémon com id " + id);
        }
        Pokemon criado = new Pokemon(id, novo.name(), novo.height());
        pokemons.add(criado);
        return criado;
    }

    public synchronized Pokemon update(Integer id, Pokemon dados) {
        carregarSeNecessario();
        if (dados.name() == null || dados.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'name' é obrigatório");
        }
        for (int i = 0; i < pokemons.size(); i++) {
            if (pokemons.get(i).id().equals(id)) {
                // Pokemon é um record (imutável): substitui o item, mantendo o id da URL
                Pokemon atualizado = new Pokemon(id, dados.name(), dados.height());
                pokemons.set(i, atualizado);
                return atualizado;
            }
        }
        throw naoEncontrado(id);
    }


    public synchronized void delete(Integer id) {
        carregarSeNecessario();
        if (!pokemons.removeIf(p -> p.id().equals(id))) {
            throw naoEncontrado(id);
        }
    }

    private void carregarSeNecessario() {
        if (carregado) return;
        for (int id = 1; id <= TOTAL_INICIAL; id++) {
            Pokemon p = client.get()
                    .uri("/pokemon/{id}", id)
                    .retrieve()
                    .body(Pokemon.class);
            if (p != null) pokemons.add(p);
        }
        carregado = true;
    }

    private ResponseStatusException naoEncontrado(Integer id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokémon com id " + id + " não encontrado");
    }
}