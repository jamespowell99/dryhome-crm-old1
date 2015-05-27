package com.dryhome.repository;

import com.dryhome.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(
        value = "SELECT c FROM Customer c WHERE LOWER(c.name) " +
            "LIKE LOWER(CONCAT('%',:searchTerm,'%')) " +
            "OR c.id like CONCAT('%', :searchTerm, '%') " +
            "OR c.address1 like CONCAT('%', :searchTerm, '%') " +
            "OR c.town like CONCAT('%', :searchTerm, '%') " +
            "OR c.postCode like CONCAT('%', :searchTerm, '%') " +
            "OR c.tel like CONCAT('%', :searchTerm, '%') " +
            "OR c.mob like CONCAT('%', :searchTerm, '%')",
        countQuery = "SELECT count(c) FROM Customer c WHERE LOWER(c.name) " +
            "LIKE LOWER(CONCAT('%',:searchTerm,'%')) " +
            "OR c.id like CONCAT('%', :searchTerm, '%')  " +
            "OR c.address1 like CONCAT('%', :searchTerm, '%') " +
            "OR c.town like CONCAT('%', :searchTerm, '%') " +
            "OR c.postCode like CONCAT('%', :searchTerm, '%') " +
            "OR c.tel like CONCAT('%', :searchTerm, '%') " +
            "OR c.mob like CONCAT('%', :searchTerm, '%')"
    )
    public Page<Customer> findByMultipleFields(@Param("searchTerm") String searchTerm, Pageable pageable);

}
