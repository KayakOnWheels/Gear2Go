package com.gear2go.domain.dto.request.address;

public record UpdateAddressRequest(Long id, String street, String buildingNumber, String apartmentNumber, String postalCode, String city) {
}
