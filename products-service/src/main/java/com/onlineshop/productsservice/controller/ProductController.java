package com.onlineshop.productsservice.controller;

import com.onlineshop.productsservice.model.Product;
import com.onlineshop.productsservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService prodServ;

    //Probando el balanceador de carga levantando 2 instancias en los puertos 8002 y 8004
    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/get-product/{id}")
    public ResponseEntity<Product> getCart(@PathVariable Long id) {

        if( id != null && id>=1 ) {

            return new ResponseEntity<>(prodServ.findProduct(id), HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Product>> getProducts(){

        List<Product> listproducts = prodServ.getProducts();

        if(listproducts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else{
            return new ResponseEntity<>(listproducts, HttpStatus.OK);
        }


    }
    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Product prod) {

        if (prod.isValid()) {
            try {
                prodServ.saveProduct(prod);
                return new ResponseEntity<>("Successfully created product", HttpStatus.CREATED);
            } catch (Exception e) {

                e.printStackTrace();
                return new ResponseEntity<>("Failed to create a product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Invalid product data: Check product ID and price", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        Product product = prodServ.findProduct(id);

        if (product!=null) {

            prodServ.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);

        } else {

            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editProduct(@PathVariable Long id, @RequestBody Product prod) {


        if (prod.isValid() && id!=null) {

            prodServ.editProduct(id,prod);
            return new ResponseEntity<>("Product edited successfully", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

    }
}