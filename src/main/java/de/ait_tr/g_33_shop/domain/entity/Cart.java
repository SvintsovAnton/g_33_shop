package de.ait_tr.g_33_shop.domain.entity;

import java.util.List;
import java.util.Objects;

public class Cart {

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
        Cart bucket = (Cart) o;
        return Objects.equals(id, bucket.id) && Objects.equals(productList, bucket.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productList);
    }

    @Override
    public String toString() {
        return String.format("Cart: %s");
    }
}
