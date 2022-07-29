package prog.academy;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class OrderBaseMain {

    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {

        emf = Persistence.createEntityManagerFactory("orders_db");
        em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        fillDatabaseWithProducts();
        try {
            while (true) {
                System.out.println("1: make order\n");

                System.out.println("2: view all clients");
                System.out.println("3: view all products");
                System.out.println("4: view all orders\n");

                System.out.println("5: add client");
                System.out.println("6: add product");

                System.out.print("-> ");

                String choose = scanner.nextLine();

                if (choose.equals("1")) {
                    makeOrder(scanner);
                } else if (choose.equals("2")) {
                    viewAllClients();
                } else if (choose.equals("3")) {
                    viewAllProducts();
                } else if (choose.equals("4")) {
                    viewAllOrders();
                } else if (choose.equals("5")) {
                    addClient(scanner);
                } else if (choose.equals("6")) {
                    addProduct(scanner);
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            scanner.close();
        }

    }

    private static void viewAllClients() {

        TypedQuery<Client> query = em.createQuery("SELECT x FROM Client x", Client.class);
        List<Client> clients = query.getResultList();
        System.out.println("\n\tAll clients:");
        for (Client client : clients) {
            System.out.println(client);
        }
        System.out.println();
    }

    private static void viewAllOrders() {
        TypedQuery<Order> query = em.createQuery("SELECT x FROM Order x", Order.class);
        List<Order> orders = query.getResultList();
        System.out.println("\n\tAll orders:");
        for (Order order : orders) {
            System.out.println(order);
            System.out.println();
        }
        System.out.println();
    }

    private static void viewAllProducts() {
        TypedQuery<Product> query = em.createQuery("SELECT x FROM Product x", Product.class);
        List<Product> products = query.getResultList();
        System.out.println("\n\tAll products:");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println();

    }

    private static void makeOrder(Scanner scanner) {

        System.out.println("Input client id:");
        Long client_id = Long.valueOf(scanner.nextLine());
        Order order = new Order(em.getReference(Client.class, client_id));

        while (true) {
            System.out.println("Input product id:");
            String productIdString = scanner.nextLine();

            if (productIdString.equals("")) {
                break;
            }

            Long product_id = Long.valueOf(productIdString);

            Product product = em.getReference(Product.class, product_id);
            order.addProduct(product);
        }

        System.out.print("Add comment to order?\nY/N ->");
        String choose = scanner.nextLine();

        if (choose.equalsIgnoreCase("Y")) {
            System.out.print("Your comment:");
            String comment = scanner.nextLine();
            order.setComment(comment);
        }

        performTransaction(() -> {
            em.persist(order);
            return 0;
        });

    }

    private static void addClient(Scanner scanner) {
        System.out.println("Input client name:");
        String name = scanner.nextLine();
        Client client = new Client(name);

        performTransaction(() -> {
            em.persist(client);
            return null;
        });
    }

    private static void addProduct(Scanner scanner) {

        System.out.println("Input product title:");
        String title = scanner.nextLine();
        System.out.println("Input product price:");
        Float price = Float.valueOf(scanner.nextLine());

        Product product = new Product(title, price);

        performTransaction(() -> {
            em.persist(product);
            return null;
        });
    }

    private static void fillDatabaseWithProducts() {
        List<Product> phones = Stream.of(
                new Product("Samsung Galaxy M52", 9999F),
                new Product("Vivo Y31 Premium", 6499F),
                new Product("Xiaomi Poco X3 Pro", 8999F),
                new Product("Realme 8 Pro", 6999F),
                new Product("OnePlus Nord N10", 7499F),
                new Product("Google Pixel 6 Pro", 32699F),
                new Product("ASUS ROG Phone 5 Pro", 49900F),
                new Product("Nokia 1100", 899F),
                new Product("Motorola E398", 1099F),
                new Product("Sony Ericsson K700i", 799F)
        ).collect(toList());

        for (Product product : phones) {
            performTransaction(() -> {
                em.persist(product);
                return null;
            });
        }

    }


    private static <T> T performTransaction(Callable<T> action) {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        try {
            T result = action.call();
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
