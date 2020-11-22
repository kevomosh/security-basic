package com.kakuom.finalshop.controller;

import com.kakuom.finalshop.model.Role;
import com.kakuom.finalshop.model.User;
import com.kakuom.finalshop.repo.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public void create() {
        var x = new User("kevoo", "omoshkevin@gmail.com", "123456", Role.ADMIN);
        userRepository.save(x);

    }

    @GetMapping("/byId/{id}")
    public User getuserById(@PathVariable Long id) {
        var b =  userRepository.findById(id);
        return b.get();
    }
}
