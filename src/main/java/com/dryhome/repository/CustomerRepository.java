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
        value = "SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:searchTermCompanyName,'%'))",
        countQuery = "SELECT count(c) FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:searchTermCompanyName,'%'))"
    )
    public Page<Customer> findByCompanyNameLike(@Param("searchTermCompanyName") String searchTermCompanyName, Pageable pageable);

}
