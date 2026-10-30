package com.example.apirestwithspring.dto;

import com.example.apirestwithspring.entities.Tipo;

public record TipoResponse(Long id, String nome) {

    public static TipoResponse from(Tipo t) {
        return new TipoResponse(t.getId(), t.getNome());
    }
}