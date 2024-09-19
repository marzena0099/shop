package com.example.demo.User;

import com.example.demo.Address.AddressEntity;
import com.example.demo.Address.AddressRepository;
import com.example.demo.DTO.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    public UserEntity add(UserEntity user) {
    return userRepository.save(user);
    }


    public UserEntity addAddressToUser(Long idUser, AddressEntity address){
        Optional<UserEntity> userOpt = userRepository.findById(idUser);
        if(!userOpt.isPresent()){
            throw new UserNotFoundException("user not found");
        }
        UserEntity user = userOpt.get();
        address.setUser(user);
        user.getAddresses().add(address);
        addressRepository.save(address);
        return userRepository.save(user);


    }
}
