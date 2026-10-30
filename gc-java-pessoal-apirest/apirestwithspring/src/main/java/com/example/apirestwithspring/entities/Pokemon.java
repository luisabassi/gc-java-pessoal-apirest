package com.example.apirestwithspring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pokemon")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;

    @ManyToMany
    @JoinTable(
            name = "pokemon_tipo",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_id"))
    private Set<Tipo> tipos = new HashSet<>();
}