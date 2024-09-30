package com.example.demo.Address;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

@PostMapping
public ResponseEntity<AddressEntity> add(@RequestBody AddressEntity address){
    AddressEntity addressRes = addressService.addAddress(address);
    return ResponseEntity.ok(addressRes);
}

    @DeleteMapping("/{idAddress}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long idAddress){
        addressService.deleteAddress(idAddress);
        return ResponseEntity.ok().build();
    }

    @PutMapping
        public ResponseEntity<?> updateAddress(@RequestBody AddressEntity addressE){
        addressService.updateAddress(addressE.getId(),addressE.getStreet(),addressE.getCity(),
                addressE.getPostalCode(),addressE.getCountry(),addressE.getAddressType());
return ResponseEntity.ok().build();
        }
    }


