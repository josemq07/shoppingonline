package com.onlineshop.salesservice.service;

import com.onlineshop.salesservice.dto.CartDTO;
import com.onlineshop.salesservice.model.Sale;
import com.onlineshop.salesservice.repository.CartFeignClient;
import com.onlineshop.salesservice.repository.ISaleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService implements  ISaleService{

    @Autowired
    private ISaleRepository saleRepo;

    @Autowired
    private CartFeignClient cartAPI;

    @Override
    @CircuitBreaker(name = "cart-service", fallbackMethod = "fallbackGetProduct")
    @Retry(name = "cart-service")
    public void saveSale(Long idCart){

        if( idCart!= null && idCart>=1) {

            CartDTO cart = cartAPI.getCart(idCart);

            Sale sale = new Sale();

            sale.setSaleDate(LocalDate.now());
            sale.setCartId(idCart);
            sale.setTotalAmount(cart.getTotalPrice());
            sale.setProductList(cart.getProductList());

            saleRepo.save(sale);
        }
    }

    @Override
    public List<Sale> getSales() { return saleRepo.findAll(); }

    @Override
    public Sale getSale(Long idSale) { return saleRepo.findById(idSale).orElse(null); }

    @Override
    public void deleteSale(Long idSale) {saleRepo.deleteById(idSale);}

    @Override
    public void editSale(Long idSale, Sale saleModified) {

        if(idSale!=null && saleModified!=null){

            Sale saleToModify = this.getSale(idSale);

            saleToModify.setCartId(saleModified.getCartId());
            saleToModify.setProductList(saleModified.getProductList());
            saleToModify.setSaleDate(saleModified.getSaleDate());
            saleToModify.setTotalAmount(saleModified.getTotalAmount());
            this.saveSale(saleToModify);

        } else {
            throw new IllegalArgumentException("idCart and cartModified must be not null");
        }

    }

    @Override
    public void saveSale(Sale sale) {

        saleRepo.save(sale);
    }

}
