package com.solution.hacktrail.service;

import com.solution.hacktrail.exceptions.ApiException;
import com.solution.hacktrail.exceptions.ResourceNotFoundException;
import com.solution.hacktrail.model.Product;
import com.solution.hacktrail.payload.ProductDTO;
import com.solution.hacktrail.payload.ProductResponse;
import com.solution.hacktrail.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ModelMapper modelMapper;

    @Value("${image.base.url}")
    private String imageBaseUrl;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String keyWord) {

            Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

            Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);

            Specification<Product> spec = Specification.where(null);
            if(keyWord !=null && !keyWord.isEmpty()){
                spec = spec.and((root,query,criteriaBuilder) ->
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), "%" + keyWord.toLowerCase() + "%"));
            }


            Page<Product> pageProducts = productRepository.findAll(spec, pageDetails);

            List<Product> products = pageProducts.getContent();

            if(products.isEmpty()){
                throw new ApiException("No Product Created Till Now !!!");
            }
            List<ProductDTO> productDTOs = products.stream()
                    .map(p -> {
                        ProductDTO productDTO = modelMapper.map(p, ProductDTO.class);
                        productDTO.setImage(constructImageUrl(p.getImage()));
                        return productDTO;
                    })
                    .toList();

            ProductResponse productResponse = new ProductResponse();
            productResponse.setContent(productDTOs);
            productResponse.setPageNumber(pageProducts.getNumber());
            productResponse.setPageSize(pageProducts.getSize());
            productResponse.setTotalElements(pageProducts.getTotalElements());
            productResponse.setTotalPages(pageProducts.getTotalPages());
            productResponse.setLastPage(pageProducts.isLast());

            return productResponse;
    }




    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);

        Product existProduct = productRepository.findByProductName(product.getProductName());

        if(existProduct != null){
            throw new ApiException("Product Name Already Exist !!!");
        }

        product.setImage("default.png");

        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product productToUpdate = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productDTO.getProductId()));


        productToUpdate.setProductName(productDTO.getProductName());
        productToUpdate.setPrice(productDTO.getPrice());
        productToUpdate.setQuantity(productDTO.getQuantity());
        productToUpdate.setImage(productDTO.getImage());


        Product savedProduct = productRepository.save(productToUpdate);

//        List<Cart> carts = cartRepository.findCartsByProductId(productId);
//
//        List<CartDTO> cartDTOS = carts.stream().map(cart -> {
//            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
//
//            List<ProductDTO> productDTOS = cart.getCartItems().stream()
//                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
//                    .collect(Collectors.toList());
//
//            cartDTO.setProduct(productDTOS);
//
//            return cartDTO;
//        }).collect(Collectors.toList());
//
//        cartDTOS.forEach(cart -> cartService.updateProductInCarts(cart.getCartId(),productId));

        return modelMapper.map(savedProduct, ProductDTO.class);
    }
    @Override
    public ProductDTO deleteProduct(Long productId) {
        Optional<Object> productOptional = productRepository.findByProductId(productId);

        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product", "productId", productId.toString());
        }

        Product product = (Product) productOptional.get();

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);

        productRepository.delete(product);

        return productDTO;
    }

    private String constructImageUrl(String imageName){
        return imageBaseUrl.endsWith("/") ? imageBaseUrl + imageName : imageBaseUrl + "/" + imageName;
    }





}
