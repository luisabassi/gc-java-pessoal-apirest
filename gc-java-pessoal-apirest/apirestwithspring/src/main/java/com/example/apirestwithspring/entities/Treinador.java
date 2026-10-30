package com.example.apirestwithspring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "treinador")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Treinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String cidade;

    @OneToMany(mappedBy = "treinador")
    private List<Pokemon> pokemons = new ArrayList<>();
}