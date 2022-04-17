package com.assignment.restcontroller;

import com.assignment.domain.Color;
import com.assignment.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/color")
public class ColorRestController {
    @Autowired
    ColorService colorService;

    @GetMapping("")
    public ResponseEntity<List<Color>> getAll(){
        if(colorService.findAll().size() <= 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(colorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> findById(@PathVariable("id") Integer id){
        Optional<Color> color = colorService.findById(id);
        if(color.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(color.get());
    }

    @PostMapping()
    public ResponseEntity<Color> save(@RequestBody Color color){
        if(color.getColor_id() ==  null){
            colorService.save(color);
            return ResponseEntity.ok(color);
        }else{
            if(colorService.existsById(color.getColor_id())) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(color);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> update(@PathVariable("id") Integer id,
                                       @RequestBody Color color){

        if(!colorService.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        colorService.save(color);
        return ResponseEntity.ok(color);
    }
}
