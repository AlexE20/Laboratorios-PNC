package com.example.laboratorio1.common;

import com.example.laboratorio1.domain.entities.Pokemon;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PokemonData {
    private final List<Pokemon> listaPokemon = new ArrayList<>();

    public PokemonData(){
        listaPokemon.add(Pokemon.builder()
                .nombre("Charmander")
                .tipo("Fuego")
                .debilidades(List.of("Agua", "Roca", "Tierra"))
                .zonas(List.of("Montaña", "Cueva"))
                .region("Kanto")
                .build());

        listaPokemon.add(Pokemon.builder()
                .nombre("Rayquasa")
                .tipo("Volador")
                .debilidades(List.of("Electrico", "Hielo", "Roca"))
                .zonas(List.of("Montaña", "Cueva"))
                .region("Kanto")
                .build());

        listaPokemon.add(Pokemon.builder()
                .nombre("Pikachu")
                .tipo("Eléctrico")
                .debilidades(List.of("Tierra"))
                .zonas(List.of("Bosque", "Ciudad"))
                .region("Kanto")
                .build());
    }

    public List<Pokemon> getLista() {
        return listaPokemon;
    }
}
