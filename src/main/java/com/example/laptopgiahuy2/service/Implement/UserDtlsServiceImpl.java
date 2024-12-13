package com.example.laptopgiahuy2.service.Implement;

import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.repository.UserDtlsRepository;
import com.example.laptopgiahuy2.service.UserDtlsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDtlsServiceImpl implements UserDtlsService {
    private UserDtlsRepository userDtlsRepository;

    public UserDtlsServiceImpl(UserDtlsRepository userDtlsRepository, PasswordEncoder passwordEncoder) {
        this.userDtlsRepository = userDtlsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private PasswordEncoder passwordEncoder;
    @Override
    public UserDtls saveUserDtls(UserDtls userDtls) {
        userDtls.setRole("ROLE_USER");
        String encodedPassword = passwordEncoder.encode(userDtls.getPassword());
        userDtls.setPassword(encodedPassword);
        UserDtls savedUserDtls = userDtlsRepository.save(userDtls);
        return savedUserDtls ;
    }
}
