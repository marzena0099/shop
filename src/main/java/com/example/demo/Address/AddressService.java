package com.example.demo.Address;

import com.example.demo.DTO.AddressNotFoundException;
import com.example.demo.ENUM.AddressType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
@Transactional
    public boolean isValidAddressType(String addressType) {
        return List.of("RESIDENTIAL", "BILLING", "SHIPPING").contains(addressType);
    }
    @Transactional
    public AddressEntity addAddress(AddressEntity address) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(address.getStreet());
        addressEntity.setCity(address.getCity());
        addressEntity.setPostalCode(address.getPostalCode());
        addressEntity.setCountry(address.getCountry());
       if(isValidAddressType(address.getAddressType().name())){
           addressEntity.setAddressType(address.getAddressType());
       }
        return addressRepository.save(addressEntity);
    }

    public void updateAddress(Long addressId, String street, String city, String postalCode, String country, AddressType addressType) {
        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found for ID: " + addressId));
        addressEntity.setStreet(street);
        addressEntity.setCity(city);
        addressEntity.setPostalCode(postalCode);
        addressEntity.setCountry(country);
        addressEntity.setAddressType(addressType);
        addressRepository.save(addressEntity);
    }


    public void deleteAddress(Long addressId) {
        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found for ID: " + addressId));
        addressRepository.delete(addressEntity);
    }
}
