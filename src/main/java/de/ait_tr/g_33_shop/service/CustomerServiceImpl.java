package de.ait_tr.g_33_shop.service;

import de.ait_tr.g_33_shop.domain.dto.CustomerDto;
import de.ait_tr.g_33_shop.domain.entity.Customer;
import de.ait_tr.g_33_shop.repository.CustomerRepository;
import de.ait_tr.g_33_shop.service.interfaces.CustomerService;
import de.ait_tr.g_33_shop.service.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;
    private CustomerMappingService mappingService;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public CustomerDto save(CustomerDto customer) {
        Customer entity = mappingService.mapDtoInEntity(customer);
        repository.save(entity);
        return mappingService.mapEntityToDto(entity);

    }

    @Override
    public List<CustomerDto> getAllActiveCustomers() {
        return repository.findAll().stream().filter(Customer::isActive).map(mappingService::mapEntityToDto).toList();
    }

    @Override
    public CustomerDto getById(Long id) {
        Customer customer = repository.findById(id).orElse(null);
        if (customer != null && customer.isActive()) {
            return mappingService.mapEntityToDto(customer);
        }
        return null;
    }

    @Override
    public CustomerDto update(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByName(String name) {

    }

    @Override
    public CustomerDto restore(Long id) {
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
