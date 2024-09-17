package com.example.demo.User;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public UserEntity add(UserEntity user) {
    return userRepository.save(user);
    }
}
