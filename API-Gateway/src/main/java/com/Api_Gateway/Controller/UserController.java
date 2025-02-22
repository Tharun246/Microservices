package com.Api_Gateway.Controller;

import com.Api_Gateway.Model.Users;
import com.Api_Gateway.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController
{
    @Autowired
    private UserService service;

    @GetMapping(value = "/users")
    public List<Users> allUsers()
    {
        return service.getUsers();
    }

    @PostMapping(value = "/add")
    public Users register(@RequestBody Users user)
    {
        return service.addUser(user);
    }

}
