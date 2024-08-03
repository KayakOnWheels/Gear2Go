package com.gear2go.service;

import com.gear2go.domain.dto.request.address.CreateAddressRequest;
import com.gear2go.domain.dto.request.address.UpdateAddressRequest;
import com.gear2go.domain.dto.response.AddressResponse;
import com.gear2go.entity.Address;
import com.gear2go.exception.AddressNotFoundException;
import com.gear2go.mapper.AddressMapper;
import com.gear2go.repository.AddressRepository;
import com.gear2go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private final AddressMapper addressMapper;

    public List<AddressResponse> getAllAddresses() {
        List<Address> addressList = addressRepository.findAll();
        return addressMapper.toAddressResponseList(addressList);
    }

    public AddressResponse getAddress(Long id) throws AddressNotFoundException{
        return addressMapper.toAddressResponse(addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id)));
    }

    public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {
        Address address = new Address(
                createAddressRequest.street(),
                createAddressRequest.buildingNumber(),
                createAddressRequest.apartmentNumber(),
                createAddressRequest.postal_code(),
                createAddressRequest.city(),
                userRepository.findById(createAddressRequest.userId()).orElseThrow()
                );

        addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public AddressResponse updateAddress(UpdateAddressRequest updateAddressRequest) {
        Address address = addressRepository.findById(updateAddressRequest.id()).orElseThrow();

        address.setStreet(updateAddressRequest.street());
        address.setBuildingNumber(updateAddressRequest.buildingNumber());
        address.setApartmentNumber(updateAddressRequest.apartmentNumber());
        address.setPostalCode(updateAddressRequest.postalCode());
        address.setCity(updateAddressRequest.city());

        addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow();
        addressRepository.delete(address);
    }
}