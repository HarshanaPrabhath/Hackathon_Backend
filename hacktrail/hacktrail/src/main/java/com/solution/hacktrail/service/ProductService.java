package com.solution.hacktrail.service;

import com.solution.hacktrail.payload.ProductDTO;
import com.solution.hacktrail.payload.ProductResponse;
import jakarta.validation.Valid;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO updateProduct(@Valid Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyWord);
}
