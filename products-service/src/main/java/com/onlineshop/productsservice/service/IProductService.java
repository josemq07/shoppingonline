package com.onlineshop.productsservice.service;

import com.onlineshop.productsservice.model.Product;

import java.util.List;

public interface IProductService {

    public void saveProduct(Product prod);
    public List<Product> getProducts();

    public Product findProduct(Long id);

    public void deleteProduct(Long id);

    public boolean editProduct(Long id, Product prod);
}
