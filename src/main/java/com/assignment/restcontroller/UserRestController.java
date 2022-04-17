package com.assignment.restcontroller;


import com.assignment.domain.User;
import com.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getAll(){
        if(userService.findAll().size() <= 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String username){
        Optional<User> user = userService.findById(username);
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping()
    public ResponseEntity<User> save(@RequestBody User user){

//        if(userService.existsById(user.getUsername())){
//            return ResponseEntity.badRequest().build();
//        }
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String username,
                                       @RequestBody User user){
        if(!userService.existsById(username)){
            return ResponseEntity.notFound().build();
        }
        userService.save(user);
        return ResponseEntity.ok(user);
    }
}
