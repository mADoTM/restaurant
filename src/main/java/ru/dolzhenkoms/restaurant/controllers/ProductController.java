package ru.dolzhenkoms.restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.dolzhenkoms.restaurant.entities.Product;
import ru.dolzhenkoms.restaurant.repositories.ProductRepository;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/product")
    public String getAllProducts(ModelMap model) {
        model.addAttribute("products", productRepository.findAll());
        return "product_list";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") long productId) {
        productRepository.deleteById(productId);
        return "redirect:/product";
    }

    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute("product") Product note) {
        productRepository.save(note);
        return "redirect:/product";
    }

    @GetMapping("/product/update/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        Product product = productRepository.findById(id).orElse(new Product());
        model.addAttribute("product", product);
        return "update_product";
    }

    @PostMapping("/product")
    public ModelAndView addNewProduct(@RequestParam String name, @RequestParam String units) {
        Product product = new Product();
        product.setName(name);
        product.setUnits(units);

        if(productRepository.findByName(name) == null)
            productRepository.save(product);

        return new ModelAndView("redirect:/product");
    }
}
