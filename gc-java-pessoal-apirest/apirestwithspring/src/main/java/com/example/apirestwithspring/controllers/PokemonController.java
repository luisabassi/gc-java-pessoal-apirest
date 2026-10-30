package com.example.apirestwithspring.controllers;

import com.example.apirestwithspring.dto.PokeApiPokemon;
import com.example.apirestwithspring.dto.PokemonRequest;
import com.example.apirestwithspring.dto.PokemonResponse;
import com.example.apirestwithspring.services.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokeApi")
public class PokemonController {

    private final PokemonService service;

    public PokemonController(PokemonService service) {
        this.service = service;
    }

    // Consulta direta na PokeAPI externa. 
    @GetMapping("/pokemon/{pokemon_name}")
    public PokeApiPokemon whoIsThatPokemon(@PathVariable("pokemon_name") String pokemonName) {
        return service.getPokemonByName(pokemonName);
    }

    // Lista os pokémons do banco. =)
    @GetMapping("/pokemons")
    public List<PokemonResponse> listAll(@RequestParam(required = false) String tipo) {
        return service.listAll(tipo);
    }

    @GetMapping("/pokemons/{id}")
    public PokemonResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/pokemons")
    @ResponseStatus(HttpStatus.CREATED)
    public PokemonResponse create(@RequestBody PokemonRequest pokemon) {
        return service.create(pokemon);
    }

    @PutMapping("/pokemons/{id}")
    public PokemonResponse update(@PathVariable Long id, @RequestBody PokemonRequest pokemon) {
        return service.update(id, pokemon);
    }

    @DeleteMapping("/pokemons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

// me arrependo de não ter feito algo relacionado ao letterboxd =/