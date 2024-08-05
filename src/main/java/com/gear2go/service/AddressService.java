package com.gear2go.service;

import com.gear2go.dto.request.address.CreateAddressRequest;
import com.gear2go.dto.request.address.UpdateAddressRequest;
import com.gear2go.dto.response.AddressResponse;
import com.gear2go.entity.Address;
import com.gear2go.entity.User;
import com.gear2go.exception.AddressNotFoundException;
import com.gear2go.exception.ExceptionWithHttpStatusCode;
import com.gear2go.exception.UserNotFoundException;
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
    private final AuthenticationService authenticationService;
    private final AddressMapper addressMapper;

    public List<AddressResponse> getAllAddresses() {
        List<Address> addressList = addressRepository.findAll();
        return addressMapper.toAddressResponseList(addressList);
    }

    public AddressResponse getAddress(Long id) throws ExceptionWithHttpStatusCode{
        return addressMapper.toAddressResponse(addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id)));
    }

    public AddressResponse getAddress() throws ExceptionWithHttpStatusCode {
        User user = authenticationService.getAuthenticatedUser().orElseThrow(UserNotFoundException::new);
        return addressMapper.toAddressResponse(addressRepository.findById(user.getId()).orElseThrow(() -> new AddressNotFoundException(user.getId())));
    }

    public AddressResponse createAddress(CreateAddressRequest createAddressRequest) throws ExceptionWithHttpStatusCode{
        User user = authenticationService.getAuthenticatedUser().orElseThrow(UserNotFoundException::new);
        Address address = new Address(
                createAddressRequest.street(),
                createAddressRequest.buildingNumber(),
                createAddressRequest.apartmentNumber(),
                createAddressRequest.postal_code(),
                createAddressRequest.city(),
                user
                );

        addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public AddressResponse updateAddress(UpdateAddressRequest updateAddressRequest) throws ExceptionWithHttpStatusCode{
        Address address = addressRepository.findById(updateAddressRequest.id()).orElseThrow(() -> new AddressNotFoundException(updateAddressRequest.id()));

        address.setStreet(updateAddressRequest.street());
        address.setBuildingNumber(updateAddressRequest.buildingNumber());
        address.setApartmentNumber(updateAddressRequest.apartmentNumber());
        address.setPostalCode(updateAddressRequest.postalCode());
        address.setCity(updateAddressRequest.city());

        addressRepository.save(address);
        return addressMapper.toAddressResponse(address);
    }

    public void deleteAddress(Long id) throws ExceptionWithHttpStatusCode{
        Address address = addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
        addressRepository.delete(address);
    }
}