package com.assignment.restcontroller;

import com.assignment.domain.Commodity;
import com.assignment.service.CommodityService;
import com.assignment.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/commodity")
public class CommodityRestController {
    @Autowired
    CommodityService commodityService;

    @GetMapping("")
    public ResponseEntity<List<Commodity>> getAll(){
        if(commodityService.findAll().size() <= 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(commodityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commodity> findById(@PathVariable("id") Integer id){
        Optional<Commodity> commodity = commodityService.findById(id);
        if(commodity.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commodity.get());
    }

    @PostMapping()
    public ResponseEntity<Commodity> save(@RequestBody Commodity commodity){
        if(commodity.getCommodity_id() ==  null){
            commodityService.save(commodity);
            return ResponseEntity.ok(commodity);
        }else{
            if(commodityService.existsById(commodity.getCommodity_id())) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(commodity);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Commodity> update(@PathVariable("id") Integer id,
                                           @RequestBody Commodity commodity){

        if(!commodityService.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        commodityService.save(commodity);
        return ResponseEntity.ok(commodity);
    }
}
