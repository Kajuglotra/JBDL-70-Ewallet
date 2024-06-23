package org.gfg.UserServiceApplication.controller;

import jakarta.validation.Valid;
import org.gfg.UserServiceApplication.dtos.UserRequestDTO;
import org.gfg.UserServiceApplication.model.Users;
import org.gfg.UserServiceApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUpdate")
    private ResponseEntity<Users> addUpdate(@RequestBody @Valid UserRequestDTO dto){
        Users user = userService.addUpdate(dto);
        if(user != null){
            ResponseEntity response = new ResponseEntity(user, HttpStatus.OK);
            return response;
        }
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }
}
