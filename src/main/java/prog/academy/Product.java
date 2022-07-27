package prog.academy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Float price;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "product_orders",
    joinColumns = @JoinColumn(name="product_id"),
    inverseJoinColumns = @JoinColumn(name = "order"))
    private Set<Order> orders = new HashSet<>();

    public Product() {
    }

    public Product(Long id, String title, Float price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public void addOrder(Order order){
        orders.add(order);
        order.addProduct(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(title, product.title) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price);
    }
}
