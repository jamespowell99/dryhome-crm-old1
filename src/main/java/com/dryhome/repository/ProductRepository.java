package com.dryhome.repository;

import com.dryhome.domain.Product;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
public interface ProductRepository extends JpaRepository<Product,Long> {

}
