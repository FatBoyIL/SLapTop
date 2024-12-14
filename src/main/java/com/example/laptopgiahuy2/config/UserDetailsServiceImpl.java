package com.example.laptopgiahuy2.config;

import com.example.laptopgiahuy2.model.UserDtls;
import com.example.laptopgiahuy2.repository.UserDtlsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDtlsRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDtls user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản của bạn");
        }
        return new CustomUser(user);
    }
}
