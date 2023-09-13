package ru.dolzhenkoms.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dolzhenkoms.restaurant.entities.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
