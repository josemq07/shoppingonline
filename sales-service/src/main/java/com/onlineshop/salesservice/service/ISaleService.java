package com.onlineshop.salesservice.service;

import com.onlineshop.salesservice.model.Sale;

import java.util.List;

public interface ISaleService {

public void saveSale(Long id);

public List<Sale> getSales();

public Sale getSale(Long idSale);

public void deleteSale(Long idSale);

public void editSale(Long idSale, Sale saleModified);

public void saveSale(Sale sale);
}
