package ru.dolzhenkoms.restaurant.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String units;

    @OneToMany(mappedBy = "product")
    private List<Supply> supplyList;

    @ManyToMany(mappedBy = "ingredients")
    private List<Dish> dishes;
}
