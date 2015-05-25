package com.dryhome.repository;

import com.dryhome.domain.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query(
        value = "SELECT * FROM t_customer WHERE LOWER(name) LIKE LOWER(CONCAT('%',:searchTermCompanyName,'%'))",
        nativeQuery = true
    )
    public List<Customer> findByCompanyNameLike(@Param("searchTermCompanyName") String searchTermCompanyName);

}
