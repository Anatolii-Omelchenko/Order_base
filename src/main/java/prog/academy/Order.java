package prog.academy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany(mappedBy = "orders")
    private List<Product> products = new ArrayList<>();

    public Order() {
    }

    public Order(Long id, Client client) {
        this.id = id;
        this.client = client;
    }

    public void addProduct(Product product){
        products.add(product);
        product.addOrder(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(client, order.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client);
    }
}
