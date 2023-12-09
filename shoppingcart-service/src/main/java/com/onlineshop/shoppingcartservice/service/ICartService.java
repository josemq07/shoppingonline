package com.onlineshop.shoppingcartservice.service;

import com.onlineshop.shoppingcartservice.dto.ProductDTO;
import com.onlineshop.shoppingcartservice.model.Cart;

import java.util.List;


public interface ICartService {

    public Cart getCart(Long id);

    public List<Cart> getCarts();

    public void deleteCart(Long id);
    public void saveCart(Cart cart);

    public void editCartExceptId(Long idCart, Cart cart);
    public void addAmount(Long idCart, Long idProduct);

    public void discountAmount(Long idCart, Long idProduct);

    public ProductDTO getProduct(Long id);

    public void addProductById(Long idProduct, Long idCart);

    public void removeProductById(Long idProduct, Long idCart);
}
