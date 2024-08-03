package com.gear2go.domain.dto.response;

public record AddressResponse(Long id, String street, String buildingNumber, String apartmentNumber, String postal_code, String city) {
}
