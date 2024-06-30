package org.gfg.UserServiceApplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.gfg.UserServiceApplication.dtos.UserRequestDTO;
import org.gfg.UserServiceApplication.model.Users;
import org.gfg.UserServiceApplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    @PostMapping("/addUpdate")
    private ResponseEntity<Users> addUpdate(@RequestBody @Valid UserRequestDTO dto) throws JsonProcessingException {
        Users user = userService.addUpdate(dto);
        if(user != null){
            ResponseEntity response = new ResponseEntity(user, HttpStatus.OK);
            return response;
        }
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/userDetails")
    public Users getUserDetails(@RequestParam("contact") String contact){
        Users u = userService.loadUserByUsername(contact);
        return u;
    }
}
