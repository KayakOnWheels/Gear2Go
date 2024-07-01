package com.gear2go.dto.response;

import java.time.LocalDate;

public record CartResponse (Long id, Long userId, LocalDate rentDate, LocalDate returnDate){
}
