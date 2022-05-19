package com.bakerybyhermann.Controller;

import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Model.Product;
import com.bakerybyhermann.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//@MOHAMMAD
@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/show-product")
    public String getView(Model model){
        model.addAttribute("productsList", productService.fetchAll());
        return "product/show-products";
    }

    @GetMapping("/new-product")
    public String newProduct(){
        return "product/new-product";
    }

    @PostMapping("/new-product")
    public String createCustomer(@ModelAttribute Product product){
        productService.addNew(product);
        return "redirect:/show-product";
    }

    @GetMapping("/update-product/{id}")
    public String updateCustomer(@PathVariable("id") int id, Model model){
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product/update-product";
    }

    @PostMapping("/update-product")
    public String updateCustomer(@ModelAttribute Product product){
        productService.updateById(product.getProductId(), product);
        return "redirect:/show-product";
    }

    @GetMapping("/product/{Id}")
    public String deleteCustomer(@PathVariable("Id") int id){
        productService.delteById(id);
        return "redirect:/show-product";
    }
}
