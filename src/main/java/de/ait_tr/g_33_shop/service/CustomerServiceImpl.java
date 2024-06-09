package de.ait_tr.g_33_shop.service;

import de.ait_tr.g_33_shop.domain.entity.Customer;
import de.ait_tr.g_33_shop.repository.CustomerRepository;
import de.ait_tr.g_33_shop.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer save(Customer customer) {
        customer.setId(null);
        customer.setActive(true);
        return repository.save(customer);
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        return repository.findAll().stream().filter(Customer::isActive).toList();
    }

    @Override
    public Customer getById(Long id) {
        return repository.findById(id).orElse(null);
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
