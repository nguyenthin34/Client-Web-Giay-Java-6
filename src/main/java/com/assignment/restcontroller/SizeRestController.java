package com.assignment.restcontroller;

import com.assignment.domain.Size;
import com.assignment.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/size")
public class SizeRestController {
    @Autowired
    SizeService sizeService;

    @GetMapping("")
    public ResponseEntity<List<Size>> getAll(){
        if(sizeService.findAll().size() <= 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sizeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Size> findById(@PathVariable("id") Integer id){
        Optional<Size> size = sizeService.findById(id);
        if(size.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(size.get());
    }

    @PostMapping()
    public ResponseEntity<Size> save(@RequestBody Size size){
        if(size.getSize_id() ==  null){
            sizeService.save(size);
            return ResponseEntity.ok(size);
        }else{
            if(sizeService.existsById(size.getSize_id())) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(size);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Size> update(@PathVariable("id") Integer id,
                                       @RequestBody Size size){

        if(!sizeService.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        sizeService.save(size);
        return ResponseEntity.ok(size);
    }
}
