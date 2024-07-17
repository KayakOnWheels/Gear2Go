package com.gear2go.repository;

import com.gear2go.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query
    Integer findNumberOfProductsRentedInDataRange(@Param("ID") Long id, @Param("RENT_DATE") LocalDate rentDate, @Param("RETURN_DATE") LocalDate returnDate);

}
