package ru.dolzhenkoms.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dolzhenkoms.restaurant.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
