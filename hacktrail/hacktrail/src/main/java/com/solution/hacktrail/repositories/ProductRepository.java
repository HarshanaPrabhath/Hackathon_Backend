package com.solution.hacktrail.repositories;

import com.solution.hacktrail.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductName(String productName);


    Optional<Object> findByProductId(Long productId);

    Page<Product> findAll(Specification<Product> spec, Pageable pageDetails);
}
