package com.gear2go.dto.request.address;

public record CreateAddressRequest(String street, String buildingNumber, String apartmentNumber, String postal_code, String city) {
}
