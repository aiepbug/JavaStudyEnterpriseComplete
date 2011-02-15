/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.client;

import javastudyproject.service.ProductsOpsBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javastudyproject.model.*;
import javastudyproject.reporter.SystemReporter;
import javastudyproject.service.CategoryOps;
import javastudyproject.service.OrderOps;
import javastudyproject.service.ProductsOps;
import javastudyproject.service.UserOps;
import javastudyproject.service.UserOpsBean.UserCriteria;
import javax.ejb.EJB;

/**
 * Contains all administrator reports in the system
 * @author eyarkoni
 */
public class ReportsScreen {

    private BufferedReader reader;

    public ReportsScreen() throws IOException, Exception {

        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nReports menu\n-----------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("1. Print all users");
        System.out.println("2. Print user information");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("3. Print all orders");
        System.out.println("4. Print orders by state");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("5. Print all products");
        System.out.println("6. Print products larger/smaller than specific price");
        System.out.println("7. Print  products by category");
        System.out.println("8. Print product orders from history");
        System.out.println("9. Print best selling product");
        System.out.println("10. Print products by price (decreasing sort)");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("11. Print all categories");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("12. Back to main menu\n");
        System.out.print("Your choise: ");

        try {
           int choise=  Integer.parseInt(reader.readLine());
            switch (choise)
            {
                case 1: { 
                    List<User> users = Main.userService.getAllUsers();
                    for (User user : users)
                    {
                        printUserInfoImpl(user);
                    }
                }break;
                case 2: {
                    System.out.println("Enter username: ");
                    String userName = reader.readLine();
                    User userContainer = new User();
                    userContainer.setUserName(userName);
                    List<User> users = Main.userService.getUserByGivenCriteria(UserCriteria.UserName, userContainer);
                    for (User user : users)
                    {
                        printUserInfoImpl(user);
                    }
                }break;
                case 3: {
                    List<Order> orders = Main.orderService.getAllOrders();
                    for (Order order : orders)
                        printOrderImpl(order);
                }break;
                case 4: {
                    System.out.print("Select Status(1. New 2. Pending 3. Ready 4. InProgress 5. Finished): ");
                    int stateChosen = Integer.parseInt(reader.readLine());
                    Order.StateType state = null;
                    switch (stateChosen) {
                        case 1: {state = Order.StateType.New;}break;
                        case 2: {state = Order.StateType.Pending;}break;
                        case 3: {state = Order.StateType.Ready;}break;
                        case 4: {state = Order.StateType.InProgress;}break;
                        case 5: {state = Order.StateType.Finished;}break;
                    }

                    List<Order> orders = Main.orderService.getAllOrders();
                    for (Order order : orders)
                    {
                        if (order.getState() == state)
                                printOrderImpl(order);
                    }

                }break;
                case 5: {
                    List<Product> products = Main.productService.getAllProducts();
                    for (Product prod : products)
                        ProductManagementScreen.printProductInfoImpl(prod);
                }break;
                case 6: {
                    System.out.print("1. Larger 2. Smaller : ");
                    int stateChosen = Integer.parseInt(reader.readLine());
                    System.out.print("\nThan number : ");
                    double number = Double.parseDouble(reader.readLine());

                    switch (stateChosen)
                    {
                        case 1:
                            List<Product> products = Main.productService.getProductsByPrice(ProductsOpsBean.LergerSmaller.Larger, number);
                            for (Product prod : products)
                                ProductManagementScreen.printProductInfoImpl(prod);
                            break;
                        case 2:
                            products = Main.productService.getProductsByPrice(ProductsOpsBean.LergerSmaller.Smaller, number);
                            for (Product prod : products)
                                ProductManagementScreen.printProductInfoImpl(prod);
                            break;
                    }

                }break;
                case 7: {
                    List<Category> categories = Main.categoryService.getAllCategories();
                    System.out.print("All categories: \n");
                    for (int i = 0; i < categories.size(); i++)
                    {
                        System.out.print(i+ 1 + ". " + categories.get(i).getName() + "\n");
                    }
                    System.out.print("Choose category index: ");
                    int catIndex = Integer.parseInt(reader.readLine());
                    Category categoryContainer = categories.get(catIndex-1);
                    Product prodContainer = new Product();
                    prodContainer.setCategory(categoryContainer);
                    List<Product> products = Main.productService.getProductsByGivenCriteria(ProductsOpsBean.ProductCriteria.category, prodContainer);
                    for (Product prod : products)
                           ProductManagementScreen.printProductInfoImpl(prod);
                }break;
                case 8: {
                    List<Product> products = Main.productService.getAllProducts();
                    if (products.isEmpty())
                    {
                        SystemReporter.report("There are no products in the system", true);
                    }
                    System.out.print("All products: \n");
                    for (int i = 0; i < products.size(); i++)
                    {
                        System.out.print(i+ 1 + ". " + products.get(i).getName() + "\n");
                    }
                    System.out.print("Type product index: ");
                    int productIndex = Integer.parseInt(reader.readLine());
                    List<Order> orders = Main.orderService.getOrdersUserNamesByPurchasedProduct(products.get(productIndex -1));
                    for (Order order : orders)
                        printOrderImpl(order);
                }break;
                case 9: {
                    Product prod = Main.productService.getMostSaleableProduct();
                    SystemReporter.report("The most saleable product is: " + prod.getName());
                }break;
                case 10: {
                    List<Product> products = Main.productService.getSortedProductsByPrice();
                    for (Product prod : products)
                        ProductManagementScreen.printProductInfoImpl(prod);
                }break;
                case 11: {
                    List <Category> categories = Main.productService.getAllCategories();
                    for (Category cat : categories)
                        SystemReporter.report("- Category: "+cat.getName()+"\n");
                }break;
                case 12: {
                    new AdministratorScreen();
                }break;
            }
            System.out.println("\n1. View another report");
            System.out.println("2. Back to main menu\n");

            System.out.println("Ente your choise: ");

            int newChoise =  Integer.parseInt(reader.readLine());
            switch (newChoise)
            {
                case 1:
                    new ReportsScreen();
                    break;
                case 2:
                    new AdministratorScreen();
                    break;
            }

        } 
        catch (Exception e)
        {
            System.out.println("Failed to show the report, error: " + e.getMessage());
            System.out.println("1. Try again");
            System.out.println("2. Back to main menu\n");

            System.out.println("Ente your choise: ");

            int choise =  Integer.parseInt(reader.readLine());
            switch (choise)
            {
                case 1:
                    new ReportsScreen();
                    break;
                case 2:
                    new AdministratorScreen();
                    break;
            }
        }
    }

public static void printUserInfoImpl(User user) throws Exception
{
          SystemReporter.report("User info:", new String[] {
                "User name: " + user.getUserName(),
                "User id: " + user.getId(),
                "User first name: " + user.getFirstName(),
                "User last name: " + user.getLastName(),
                "User email: " + user.getEmail(),
                "User age: " + user.getAge(),
                "Create date: " + user.getCreateDate(),
          });
          SystemReporter.report("--------------------------------");
}

public static void printOrderImpl(Order order) throws Exception
{
    SystemReporter.report("Order info for user " + order.getUser().getUserName() + ": ", new String[] {
        "Order ID:\t" + order.getRunId(),
        "Order date:\t" + order.getOrderDate(),
        "Order deliviry date:\t" + order.getDeliveryDate(),
        "Order deliviry type:\t" + order.getDeliveryType().toString(),
        "Order state:\t" + order.getState().toString()
    });
}


    
}
