package com.gear2go.controller;

import com.gear2go.dto.request.address.CreateAddressRequest;
import com.gear2go.dto.request.address.UpdateAddressRequest;
import com.gear2go.dto.response.AddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/address")
public class AddressController {

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddresss() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody CreateAddressRequest createAddressRequest) {
        return null;
    }

    @PutMapping
    public ResponseEntity<AddressResponse> updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        return null;
    }

}
