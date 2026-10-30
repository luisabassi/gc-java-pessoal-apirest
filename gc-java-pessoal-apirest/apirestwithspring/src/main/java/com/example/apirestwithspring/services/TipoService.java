package com.example.apirestwithspring.services;

import com.example.apirestwithspring.dto.TipoRequest;
import com.example.apirestwithspring.dto.TipoResponse;
import com.example.apirestwithspring.entities.Tipo;
import com.example.apirestwithspring.repositories.TipoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TipoService {

    private final TipoRepository tipoRepository;

    public TipoService(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    @Transactional(readOnly = true)
    public List<TipoResponse> listAll() {
        return tipoRepository.findAll().stream().map(TipoResponse::from).toList();
    }

    @Transactional
    public TipoResponse create(TipoRequest req) {
        if (req.nome() == null || req.nome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'nome' é obrigatório");
        }
        if (tipoRepository.existsByNomeIgnoreCase(req.nome())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe o tipo '" + req.nome() + "'");
        }

        Tipo tipo = new Tipo();
        tipo.setNome(req.nome().trim().toLowerCase());
        Tipo savedTipo = tipoRepository.save(tipo);
        
        return TipoResponse.from(savedTipo);
    }

    @Transactional
    public void delete(Long id) {
        Tipo tipo = tipoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Tipo com id " + id + " não encontrado"));
        if (!tipo.getPokemons().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tipo em uso por pokémons; não pode ser removido");
        }
        tipoRepository.delete(tipo);
    }
}