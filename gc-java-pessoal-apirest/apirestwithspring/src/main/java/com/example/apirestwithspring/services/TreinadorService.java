package com.example.apirestwithspring.services;

import com.example.apirestwithspring.dto.PokemonResponse;
import com.example.apirestwithspring.dto.TreinadorRequest;
import com.example.apirestwithspring.dto.TreinadorResponse;
import com.example.apirestwithspring.entities.Pokemon;
import com.example.apirestwithspring.entities.Treinador;
import com.example.apirestwithspring.repositories.PokemonRepository;
import com.example.apirestwithspring.repositories.TreinadorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TreinadorService {

    private final TreinadorRepository treinadorRepository;
    private final PokemonRepository pokemonRepository;

    public TreinadorService(TreinadorRepository treinadorRepository, PokemonRepository pokemonRepository) {
        this.treinadorRepository = treinadorRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Transactional(readOnly = true)
    public List<TreinadorResponse> listAll() {
        return treinadorRepository.findAll().stream().map(TreinadorResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TreinadorResponse findById(Long id) {
        return TreinadorResponse.from(buscar(id));
    }

    @Transactional(readOnly = true)
    public List<PokemonResponse> listPokemons(Long treinadorId) {
        buscar(treinadorId); // garante o 404 se o treinador não existir
        return pokemonRepository.findByTreinadorId(treinadorId).stream()
                .map(PokemonResponse::from)
                .toList();
    }

    @Transactional
    public TreinadorResponse create(TreinadorRequest req) {
        validar(req);
        Treinador treinador = new Treinador();
        treinador.setNome(req.nome());
        treinador.setCidade(req.cidade());
        return TreinadorResponse.from(treinadorRepository.save(treinador));
    }

    @Transactional
    public TreinadorResponse update(Long id, TreinadorRequest req) {
        validar(req);
        Treinador treinador = buscar(id);
        treinador.setNome(req.nome());
        treinador.setCidade(req.cidade());
        return TreinadorResponse.from(treinadorRepository.save(treinador));
    }

    @Transactional
    public void delete(Long id) {
        Treinador treinador = buscar(id);
        for (Pokemon p : treinador.getPokemons()) {
            p.setTreinador(null);
        }
        treinadorRepository.delete(treinador);
    }

    private Treinador buscar(Long id) {
        return treinadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Treinador com id " + id + " não encontrado"));
    }

    private void validar(TreinadorRequest req) {
        if (req.nome() == null || req.nome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'nome' é obrigatório");
        }
    }
}
