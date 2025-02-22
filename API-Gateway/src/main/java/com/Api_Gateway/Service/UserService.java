package com.Api_Gateway.Service;

import com.Api_Gateway.Model.Users;
import com.Api_Gateway.Repository.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private MyUserRepo repo;

    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    public List<Users> getUsers()
    {
        return repo.findAll();
    }

    public Users addUser(Users user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }
}
