package ru.dolzhenkoms.restaurant.dtos;

import lombok.Data;
import ru.dolzhenkoms.restaurant.entities.Dish;

@Data
public class MenuDishDto {
    private Dish dish;
}