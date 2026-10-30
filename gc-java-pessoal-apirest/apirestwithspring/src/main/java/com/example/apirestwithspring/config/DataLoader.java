package com.example.apirestwithspring.config;

import com.example.apirestwithspring.dto.PokeApiPokemon;
import com.example.apirestwithspring.entities.Pokemon;
import com.example.apirestwithspring.entities.Tipo;
import com.example.apirestwithspring.entities.Treinador;
import com.example.apirestwithspring.repositories.PokemonRepository;
import com.example.apirestwithspring.repositories.TipoRepository;
import com.example.apirestwithspring.repositories.TreinadorRepository;
import com.example.apirestwithspring.services.PokeApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

// Popula o banco na subida: importa os primeiros pokémons da PokeAPI (com seus tipos)
// Oi Dori, Oi Jean =)
@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private static final int TOTAL_INICIAL = 50;

    private final PokemonRepository pokemonRepository;
    private final TreinadorRepository treinadorRepository;
    private final TipoRepository tipoRepository;
    private final PokeApiClient pokeApi;

    public DataLoader(PokemonRepository pokemonRepository,
                      TreinadorRepository treinadorRepository,
                      TipoRepository tipoRepository,
                      PokeApiClient pokeApi) {
        this.pokemonRepository = pokemonRepository;
        this.treinadorRepository = treinadorRepository;
        this.tipoRepository = tipoRepository;
        this.pokeApi = pokeApi;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (pokemonRepository.count() == 0) {
            importarDaPokeApi();
        }
        if (treinadorRepository.count() == 0) {
            criarTreinadores();
        }
    }

    private void importarDaPokeApi() {
        Map<String, Tipo> tipos = new HashMap<>();
        try {
            for (int id = 1; id <= TOTAL_INICIAL; id++) {
                PokeApiPokemon dto = pokeApi.buscar(String.valueOf(id));
                if (dto == null) continue;

                Pokemon pokemon = new Pokemon();
                pokemon.setName(dto.name());
                pokemon.setHeight(dto.height());
                if (dto.types() != null) {
                    for (PokeApiPokemon.TypeSlot slot : dto.types()) {
                        String nomeTipo = slot.type().name();
                        Tipo tipo = tipos.computeIfAbsent(nomeTipo, n -> {
                            Tipo novoTipo = new Tipo();
                            novoTipo.setNome(n);
                            return tipoRepository.save(novoTipo);
                        });
                        pokemon.getTipos().add(tipo);
                    }
                }
                pokemonRepository.save(pokemon);
            }
        } catch (Exception e) {
            log.warn("Não foi possível importar tudo da PokeAPI: {}", e.getMessage());
        }
    }

    private void criarTreinadores() {
        Treinador jean = criarTreinador("Jean Divo", "Porto Alegre");
        Treinador dori = criarTreinador("Dori Amado", "Porto Alegre");
        Treinador luisa = criarTreinador("Luísa Linda", "Esteio");

        adotar(jean, "pikachu", "bulbasaur", "charmander");
        adotar(dori, "squirtle");
        adotar(luisa, "diglett");
    }

    private Treinador criarTreinador(String nome, String cidade) {
        Treinador treinador = new Treinador();
        treinador.setNome(nome);
        treinador.setCidade(cidade);
        return treinadorRepository.save(treinador);
    }

    private void adotar(Treinador treinador, String... nomes) {
        for (String nome : nomes) {
            pokemonRepository.findByNameIgnoreCase(nome).ifPresent(p -> {
                p.setTreinador(treinador);
                pokemonRepository.save(p);
            });
        }
    }
}