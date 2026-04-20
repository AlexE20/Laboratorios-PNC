package com.example.laboratorio1;

import com.example.laboratorio1.Services.PokemonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Laboratorio1Application {

    public static void main(String[] args) {
        SpringApplication.run(Laboratorio1Application.class, args);
    }
    @Bean
    public CommandLineRunner run(PokemonService pokemonService) {
        return args -> {
            System.out.println("\n=== [INICIANDO POKEDEX DIGITAL] ===");

            System.out.println("\n--- Pokémones de  tipo Fuego ---");
            pokemonService.filtrarPorTipo("Fuego").forEach(p ->
                    System.out.println("[PKMN] Nombre: " + p.getNombre() + " | Tipo: " + p.getTipo() + " | Debilidades: " + String.join(", ", p.getDebilidades()))
            );

            System.out.println("\n--- Pokémones en Zona de bosque ---");
            pokemonService.filtrarPorZona("Bosque").forEach(p ->
                    System.out.println("[PKMN] Nombre: " + p.getNombre() + " | Tipo: " + p.getTipo() + " | Debilidades: " + String.join(", " ,p.getDebilidades()) +"| Zonas: " + String.join(", ", p.getZonas()))
            );

            System.out.println("\n--- Pokémon con debilidad: Tierra ---");
            pokemonService.filtrarPorDebilidad("Tierra").forEach(p ->
                    System.out.println("[PKMN] Nombre: " + p.getNombre() + " | Tipo: " + p.getTipo() + " | Debilidades: " + String.join(", ", p.getDebilidades())+ "| Zonas: " + String.join(", ", p.getZonas()))
            );

            System.out.println("\n=== [SISTEMA TERMINADO CORRECTAMENTE] ===");
        };
    }
}

