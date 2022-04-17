package com.assignment.restcontroller;

import com.assignment.domain.Product;
import com.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/product")
public class ProductRestController {

    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public Product getOne(@PathVariable("id") Integer id){
        Optional<Product> product = productService.findById(id);
//        if(product.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
        return product.get();
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAll(){
        List<Product> product = productService.findAll();
        if(product.size() <= 0){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(product);
    }
}
