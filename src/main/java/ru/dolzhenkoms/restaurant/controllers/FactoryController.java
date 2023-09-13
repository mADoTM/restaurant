package ru.dolzhenkoms.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.dolzhenkoms.restaurant.entities.Factory;
import ru.dolzhenkoms.restaurant.entities.Product;
import ru.dolzhenkoms.restaurant.repositories.FactoryRepository;

@Controller
@RequiredArgsConstructor
public class FactoryController {

    private final FactoryRepository factoryRepository;

    @GetMapping("/factory")
    public String getAllFactories(ModelMap model) {
        model.addAttribute("factories", factoryRepository.findAll());
        return "factory_list";
    }

    @GetMapping("/factory/delete/{id}")
    public String deleteFactory(@PathVariable("id") long factoryId) {
        factoryRepository.deleteById(factoryId);
        return "redirect:/factory";
    }

    @PostMapping("/factory/save")
    public String saveFactory(@ModelAttribute("factory") Factory factory) {
        factoryRepository.save(factory);
        return "redirect:/factory";
    }

    @GetMapping("/factory/update/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        Factory factory = factoryRepository.findById(id).orElse(new Factory());
        model.addAttribute("factory", factory);
        return "update_factory";
    }

    @PostMapping("/factory")
    public ModelAndView addNewFactory(@RequestParam String name) {
        Factory factory = new Factory();
        factory.setName(name);

        if(factoryRepository.findByName(name) == null)
            factoryRepository.save(factory);

        return new ModelAndView("redirect:/factory");
    }
}
