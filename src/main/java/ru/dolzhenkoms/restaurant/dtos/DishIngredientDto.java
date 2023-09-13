package ru.dolzhenkoms.restaurant.dtos;

import lombok.Data;
import ru.dolzhenkoms.restaurant.entities.Product;

@Data
public class DishIngredientDto {
    private Product product;
}
