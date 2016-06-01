package com.dryhome.repository;

import com.dryhome.domain.Order;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Order entity.
 */
public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findByCustomerId(Long customerId);

}
