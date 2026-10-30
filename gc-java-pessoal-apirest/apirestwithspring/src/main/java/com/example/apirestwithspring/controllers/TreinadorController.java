package com.example.apirestwithspring.controllers;

import com.example.apirestwithspring.dto.PokemonResponse;
import com.example.apirestwithspring.dto.TreinadorRequest;
import com.example.apirestwithspring.dto.TreinadorResponse;
import com.example.apirestwithspring.services.TreinadorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokeApi/treinadores")
public class TreinadorController {

    private final TreinadorService service;

    public TreinadorController(TreinadorService service) {
        this.service = service;
    }

    @GetMapping
    public List<TreinadorResponse> listAll() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public TreinadorResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/pokemons")
    public List<PokemonResponse> listPokemons(@PathVariable Long id) {
        return service.listPokemons(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TreinadorResponse create(@RequestBody TreinadorRequest treinador) {
        return service.create(treinador);
    }

    @PutMapping("/{id}")
    public TreinadorResponse update(@PathVariable Long id, @RequestBody TreinadorRequest treinador) {
        return service.update(id, treinador);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}