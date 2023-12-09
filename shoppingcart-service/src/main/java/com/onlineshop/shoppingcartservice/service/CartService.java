package com.onlineshop.shoppingcartservice.service;

import com.onlineshop.shoppingcartservice.dto.ProductDTO;
import com.onlineshop.shoppingcartservice.model.Cart;
import com.onlineshop.shoppingcartservice.repository.ICartRepository;
import com.onlineshop.shoppingcartservice.repository.ProductFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;



@Service
public class CartService implements ICartService {


    @Autowired
    private ICartRepository cartRep;

    @Autowired
    private ProductFeignClient productAPI;

    @Override
    public Cart getCart(Long id) { return cartRep.findById(id).orElse(null); }

    @Override
    public List<Cart> getCarts() { return cartRep.findAll(); }

    @Override
    public void deleteCart(Long id) { cartRep.deleteById(id); }

    @Override
    public void saveCart(Cart cart) {  cartRep.save(cart); }

    @Override
    public void editCartExceptId(Long idCart, Cart cartModified) {

        if(idCart!=null && cartModified!=null){

            Cart cartToModify = this.getCart(idCart);

            cartToModify.setProductList(cartModified.getProductList());
            cartToModify.setTotalPrice(cartModified.getTotalPrice());
            this.saveCart(cartToModify);

        } else {
            throw new IllegalArgumentException("idCart and cartModified must be not null");
        }

    }
    @Override
    @Transactional
    public void addAmount(Long idCart, Long idProduct) {

        if (idCart != null && idProduct != null) {

            Cart cart = this.getCart(idCart);
            ProductDTO product = this.getProduct(idProduct);
            double amount = product.getProduct_price();
            cart.setTotalPrice(cart.getTotalPrice() + amount);
            cart.addProductToList(idProduct);
            cartRep.save(cart);

        } else{

            throw new IllegalArgumentException("idCart and idProduct must be not null");
        }

    }

    @Override
    @Transactional
    public void discountAmount(Long idCart, Long idProduct) {

        if (idCart != null && idProduct != null) {

            Cart cart = this.getCart(idCart);
            double mount = this.getProduct(idProduct).getProduct_price();

            cart.setTotalPrice(cart.getTotalPrice() - mount);
            cart.discountProductToList(idProduct);
            cartRep.save(cart);
        } else{
            throw new IllegalArgumentException("idCart and idProduct must be not null");
        }

    }

    @Override
    @Transactional
    public void addProductById(Long idProduct, Long idCart) {


        if (idCart != null && idProduct != null) {
            Cart cart = this.getCart(idCart);
            ProductDTO product = this.getProduct(idProduct);
            List<Long> list = cart.getProductList();
            list.add(idProduct);
            cartRep.save(cart);
        } else {
            throw new IllegalArgumentException("Invalid arguments: idCart and idProduct must not be null");
        }
    }


    @Override
    @Transactional
    public void removeProductById(Long idProduct, Long idCart) {


        if (idProduct != null && idCart != null) {

            Cart cart = this.getCart(idCart);
            ProductDTO product = this.getProduct(idProduct);

            List<Long> productList = cart.getProductList();

            if (productList.remove(idProduct)) {
                cartRep.save(cart);
            } else {
                throw new IllegalArgumentException("Product not found in the cart");
            }
        } else {
            throw new IllegalArgumentException("Invalid arguments: idCart and idProduct must be not null");
        }
    }


    @Override
    @CircuitBreaker(name = "products-service", fallbackMethod = "fallbackGetProduct")
    @Retry(name = "products-service")
    public ProductDTO getProduct(Long id) {

        //createException();

        return productAPI.getProduct(id);

    }
    public ProductDTO fallbackGetProduct(Throwable throwable){

        return new ProductDTO(9999L,"FALLIDO","FALLIDO");
    }

    public void createException(){

        throw new IllegalArgumentException("Prueba Resilience4J Y Circuit Breaker");
    }
}






