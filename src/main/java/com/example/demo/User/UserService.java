package com.example.demo.User;

import com.example.demo.Address.AddressEntity;
import com.example.demo.Address.AddressRepository;
import com.example.demo.DTO.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private AddressRepository addressRepository;

    @Transactional
    public UserEntity add(UserEntity user) {
//        user.setName(abc+user.getName());
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity edit(UserEntity userEntity) {
        Long userId = userEntity.getId();
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("not found user");
        }
        userEntity.setId(userId);
        return userRepository.save(userEntity);
    }

    @Transactional
    public void remove(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException("not found user");
        }
        userRepository.deleteById(userId);

    }
@Transactional
    public UserEntity addAddressToUser(Long idUser, AddressEntity address) {
        UserEntity user = userRepository.findById(idUser)
                .orElseThrow(()-> new UserNotFoundException("user not found"));
        address.setUser(user);
        user.getAddresses().add(address);
        addressRepository.save(address);
        return userRepository.save(user);
    }
}
