package com.example.demo.Address;

import com.example.demo.ENUM.AddressType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    public ResponseEntity<AddressEntity> addAddress(@RequestBody AddressEntity address) {
      String street = address.getStreet();
      String city = address.getCity();
      String postalCode = address.getPostalCode();
      String country = address.getCountry();
      AddressType addressType = address.getAddressType();

        AddressEntity addressEntity = addressService.addAddress(street, city,
                postalCode, country, addressType);
        return ResponseEntity.ok(addressEntity);
    }
    @PostMapping("/{idAddress}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long idAddress){
        addressService.deleteAddress(idAddress);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
        public ResponseEntity<AddressEntity> updateAddress(@RequestBody AddressEntity addressE){
        addressService.updateAddress(addressE.getId(),addressE.getStreet(),addressE.getCity(),
                addressE.getPostalCode(),addressE.getCountry(),addressE.getAddressType());
return ResponseEntity.ok().build();
        }
    }


