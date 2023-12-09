package com.onlineshop.shoppingcartservice.controller;

import com.onlineshop.shoppingcartservice.model.Cart;
import com.onlineshop.shoppingcartservice.repository.ProductFeignClient;
import com.onlineshop.shoppingcartservice.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartServ;

    @Autowired
    private ProductFeignClient productAPI;

    @GetMapping("/getCart/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {

        if(id!=null && id>=1){
            return new ResponseEntity<>(cartServ.getCart(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-carts")
    public ResponseEntity<List<Cart>> getAllCarts(){ return new ResponseEntity<>(cartServ.getCarts(),HttpStatus.OK); }


    @PostMapping("/createCart")
    public ResponseEntity<String> createCart(@RequestBody(required = false) Cart cart) {
        try {
            if (cart == null) {
                Cart carrito = new Cart();
                cartServ.saveCart(carrito);

            } else {
                    cartServ.saveCart(cart);
            }
                return new ResponseEntity<>("Cart created successfully!", HttpStatus.CREATED);

            } catch (RuntimeException e) {

            return new ResponseEntity<>("Error creating cart!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/add-amount/{id-product}/{id-cart}")
    public ResponseEntity<String> addProductAmount(@PathVariable("id-product") Long idProduct,
                                                   @PathVariable("id-cart") Long idCart) {

        try{
            cartServ.addAmount(idCart, idProduct);
            return new ResponseEntity<>("Cart modified successfully", HttpStatus.OK);

        }     catch (Exception e) {

            return new ResponseEntity<>("Error modifying cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/discount-amount/{id-product}/{id-cart}")
    public ResponseEntity<String> discountProductAmount(@PathVariable("id-product") Long idProduct,
                                                        @PathVariable("id-cart") Long idCart) {
        try {
            cartServ.discountAmount(idCart, idProduct);
            return new ResponseEntity<>("Cart modified successfully", HttpStatus.OK);

        }     catch (Exception e) {

            return new ResponseEntity<>("Error modifying cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/add-product/{id-product}/{id-cart}")
    public ResponseEntity<String> addProductById(@PathVariable("id-product") Long idProduct,
                                                 @PathVariable("id-cart") Long idCart) {
        try {
            cartServ.addProductById(idProduct, idCart);
            return new ResponseEntity<>("Product add successfully", HttpStatus.OK);

        }     catch (Exception e) {

            return new ResponseEntity<>("Error trying to add product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/remove-product/{id-product}/{id-cart}")
    public ResponseEntity<String> removeProductById(@PathVariable("id-product") Long idProduct,
                                                        @PathVariable("id-cart") Long idCart){
        try{
            cartServ.removeProductById(idProduct,idCart);
            return new ResponseEntity<>("Product remove successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error trying to remove product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit-cart/{id-cart}")
    public ResponseEntity<String> editCart(@PathVariable("id-cart")Long idCart,
                                           @RequestBody Cart cartModified){
        try{
            cartServ.editCartExceptId(idCart,cartModified);
            return new ResponseEntity<>("Cart edited successfully", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error trying to edit cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
