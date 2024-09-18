package com.example.demo.Address;

import com.example.demo.DTO.AddressNotFoundException;
import com.example.demo.ENUM.AddressType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    public AddressEntity addAddress(String street, String city, String postalCode, String country, AddressType addressType) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(street);
        addressEntity.setCity(city);
        addressEntity.setPostalCode(postalCode);
        addressEntity.setCountry(country);
        addressEntity.setAddressType(addressType);
        return addressRepository.save(addressEntity);
    }

    public AddressEntity updateAddress(Long addressId, String street, String city, String postalCode, String country, AddressType addressType) {
        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found for ID: " + addressId));
        addressEntity.setStreet(street);
        addressEntity.setCity(city);
        addressEntity.setPostalCode(postalCode);
        addressEntity.setCountry(country);
        addressEntity.setAddressType(addressType);
        return addressRepository.save(addressEntity);
    }


    public void deleteAddress(Long addressId) {
        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found for ID: " + addressId));
        addressRepository.delete(addressEntity);
    }
}
