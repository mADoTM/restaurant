package ru.dolzhenkoms.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dolzhenkoms.restaurant.entities.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
