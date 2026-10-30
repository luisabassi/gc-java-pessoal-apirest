package com.example.apirestwithspring.controllers;

import com.example.apirestwithspring.dto.TipoRequest;
import com.example.apirestwithspring.dto.TipoResponse;
import com.example.apirestwithspring.services.TipoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokeApi/tipos")
public class TipoController {

    private final TipoService service;

    public TipoController(TipoService service) {
        this.service = service;
    }

    @GetMapping
    public List<TipoResponse> listAll() {
        return service.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoResponse create(@RequestBody TipoRequest tipo) {
        return service.create(tipo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}