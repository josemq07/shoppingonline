package com.onlineshop.shoppingcartservice.repository;

import com.onlineshop.shoppingcartservice.model.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {



}
