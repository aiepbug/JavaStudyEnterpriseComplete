/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javastudyproject.model.*;
import javastudyproject.service.OrderOpsBean;
import javastudyproject.reporter.SystemReporter;
import javastudyproject.service.OrderOps;
import javax.ejb.EJB;
import javax.persistence.Query;

/**
 * this is the order management screen for administrator user
 * @author eyarkoni
 */
public class OrdersScreen  {

    private BufferedReader reader;

    /**
     * this function do the three operations from the orderScreen menu
     * @throws Exception
     */
    public OrdersScreen() throws Exception {

        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Orders  menu\n-----------------------------------------------");
        System.out.println("1. Update order status");
        System.out.println("2. Delete order");
        System.out.println("3. Back to main menu\n");

        System.out.print("Your choise: ");

        try {
           int choise=  Integer.parseInt(reader.readLine());
            switch (choise)
            {
                case 1: {
                    updateOrderStatus();
                }break;
                 case 2: {
                   deleteOrder();
                }break;
                 case 3: {
                    new AdministratorScreen();
                }break;
            }
        }
 catch (IOException e)
          {
                SystemReporter.report("Failed to update user details, error: " + e.getMessage());
                new OrdersScreen();
          }        catch (Exception e)
        {
            SystemReporter.report("Failed to perform last action, error: " + e.getMessage());
            System.out.println("1. Try again");
            System.out.println("2. Back to main menu\n");

            System.out.println("Ente your choise: ");

            int choise=  Integer.parseInt(reader.readLine());
            switch (choise)
            {
                case 1:
                    new OrdersScreen();
                    break;
                case 2:
                    new AdministratorScreen();
                    break;
            }
        }
    }

    /**
     * update an order
     * @throws Exception
     */
    private void updateOrderStatus() throws Exception
    {
        System.out.print("Choose order: ");
        List<Order> orders = Main.orderService.getAllOrders();


        for (int i = 0; i < orders.size(); i++)
        {
           SystemReporter.report( i+1 + ". Run ID - '"
                   + orders.get(i).getRunId() + "' of user - "
                   + orders.get(i).getUser().getUserName());
        }
        int id = Integer.parseInt(reader.readLine());
        System.out.print("Enter new state (1. New 2. Pending 3. Ready 4. InProgress 5. Finished): ");
        int stateChosen = Integer.parseInt(reader.readLine());
        Order.StateType state = Order.StateType.New;
        switch (stateChosen) {
            case 1: {state = Order.StateType.New;}break;
            case 2: {state = Order.StateType.Pending;}break;
            case 3: {state = Order.StateType.Ready;}break;
            case 4: {state = Order.StateType.InProgress;}break;
            case 5: {state = Order.StateType.Finished;}break;
        }
        Main.orderService.updateOrderStatus(id, state);
    }

    /**
     * delete order
     * @throws Exception
     */
    private void deleteOrder() throws Exception
    {
        List<Order> orders = Main.orderService.getAllOrders();

        for (int i = 0; i < orders.size(); i++)
        {
           SystemReporter.report( i + 1 + ". Run ID: "
                   + orders.get(i).getRunId() + " of user: "
                   + orders.get(i).getUser().getUserName());
        }
        System.out.print("Choose order: ");
        int id = Integer.parseInt(reader.readLine());
        Main.orderService.deleteOrder(id);
    }
}
