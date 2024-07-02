package com.gear2go.mapper;

import com.gear2go.dto.response.AddressResponse;
import com.gear2go.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressMapper {

    public AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber(),
                address.getPostalCode(),
                address.getCity()
        );
    }

    public List<AddressResponse> toAddressList(List<Address> addressList) {
        return addressList.stream()
                .map(this::toAddressResponse)
                .toList();
    }
}
