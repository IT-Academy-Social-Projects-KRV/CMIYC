package com.ms.authority.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.ms.authority.dto.UserDto;
import com.ms.authority.entity.Role;
import com.ms.authority.entity.User;
import com.ms.authority.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User userByEmail = userRepository.findByEmail(username);
        if (userByEmail == null)
            throw new UsernameNotFoundException("Unable to find user with this email");

        return userByEmail;
    }

    public Set<UserDto> listUsersRequest() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toSet());
    }

    private UserDto convertToUserDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setActive(user.isEnabled());
        userDto.setRegisterDate(user.getRegisterDate());
        userDto.setScopes(user.getRoles().stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet()));

        return userDto;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 8 Should be enough ==> https://security.stackexchange.com/a/83382
        return new BCryptPasswordEncoder(8);
    }
}
