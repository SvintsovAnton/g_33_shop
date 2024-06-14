package de.ait_tr.g_33_shop.service.interfaces;

import de.ait_tr.g_33_shop.domain.dto.CustomerDto;

import java.math.BigDecimal;
import java.util.List;

public interface
CustomerService {

    CustomerDto save(CustomerDto customer);
    List<CustomerDto> getAllActiveCustomers();
    CustomerDto getById(Long id);
    CustomerDto update(Long id);
    void deleteById(Long id);
    void deleteByName(String name);
    CustomerDto restore(Long id);
    long getQuantityActiveCustomers();
    BigDecimal getTotalPriceCartOfCustomer(Long id);
    BigDecimal getAveragePriceCartProductinOfCustomer(long id);
    void addProductInCart(Long productId, Long CustomerId);
    void deleteProductFromCart(Long productId, Long CustomerId);
    void clearCartOfCustomer(Long id);


}
