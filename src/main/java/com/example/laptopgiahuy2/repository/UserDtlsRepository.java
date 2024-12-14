package com.example.laptopgiahuy2.repository;

import com.example.laptopgiahuy2.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDtlsRepository extends JpaRepository<UserDtls, Integer> {
    public UserDtls findByEmail(String email);
    public List<UserDtls> findByRole(String role);
}
