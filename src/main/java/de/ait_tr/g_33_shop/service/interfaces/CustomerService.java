package de.ait_tr.g_33_shop.service.interfaces;

import de.ait_tr.g_33_shop.domain.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);
    List<Customer> getAllActiveCustomers();
    Customer getById(Long id);
    Customer update(Long id);
    void deleteById(Long id);
    void deleteByName(String name);
    Customer restore(Long id);
    long getQuantityActiveCustomers();
    BigDecimal getTotalPriceCartOfCustomer(Long id);
    BigDecimal getAveragePriceCartProductinOfCustomer(long id);
    void addProductInCart(Long productId, Long CustomerId);
    void deleteProductFromCart(Long productId, Long CustomerId);
    void clearCartOfCustomer(Long id);


}
