package com.onlineshop.salesservice.controller;

import com.onlineshop.salesservice.model.Sale;
import com.onlineshop.salesservice.service.SaleService;
import feign.Response;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

   @Autowired
   private SaleService saleServ;

    @PostMapping("/create-sale/{idCart}")
    public ResponseEntity<String> createSale(@PathVariable Long idCart){

       try {

           saleServ.saveSale(idCart);
           return new ResponseEntity<>("Sale created successfully!", HttpStatus.OK);
            } catch (Exception e) {
           return new ResponseEntity<>("Error creating cart", HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping("/get-all-sales")
    public ResponseEntity<List<Sale>> getAllSales(){

        return new ResponseEntity<>(saleServ.getSales(), HttpStatus.OK);
    }


    @GetMapping("/get-sale/{id-sale}")
    public ResponseEntity<Sale> getSale(@PathVariable("id-sale")Long id){

        if(id!=null && id>=1){

            return new ResponseEntity<>(saleServ.getSale(id), HttpStatus.OK);

        }else {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete-sale/{idSale}")
    public ResponseEntity<String> deleteSale(@PathVariable("idSale") Long id) {

        if (id!=null && id>=1) {

            saleServ.deleteSale(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);

        } else {

            return new ResponseEntity<>("Sale id not valid", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit-sale/{id-sale}/{editedSale}")
    public ResponseEntity<String> editSaleExceptId(@PathVariable("id-sale")Long id,
                                                   @PathVariable("editedSale")Sale sale){
        if (sale!=null && id>=1) {

            saleServ.editSale(id,sale);
            return new ResponseEntity<>("Sale edited successfully", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Sale not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-total-mount/{id-sale}")
    public ResponseEntity<Double> getTotalAmount(@PathVariable("id-sale")Long id){

        if(id!=null && id>=1){

            return new ResponseEntity<>(saleServ.getSale(id).getTotalAmount(), HttpStatus.OK);

        } else{

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
