package com.example.demo.User;

import com.example.demo.Address.AddressEntity;
import com.example.demo.Address.AddressRepository;
import com.example.demo.DTO.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



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
    public UserEntity edit(Long userId, UserEntity userEntity) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("not found user");
        }
        userEntity.setId(userId);
        return userRepository.save(userEntity);
    }

    @Transactional
    public void remove(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("not found user");
        }
        userRepository.deleteById(userId);

    }

    @Transactional
    public UserEntity addAddressToUser(Long idUser, Long addressId) {
        UserEntity user = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        AddressEntity address = addressRepository.findById(addressId)
                .orElseThrow(() -> new UserNotFoundException("address not found"));
        user.getAddresses().add(address);
        address.setUser(user);
        addressRepository.save(address);
        return userRepository.save(user);
    }
}
