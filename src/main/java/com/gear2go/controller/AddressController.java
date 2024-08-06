package com.gear2go.controller;

import com.gear2go.dto.request.address.CreateAddressRequest;
import com.gear2go.dto.request.address.UpdateAddressRequest;
import com.gear2go.dto.response.AddressResponse;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Returns list of all addresses in database", description = "Roles required: ADMIN")
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Returns address with given id", description = "Roles required: ADMIN")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(addressService.getAddress(id));
    }


    @GetMapping
    @Operation(summary = "Returns list of addresses of user making request", description = "Authenticated only")
    public ResponseEntity<AddressResponse> getAddress() throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(addressService.getAddress());
    }


    @PostMapping
    @Operation(summary = "Creates address assigned to user making request", description = "Authenticated only")
    public ResponseEntity<AddressResponse> createAddress(@RequestBody CreateAddressRequest createAddressRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(addressService.createAddress(createAddressRequest));
    }


    @PutMapping
    @Operation(summary = "Updates address with given id if it is assigned to user making request", description = "Authenticated only")
    public ResponseEntity<AddressResponse> updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest) throws ExceptionWithHttpStatusCode {
        return ResponseEntity.ok(addressService.updateAddress(updateAddressRequest));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes address with given id to user making request", description = "Roles required: ADMIN")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) throws ExceptionWithHttpStatusCode {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

}
