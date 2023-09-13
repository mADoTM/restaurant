package ru.dolzhenkoms.restaurant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dolzhenkoms.restaurant.entities.Menu;

import java.time.LocalDate;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByDate(LocalDate date);
}
