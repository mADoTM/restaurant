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
import ru.dolzhenkoms.restaurant.dtos.MenuDishDto;
import ru.dolzhenkoms.restaurant.entities.Menu;
import ru.dolzhenkoms.restaurant.repositories.DishRepository;
import ru.dolzhenkoms.restaurant.repositories.MenuRepository;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuRepository menuRepository;
    private final DishRepository dishRepository;

    @GetMapping("/menu")
    public String getAllMenus(ModelMap model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("menus", menuRepository.findAll());
        return "menu_list";
    }

    @GetMapping("/menu/delete/{id}")
    public String deleteMenu(@PathVariable("id") long menuId) {
        menuRepository.deleteById(menuId);
        return "redirect:/menu";
    }

    @PostMapping("/menu/save")
    public String saveMenu(@ModelAttribute("menu") Menu menu) {
        System.out.println(menu.getId());
        menuRepository.save(menu);
        return "redirect:/menu";
    }

    @GetMapping("/menu/update/{menuId}/delete/{dishId}")
    public String deleteDishFromMenu(@PathVariable(value = "menuId") long menuId, @PathVariable(value = "dishId") long dishId) {
        var menu = menuRepository.findById(menuId).get();
        var dish = dishRepository.findById(dishId).get();
        menu.getDishes().remove(dish);
        menuRepository.save(menu);
        return "redirect:/menu/update/" + menuId;
    }

    @GetMapping("/menu/update/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        Menu menu = menuRepository.findById(id).orElse(new Menu());
        model.addAttribute("menu", menu);
        model.addAttribute("dishes", dishRepository.findAll().stream().filter(d -> !d.getMenuList().contains(menu)).collect(Collectors.toList()));
        model.addAttribute("new_dish", new MenuDishDto());
        return "update_menu";
    }

    @PostMapping("/menu/update/{id}")
    public String addDishToMenu(@PathVariable(value = "id") long id, @ModelAttribute("new_dish") MenuDishDto dto, Model model) {
        Menu menu = menuRepository.findById(id).orElse(new Menu());
        menu.getDishes().add(dto.getDish());
        menuRepository.save(menu);
        model.addAttribute("menu", menu);
        model.addAttribute("new_dish", new MenuDishDto());
        model.addAttribute("dishes", dishRepository.findAll().stream().filter(d -> !d.getMenuList().contains(menu)).collect(Collectors.toList()));

        return "update_menu";
    }

    @PostMapping("/menu")
    public ModelAndView addNewMenu(@ModelAttribute("menu") Menu menu) {
        if(menuRepository.findByDate(menu.getDate()) == null)
            menuRepository.save(menu);

        return new ModelAndView("redirect:/menu");
    }
}
