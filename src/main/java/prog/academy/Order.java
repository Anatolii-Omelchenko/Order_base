package prog.academy;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "my_orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany(mappedBy = "orderList")
    private List<Product> products = new ArrayList<>();

    @Column(length = 100)
    private String comment = "no comment.";

    public Order() {
    }

    public Order(Client client) {
        this.client = client;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {

        StringBuilder productsFromOrder = new StringBuilder();
        for (Product product : products) {
            productsFromOrder.append(product).append("\n");
        }

        return "Order id: " + id + " | Client: " + client.getName()
                + "\n\tProducts: \n" + productsFromOrder
                + "Comment: " + comment;
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
