package de.ait_tr.g_33_shop.domain.dto;

import de.ait_tr.g_33_shop.domain.entity.Customer;
import de.ait_tr.g_33_shop.domain.entity.Product;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


public class CartDto {


    private Long id;
    private List<Product> productList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return Objects.equals(id, cartDto.id) && Objects.equals(productList, cartDto.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productList);
    }

    @Override
    public String toString() {
        return String.format("Cart: id -%d, containts %d products", id, productList == null ? 0 : productList.size());
    }
}
