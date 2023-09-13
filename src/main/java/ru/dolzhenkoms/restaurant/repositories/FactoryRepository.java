package ru.dolzhenkoms.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dolzhenkoms.restaurant.entities.Factory;

public interface FactoryRepository extends JpaRepository<Factory, Long> {
}
