package com.assignment.restcontroller;

import com.assignment.domain.Role;
import com.assignment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/role")
public class RoleRestController {
    @Autowired
    RoleService roleService;

    @GetMapping("")
    public ResponseEntity<List<Role>> getAll(){
        if(roleService.findAll().size() <= 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable("id") Integer id){
        Optional<Role> Role = roleService.findById(id);
        if(Role.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Role.get());
    }

    @PostMapping()
    public ResponseEntity<Role> save(@RequestBody Role role){
        if(role.getRole_id() ==  null){
            roleService.save(role);
            return ResponseEntity.ok(role);
        }else{
            if(roleService.existsById(role.getRole_id())) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(role);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable("id") Integer id,
                                       @RequestBody Role Role){

        if(!roleService.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        roleService.save(Role);
        return ResponseEntity.ok(Role);
    }
}
