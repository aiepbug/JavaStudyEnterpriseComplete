package javastudyproject.service;

import java.util.List;
import javastudyproject.model.Order;
import javastudyproject.model.Product;
import javax.ejb.Remote;

/**
 * Orders interface
 * @author alon
 */
@Remote
public interface OrderOps {

    public void updateOrderStatus(int orderRunId, Order.StateType status) throws Exception;
    public void deleteOrder(int orderRunId) throws Exception;
    public void printAllOrders() throws Exception;
    public void printOrdersByState(Order.StateType state) throws Exception;
    public List<Order> getOrdersUserNamesByPurchasedProduct(Product productToFind) throws Exception;
    public List<Order> getAllOrders();
    public List<Order> getAllOrdersByUserID(int userID);
    public void addNewOrder(Order order) throws Exception;

}
