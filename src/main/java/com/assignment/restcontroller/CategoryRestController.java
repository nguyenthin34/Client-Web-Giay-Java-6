package com.assignment.restcontroller;

import com.assignment.domain.Category;
import com.assignment.service.CategoryService;
import com.assignment.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/category")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAll(){
        if(categoryService.findAll().size() <= 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") Integer id){
        Optional<Category> category = categoryService.findById(id);
        if(category.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category.get());
    }

    @PostMapping()
    public ResponseEntity<Category> save(@RequestBody Category category){
        if(category.getCategory_id() ==  null){
            categoryService.save(category);
            return ResponseEntity.ok(category);
        }else {
            if (categoryService.existsById(category.getCategory_id())) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(category);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") Integer id,
                                        @RequestBody Category category){

        if(!categoryService.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        categoryService.save(category);
        return ResponseEntity.ok(category);
    }
}
