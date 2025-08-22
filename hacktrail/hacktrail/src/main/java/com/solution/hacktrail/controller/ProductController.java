package com.solution.hacktrail.controller;


import com.solution.hacktrail.config.AppConstants;
import com.solution.hacktrail.payload.ProductDTO;
import com.solution.hacktrail.payload.ProductResponse;
import com.solution.hacktrail.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getALlProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCT_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder,
            @RequestParam(name = "keyword", required = false)String keyWord


    ){
        ProductResponse productResponse =  productService.getAllProducts(pageNumber, pageSize,sortBy,sortOrder,keyWord);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


    @PostMapping("/admin/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO) {

        ProductDTO product = productService.addProduct(productDTO);

        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @PathVariable Long productId,@RequestBody ProductDTO productDTO){

        ProductDTO product = productService.updateProduct(productId,productDTO);

        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){

        ProductDTO productDTO = productService.deleteProduct(productId);

        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }




}
