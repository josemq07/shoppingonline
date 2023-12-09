package com.onlineshop.salesservice.repository;

import com.onlineshop.salesservice.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cartAPI", url="http://localhost:8001/cart")
public interface CartFeignClient {

    @GetMapping("/getCart/{id}")
    public CartDTO getCart(@PathVariable Long id);
}
