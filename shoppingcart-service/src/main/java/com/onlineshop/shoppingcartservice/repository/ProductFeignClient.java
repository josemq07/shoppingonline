package com.onlineshop.shoppingcartservice.repository;

import com.onlineshop.shoppingcartservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productsAPI", url="http://localhost:8002/products")
public interface ProductFeignClient {

    @GetMapping("/get/{id}")
    public ProductDTO getProduct(@PathVariable Long id);
}
