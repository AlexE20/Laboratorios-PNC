package com.example.laboratorio1.Repository;

import com.example.laboratorio1.common.PokemonData;

public class PokemonRepository {
    private final PokemonData pokemonData;
    public List<Pokemon> getListaPokemon() {
        return pokemonData.getLista();
    }
}
