package com.example.apirestwithspring.services;

import com.example.apirestwithspring.dto.PokeApiPokemon;
import com.example.apirestwithspring.dto.PokemonRequest;
import com.example.apirestwithspring.dto.PokemonResponse;
import com.example.apirestwithspring.entities.Pokemon;
import com.example.apirestwithspring.entities.Tipo;
import com.example.apirestwithspring.entities.Treinador;
import com.example.apirestwithspring.repositories.PokemonRepository;
import com.example.apirestwithspring.repositories.TipoRepository;
import com.example.apirestwithspring.repositories.TreinadorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final TreinadorRepository treinadorRepository;
    private final TipoRepository tipoRepository;
    private final PokeApiClient pokeApi;

    public PokemonService(PokemonRepository pokemonRepository,
                          TreinadorRepository treinadorRepository,
                          TipoRepository tipoRepository,
                          PokeApiClient pokeApi) {
        this.pokemonRepository = pokemonRepository;
        this.treinadorRepository = treinadorRepository;
        this.tipoRepository = tipoRepository;
        this.pokeApi = pokeApi;
    }

    // Consulta direta na PokeAPI 
    public PokeApiPokemon getPokemonByName(String pokemonName) {
        return pokeApi.buscar(pokemonName);
    }

    @Transactional(readOnly = true)
    public List<PokemonResponse> listAll(String tipo) {
        List<Pokemon> lista = (tipo == null || tipo.isBlank())
                ? pokemonRepository.findAll()
                : pokemonRepository.buscarPorTipo(tipo.trim());
        return lista.stream().map(PokemonResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public PokemonResponse findById(Long id) {
        return PokemonResponse.from(buscar(id));
    }

    @Transactional
    public PokemonResponse create(PokemonRequest req) {
        validar(req);
        Pokemon pokemon = new Pokemon();
        aplicar(pokemon, req);
        return PokemonResponse.from(pokemonRepository.save(pokemon));
    }

    @Transactional
    public PokemonResponse update(Long id, PokemonRequest req) {
        validar(req);
        Pokemon pokemon = buscar(id);
        aplicar(pokemon, req);
        return PokemonResponse.from(pokemonRepository.save(pokemon));
    }

    @Transactional
    public void delete(Long id) {
        pokemonRepository.delete(buscar(id));
    }

    // ---------- auxiliares ----------

    private Pokemon buscar(Long id) {
        return pokemonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pokémon com id " + id + " não encontrado"));
    }

    private void validar(PokemonRequest req) {
        if (req.name() == null || req.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'name' é obrigatório");
        }
    }

    private void aplicar(Pokemon pokemon, PokemonRequest req) {
        pokemon.setName(req.name());
        pokemon.setHeight(req.height());

        if (req.treinadorId() == null) {
            pokemon.setTreinador(null);
        } else {
            Treinador treinador = treinadorRepository.findById(req.treinadorId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Treinador " + req.treinadorId() + " não existe"));
            pokemon.setTreinador(treinador);
        }

        Set<Tipo> tipos = new HashSet<>();
        if (req.tipoIds() != null && !req.tipoIds().isEmpty()) {
            tipos.addAll(tipoRepository.findAllById(req.tipoIds()));
            if (tipos.size() != req.tipoIds().size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Algum tipo informado não existe");
            }
        }
        pokemon.setTipos(tipos);
    }
}