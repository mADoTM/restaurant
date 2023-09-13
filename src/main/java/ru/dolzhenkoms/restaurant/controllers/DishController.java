package ru.dolzhenkoms.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.dolzhenkoms.restaurant.dtos.DishIngredientDto;
import ru.dolzhenkoms.restaurant.entities.Dish;
import ru.dolzhenkoms.restaurant.repositories.DishRepository;
import ru.dolzhenkoms.restaurant.repositories.ProductRepository;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DishController {

    private final DishRepository dishRepository;
    private final ProductRepository productRepository;

    @GetMapping("/dish")
    public String getAllSupplies(ModelMap model) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("dishes", dishRepository.findAll());
        return "dish_list";
    }

    @GetMapping("/dish/delete/{id}")
    public String deleteDish(@PathVariable("id") long dishId) {
        dishRepository.deleteById(dishId);
        return "redirect:/dish";
    }

    @PostMapping("/dish/save")
    public String saveSupply(@ModelAttribute("supply") Dish dish) {
        dishRepository.save(dish);
        return "redirect:/dish";
    }

    @GetMapping("/dish/update/{dishId}/delete/{productId}")
    public String deleteProductFromDish(@PathVariable(value = "dishId") long dishId, @PathVariable(value = "productId") long productId) {
        var dish = dishRepository.findById(dishId).get();
        var product = productRepository.findById(productId).get();
        dish.getIngredients().remove(product);
        dishRepository.save(dish);
        return "redirect:/dish/update/" + dishId;
    }

    @GetMapping("/dish/update/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        Dish dish = dishRepository.findById(id).orElse(new Dish());
        System.out.println(dish.getId());
        model.addAttribute("dish", dish);
        model.addAttribute("new_ingredient", new DishIngredientDto());
        model.addAttribute("products", productRepository.findAll().stream().filter(p -> !p.getDishes().contains(dish)).collect(Collectors.toList()));
        return "update_dish";
    }

    @PostMapping("/dish/update/{id}")
    public String addIngredientToDish(@PathVariable(value = "id") long id, @ModelAttribute("new_ingredient") DishIngredientDto dto, Model model) {
        Dish dish = dishRepository.findById(id).orElse(new Dish());
        dish.getIngredients().add(dto.getProduct());
        dishRepository.save(dish);
        model.addAttribute("dish", dish);
        model.addAttribute("new_ingredient", new DishIngredientDto());
        model.addAttribute("products", productRepository.findAll().stream().filter(p -> !p.getDishes().contains(dish)).collect(Collectors.toList()));

        return "update_dish";
    }

    @PostMapping("/dish")
    public ModelAndView addNewDish(@ModelAttribute("dish") Dish dish) {

        dishRepository.save(dish);

        return new ModelAndView("redirect:/dish");
    }
}
