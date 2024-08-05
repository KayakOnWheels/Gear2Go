package com.gear2go.controller;

import com.gear2go.dto.request.address.CreateAddressRequest;
import com.gear2go.dto.request.address.UpdateAddressRequest;
import com.gear2go.dto.response.AddressResponse;
import com.gear2go.exception.AddressNotFoundException;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/address")
public class AddressController {

    private final AddressService addressService;
    @GetMapping("/all")
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(addressService.getAddress(id));
    }

    @GetMapping()
    public ResponseEntity<AddressResponse> getAddressById() throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(addressService.getAddress());
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@RequestBody CreateAddressRequest createAddressRequest) throws ExceptionWithHttpStatusCode{
        return ResponseEntity.ok(addressService.createAddress(createAddressRequest));
    }

    @PutMapping
    public ResponseEntity<AddressResponse> updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest) throws ExceptionWithHttpStatusCode{
        return ResponseEntity.ok(addressService.updateAddress(updateAddressRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) throws ExceptionWithHttpStatusCode{
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

}
