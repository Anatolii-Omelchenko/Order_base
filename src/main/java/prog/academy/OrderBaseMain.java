package prog.academy;

import java.util.Scanner;

public class OrderBaseMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DaraBaseManager.fillDatabaseWithProducts();
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
                    DaraBaseManager.makeOrder();
                } else if (choose.equals("2")) {
                    DaraBaseManager.viewAllClients();
                } else if (choose.equals("3")) {
                    DaraBaseManager.viewAllProducts();
                } else if (choose.equals("4")) {
                    DaraBaseManager.viewAllOrders();
                } else if (choose.equals("5")) {
                    DaraBaseManager.addClient();
                } else if (choose.equals("6")) {
                    DaraBaseManager.addProduct();
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            DaraBaseManager.closeDataBase();
        }

    }


}
