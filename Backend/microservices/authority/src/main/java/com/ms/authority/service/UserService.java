package com.ms.authority.service;

import com.ms.authority.entity.User;
import com.ms.authority.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User userByEmail = userRepository.findByEmail(username);
        if(userByEmail == null)
            throw new UsernameNotFoundException("Unable to find user with this email");

        return userByEmail;
    }

    public User changeUserActive(String userId, boolean isActive) throws UsernameNotFoundException {
        User user = userRepository.findById(Integer.parseInt(userId))
                .orElseThrow(() -> new UsernameNotFoundException("Unable to find user with this id"));
        user.setActive(isActive);
        return userRepository.save(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 8 Should be enough ==> https://security.stackexchange.com/a/83382
        return new BCryptPasswordEncoder(8);
    }
}
