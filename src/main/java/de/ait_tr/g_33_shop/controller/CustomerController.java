package de.ait_tr.g_33_shop.controller;

import de.ait_tr.g_33_shop.domain.entity.Customer;
import de.ait_tr.g_33_shop.service.interfaces.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer save(@RequestBody
                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Instance of a Product")
                             Customer customer) {
        return customerService.save(customer);
    }

    @Operation(
            summary = "Get one or all products",
            description = "Getting one or all products that exist in the database"
    )
    @GetMapping
    public List<Customer> getCustomer(@RequestParam(required = false) Long id) {
        if (id == null) {
            return customerService.getAllActiveCustomers();
        } else {
            Customer customer = customerService.getById(id);
            return customer == null ? null : List.of(customer);
        }
    }


    @PutMapping
    public Customer update(@RequestParam Long id) {
        return customerService.update(id);
    }

    @DeleteMapping
    public void delete(@RequestParam(required = false) Long id, @RequestParam(required = false) String name) {
        if (id != null) {
            customerService.deleteById(id);
        } else if (name != null) {
            customerService.deleteByName(name);
        }
    }

    @PutMapping("/restore")
    public Customer restore(@RequestParam Long id) {
        return customerService.restore(id);
    }

    @GetMapping("/quantity")
    public long getQuantityActiveCustomers() {
        return customerService.getQuantityActiveCustomers();
    }

    @GetMapping("/total-cart-price")
    public BigDecimal getTotalPriceCartOfCustomer(@RequestParam Long id) {
        return customerService.getTotalPriceCartOfCustomer(id);
    }

    @GetMapping("/average-cart-price")
    public BigDecimal getAveragePriceProductInCartOfCustomer(@RequestParam Long id) {
        return customerService.getAveragePriceCartProductinOfCustomer(id);
    }

    @PutMapping("/product")
    public void getProductInCart(@RequestParam Long productId, @RequestParam Long customerId) {
        customerService.addProductInCart(productId, customerId);
    }

    @DeleteMapping("/product")
    public void deleteProductsFromCart(@RequestParam(required = false) Long productId, @RequestParam(required = false) Long customerId) {
        if (productId == null) {
            customerService.clearCartOfCustomer(customerId);
        } else if (productId != null) {
            customerService.deleteProductFromCart(productId, customerId);
        }
    }


}


