package de.ait_tr.g_33_shop.service;

import de.ait_tr.g_33_shop.domain.entity.Customer;
import de.ait_tr.g_33_shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        return null;
    }

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public Customer update(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public Customer restore(Long id) {
        return null;
    }

    @Override
    public long getQuantityActiveCustomers() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPriceCartOfCustomer(Long id) {
        return null;
    }

    @Override
    public BigDecimal getAveragePriceCartProductinOfCustomer(long id) {
        return null;
    }

    @Override
    public void addProductInCart(Long productId, Long CustomerId) {

    }

    @Override
    public void deleteProductFromCart(Long productId, Long CustomerId) {

    }

    @Override
    public void clearCartOfCustomer(Long id) {

    }
}
