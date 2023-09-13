package ru.dolzhenkoms.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.dolzhenkoms.restaurant.entities.Factory;
import ru.dolzhenkoms.restaurant.entities.Product;
import ru.dolzhenkoms.restaurant.entities.Supply;
import ru.dolzhenkoms.restaurant.repositories.FactoryRepository;
import ru.dolzhenkoms.restaurant.repositories.ProductRepository;
import ru.dolzhenkoms.restaurant.repositories.SupplyRepository;

@Controller
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyRepository supplyRepository;
    private final ProductRepository productRepository;
    private final FactoryRepository factoryRepository;

    @GetMapping("/supply")
    public String getAllSupplies(ModelMap model) {
        model.addAttribute("supply", new Supply());
        model.addAttribute("supplies", supplyRepository.findAll());
        model.addAttribute("factories", factoryRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "supply_list";
    }

    @GetMapping("/supply/delete/{id}")
    public String deleteSupply(@PathVariable("id") long supplyId) {
        supplyRepository.deleteById(supplyId);
        return "redirect:/supply";
    }

    @PostMapping("/supply/save")
    public String saveSupply(@ModelAttribute("supply") Supply supply) {
        System.out.println(supply.getId());
        supplyRepository.save(supply);
        return "redirect:/supply";
    }

    @GetMapping("/supply/update/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        Supply supply = supplyRepository.findById(id).orElse(new Supply());
        System.out.println(supply.getId());
        model.addAttribute("supply", supply);
        model.addAttribute("factories", factoryRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "update_supply";
    }

    @PostMapping("/supply")
    public ModelAndView addNewSupply(@ModelAttribute("supply") Supply supply) {

        supplyRepository.save(supply);

        return new ModelAndView("redirect:/supply");
    }
}
