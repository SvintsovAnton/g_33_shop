package de.ait_tr.g_33_shop.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;



    @ManyToMany
    @JoinTable(name="cart_product",
               joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productList;

    public void addProduct(Product product){
        if(product.isActive()){
            productList.add(product);
        }
    }

    public List<Product> getAllActiveProducts (){
        return productList.stream().
                filter(Product::isActive).
                toList();
    }

    public void removeProductById(Long id){
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()){
            if (iterator.next().getId().equals(id))
                iterator.remove();
            break;
        }
    }

    public void clear(){
        productList.clear();
    }

    public BigDecimal getActiveProductsTotalCost(){
        return productList.stream()
                .filter(Product::isActive).
                map(Product::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public BigDecimal getActiveProductsAverageCost(){
        int count = getAllActiveProducts().size();
        if (count==0){
            return BigDecimal.ZERO;
        }
        return getActiveProductsTotalCost().divide(new BigDecimal(count), RoundingMode.DOWN);
    }
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(customer, cart.customer) && Objects.equals(productList, cart.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, productList);
    }

    @Override
    public String toString() {
        return String.format("Cart: id -%d, containts %d products",id,productList==null?0:productList.size());
    }
}
