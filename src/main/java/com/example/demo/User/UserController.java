package com.example.demo.User;

import com.example.demo.Address.AddressEntity;
import com.example.demo.DTO.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private String abc;

    @PostMapping
    public ResponseEntity<UserEntity> add(@RequestBody UserEntity user) {
        UserEntity userEntity = userService.add(user);
        return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
    }
    @PostMapping("/{userId}/{addressId}/address")
    public ResponseEntity<UserEntity> addAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        UserEntity user = userService.addAddressToUser(userId, addressId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> edit(@PathVariable Long userId, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.edit(userId,user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> remove(@PathVariable Long userId) {
        userService.remove(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}



