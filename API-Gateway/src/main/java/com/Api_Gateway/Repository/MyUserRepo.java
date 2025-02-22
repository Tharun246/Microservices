package com.Api_Gateway.Repository;

import com.Api_Gateway.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepo extends JpaRepository<Users,Long> {
    Users findByUserName(String username);
}
