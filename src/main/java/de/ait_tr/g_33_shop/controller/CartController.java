package de.ait_tr.g_33_shop.controller;

import de.ait_tr.g_33_shop.domain.entity.Cart;
import de.ait_tr.g_33_shop.domain.entity.Product;
import de.ait_tr.g_33_shop.service.interfaces.CartService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Product addProduct(@RequestParam Long productId, @RequestParam Long cartId) {
        return cartService.addProduct(productId, cartId);
    }

    @GetMapping
    public Map<Product, Integer> getAllProductsInCart(@RequestParam Long cartId) {
        if (cartId != null) {
            return cartService.getAllActiveProductInCart(cartId);
        }
        return null;
    }

    @DeleteMapping
    public void deleteProductFromCart(@RequestParam Long cartId, @RequestParam(required = false) Long productId) {

        if (productId != null) {
            cartService.deleteProductFromCart(productId, cartId);
        } else {
            cartService.cleanCart(cartId);
        }
    }


    @GetMapping("/get-total-price")
    public BigDecimal getTotalCartPrice(Long cartId) {
        return cartService.getTotalCartPrice(cartId);
    }

    @GetMapping("/get-average-price")
    public BigDecimal getAverageCartPrice(Long cartId) {
        return cartService.getAverageCartPrice(cartId);
    }


}
