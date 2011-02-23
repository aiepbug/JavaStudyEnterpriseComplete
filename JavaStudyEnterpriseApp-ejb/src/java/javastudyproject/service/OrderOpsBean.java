package javastudyproject.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javastudyproject.model.Order;
import javastudyproject.model.Product;
import javastudyproject.model.User;
import javastudyproject.reporter.SystemReporter;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * This class providing orders management
 * @author alon
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER) //Default
@TransactionAttribute(TransactionAttributeType.REQUIRED) //Default
public class OrderOpsBean implements OrderOps{

    @PersistenceContext(unitName="JavaStudyProject")
    private EntityManager em;

    public void updateOrderStatus(int orderRunId, Order.StateType status) throws Exception
    {
        Order order = em.find(Order.class, orderRunId);
        if (order == null)
        {
            SystemReporter.report("Didin't find order with runid: " + orderRunId, true );
        }
        order.updateState(status);
        try
        {
            em.merge(order);
        }
        catch(Exception e)
        {
            SystemReporter.report(
                    "Catched exception when performed write to DB: " + e.getMessage(), true);
        }
    }
    
    public void deleteOrder(int orderRunId) throws Exception
    {
        Order order = em.find(Order.class, orderRunId);
        if (order == null)
        {
            SystemReporter.report("Didin't find order with runid: " + orderRunId, true );
        }
        try
        {
            em.remove(order);
        }
        catch(Exception e)
        {
            SystemReporter.report(
                    "Catched exception when performed write to DB: " + e.getMessage(), true);
        }
    }

    public void printAllOrders() throws Exception
    {
        List<Order> orders = getAllOrders();
        if (orders.isEmpty())
        {
            SystemReporter.report("There is no orders", true);
        }
       for (Order order: orders)
       {
           printOrderImpl(order);
       }
    }

    /**
     * Printing order by his state
     * @param state
     * @throws Exception
     */
    public void printOrdersByState(Order.StateType state) throws Exception
    {
        SystemReporter.report("Printing all orders with this state: " + state);
        Query query = em.createQuery("SELECT o FROM Order o where o.state = :STATE").setParameter("STATE", state);
        List<Order> orders = query.getResultList();
        if (orders.isEmpty())
        {
            SystemReporter.report("There is no orders with state: " + state, true);
        }
        for (Order order: orders)
        {
           printOrderImpl(order);
        }
    }

    /**
     * Get orders by Purchased Products
     * @param productToFind
     * @return
     * @throws Exception
     */
    public List<Order> getOrdersUserNamesByPurchasedProduct(Product productToFind) throws Exception
    {
        SystemReporter.report("Finding all user that purchased " + productToFind.getName() + "...");
        Query query = em.createQuery(
                "SELECT o FROM Order o inner join o.orderProducts p where p.name = '"
                + productToFind.getName()+ "'");
        List<Order> orders = (List<Order>)query.getResultList();
        if (orders.isEmpty())
        {
            SystemReporter.report("Nobody purchased product " + productToFind.getName());
            return null;
        }
        return orders;
    }

    /**
     * Print order helper
     * @param order
     * @throws Exception
     */
    private void printOrderImpl(Order order) throws Exception
    {
        SystemReporter.report("Order info for user " + order.getUser().getUserName() + ": ", new String[] {
            "Order ID:\t" + order.getRunId(),
            "Order date:\t" + order.getOrderDate(),
            "Order deliviry date:\t" + order.getDeliveryDate(),
            "Order deliviry type:\t" + order.getDeliveryType().toString(),
            "Order state:\t" + order.getState().toString()
        });
    }

    /**
     * Get all orders
     * @return
     */
    public List<Order> getAllOrders()
    {
       Query query = em.createQuery("SELECT o FROM Order o");
       return query.getResultList();
    }

    /**
     * Get order by generated id
     * user for the read only users
     * @param userID
     * @return
     */
    public List<Order> getAllOrdersByUserID(int userID)
    {
        Query query = em.createQuery("select o from Order o where o.id = " + userID);
        List<Order> orders = (List<Order> )query.getResultList();
        return orders;
    }

    /**
     * add new oder to DB
     * @param order
     * @throws Exception
     */
    public void addNewOrder(Order order) throws Exception
    {
        try
        {
            em.persist(order);
        }
        catch(EntityExistsException e)
        {
            SystemReporter.report("The specific order is already exists. EM exception: " + e.getMessage(), true);
        }
        catch(Exception ex)
        {
            SystemReporter.report(
                    "Catched exception when performed write to DB: " + ex.getMessage(), true);
        }
        SystemReporter.report("Added new order");
    }

    /**
     * This method used for the scheduler in order
     * to get all orders for each user and send the
     * by email
     * @return
     */
    public HashMap<User, ArrayList<Order>> GetAllOrdersForAllUser()
    {

        HashMap<User, ArrayList<Order>> productAmount =  new HashMap<User, ArrayList<Order>>();
        Query query = em.createQuery("SELECT u FROM User u");
        List<User> users  = query.getResultList();
        ArrayList<Order> currOrders = new ArrayList<Order>();
        for (User user: users)
        {
             
             List<Order> orders = getAllOrders();
             for (Order order: orders)
             {
                 if(order.getUser().getUserName().equals(user.getUserName()))
                 {
                    currOrders.add(order);
                 }
            }
            if (!currOrders.isEmpty())
            {
                productAmount.put(user, (ArrayList<Order>) currOrders.clone());
            }
            currOrders.clear();
        }
        return productAmount;
    }
}
