package com.example.apirestwithspring.controllers;

import com.example.apirestwithspring.entities.Pokemon;
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

    @GetMapping("/pokemon/{pokemon_name}")
    public String whoIsThatPokemon (@PathVariable String pokemon_name){
        return service.getPokemonByName(pokemon_name);
    }

    @GetMapping("/pokemons")
    public List<Pokemon> listAll() {
        return service.listAll();
    }

    @GetMapping("/pokemons/{id}")
    public Pokemon findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping("/pokemons")
    @ResponseStatus(HttpStatus.CREATED)
    public Pokemon create(@RequestBody Pokemon pokemon) {
        return service.create(pokemon);
    }

    @PutMapping("/pokemons/{id}")
    public Pokemon update(@PathVariable Integer id, @RequestBody Pokemon pokemon) {
        return service.update(id, pokemon);
    }

    @DeleteMapping("/pokemons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
