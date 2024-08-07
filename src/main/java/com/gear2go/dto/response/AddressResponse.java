package com.gear2go.dto.response;

public record AddressResponse(Long id, String street, String buildingNumber, String apartmentNumber, String postalCode, String city) {
}
