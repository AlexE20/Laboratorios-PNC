package com.example.laboratorio1.Repository;

import com.example.laboratorio1.common.PokemonData;
import com.example.laboratorio1.domain.entities.Pokemon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PokemonRepository {
    private final PokemonData pokemonData;
    public List<Pokemon> getListaPokemon() {
        return pokemonData.getLista();
    }
}
