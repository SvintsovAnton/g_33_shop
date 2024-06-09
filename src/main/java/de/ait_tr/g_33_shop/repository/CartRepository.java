package de.ait_tr.g_33_shop.repository;

import de.ait_tr.g_33_shop.domain.entity.Cart;
import de.ait_tr.g_33_shop.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository<Cart,Long> {
}
