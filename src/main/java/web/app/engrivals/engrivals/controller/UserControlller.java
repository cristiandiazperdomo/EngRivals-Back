package web.app.engrivals.engrivals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.engrivals.engrivals.Dtos.UserDTO;
import web.app.engrivals.engrivals.persistance.entities.UserEntity;
import web.app.engrivals.engrivals.service.UserService;

@RestController
@RequestMapping("/v1/api/users")
@CrossOrigin("*")
public class UserControlller {
    @Autowired
    UserService userService;
    
    @PostMapping
    public UserEntity create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }
    
//    @PutMapping
//    public UserEntity update(@RequestBody UserDTO user) {
//        return userService.update(user);
//    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userService.deleteById(id);
    }
    
    @GetMapping("/{id}")
    public UserEntity findById(@PathVariable String id) {
        return userService.findById(id);
    }
    
}
