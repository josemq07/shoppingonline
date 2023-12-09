package com.onlineshop.productsservice.service;


import com.onlineshop.productsservice.model.Product;
import com.onlineshop.productsservice.repository.IProductRepository;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository prodRep;

    @Override
    public void saveProduct(Product prod) { prodRep.save(prod);}

    @Override
    public List<Product> getProducts() {
        return prodRep.findAll();
    }

    @Override
    public Product findProduct(Long id) {
        return prodRep.findById(id).orElse(null);
    }

    @Override
    public void deleteProduct(Long id) { prodRep.deleteById(id); }

    @Override
    public boolean editProduct(Long productId, Product updatedProduct) {

        Product existingProduct = prodRep.findById(productId).orElse(null);


        if (existingProduct != null && updatedProduct !=null) {

            existingProduct.setProduct_price(updatedProduct.getProduct_price());
            existingProduct.setProduct_name(updatedProduct.getProduct_name());
            existingProduct.setProduct_brand(updatedProduct.getProduct_brand());
            existingProduct.setProduct_id(updatedProduct.getProduct_id());


            prodRep.save(existingProduct);
            return true;

        } else {
            return false;
        }
    }
}