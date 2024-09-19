package com.example.demo.User;

import com.example.demo.Address.AddressEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @PostMapping
    public UserEntity add(@RequestBody UserEntity user){
        return userService.add(user);
    }

    @PostMapping("/{userId}/address")
    public ResponseEntity<UserEntity> addAddress(@PathVariable Long userId, @RequestBody AddressEntity address){
        UserEntity user = userService.addAddressToUser(userId,address);
        return ResponseEntity.ok(user);
    }

}

