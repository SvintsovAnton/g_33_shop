package de.ait_tr.g_33_shop.service.interfaces;

import de.ait_tr.g_33_shop.domain.entity.Product;
import de.ait_tr.g_33_shop.domain.entity.Cart;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {

    Product addProduct(Long productId, Long cartId);

    Map<Product, Integer> getAllActiveProductInCart(Long cartId);

    void deleteProductFromCart(Long cartId, Long productId);

    void cleanCart(Long cartId);

    BigDecimal getTotalCartPrice(Long cartId);

    BigDecimal getAverageCartPrice(Long cartId);


}
