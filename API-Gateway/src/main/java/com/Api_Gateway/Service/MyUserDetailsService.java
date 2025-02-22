package com.Api_Gateway.Service;

import com.Api_Gateway.Model.UserPrincipal;
import com.Api_Gateway.Model.Users;
import com.Api_Gateway.Repository.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MyUserDetailsService implements ReactiveUserDetailsService
{
    @Autowired
    private MyUserRepo repo;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user=repo.findByUserName(username);
//        return new UserPrincipal(user);
//    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.justOrEmpty(repo.findByUserName(username))
                .map(UserPrincipal::new);
    }
}
