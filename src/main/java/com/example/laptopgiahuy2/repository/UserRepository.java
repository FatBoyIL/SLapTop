package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {
    UserDtls findByEmail(String email);
}
