package de.ait_tr.g_33_shop.service;

import de.ait_tr.g_33_shop.domain.entity.Product;
import de.ait_tr.g_33_shop.repository.ProductRepository;
import de.ait_tr.g_33_shop.service.interfaces.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private ProductRepository repository;

    public CartServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product addProduct(Long productId, Long cartId) {
        return null;
    }

    @Override
    public Map<Product, Integer> getAllActiveProductInCart(Long cartId) {
        return null;
    }

    @Override
    public void deleteProductFromCart(Long cartId, Long productId) {

    }

    @Override
    public void cleanCart(Long cartId) {

    }

    @Override
    public BigDecimal getTotalCartPrice(Long cartId) {
        return null;
    }

    @Override
    public BigDecimal getAverageCartPrice(Long cartId) {
        return null;
    }
}
