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
    public UserEntity add(@RequestBody UserEntity user){
        return userService.add(user);
    }

    @PostMapping("/{userId}/address")
    public ResponseEntity<UserEntity> addAddress(@PathVariable Long userId, @RequestBody AddressEntity address){
        UserEntity user = userService.addAddressToUser(userId,address);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<?> edit( @RequestBody UserEntity user){
        try {
            UserEntity updatedUser = userService.edit(user);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> remove(@PathVariable Long userId){
        userService.remove(userId);
        return ResponseEntity.ok().build();
    }



    }



