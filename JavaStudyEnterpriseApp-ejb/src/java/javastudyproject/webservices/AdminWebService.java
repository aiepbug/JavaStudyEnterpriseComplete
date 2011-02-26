/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.webservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javastudyproject.model.Category;
import javastudyproject.model.Order;
import javastudyproject.model.Product;
import javastudyproject.model.User;
import javastudyproject.service.OrderOps;
import javastudyproject.service.ProductsOps;
import javastudyproject.service.ProductsOpsBean;
import javastudyproject.service.UserOps;
import javastudyproject.service.UserOpsBean.UserCriteria;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.swing.text.Keymap;

/**
 *
 * @author EladYarkoni
 */
@WebService()
@Stateless()
public class AdminWebService {

    @EJB UserOps userService;
    @EJB OrderOps orderService;
    @EJB ProductsOps productService;

    @WebMethod(operationName = "getAllUsers")
    public List<User> getAllUsers(@WebParam(name = "user") String user,@WebParam(name = "password") String password) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        return userService.getAllUsers();
    }

    @WebMethod(operationName = "getUserInfo")
    public User getUserInfo(@WebParam(name = "user") String user,@WebParam(name = "password") String password, @WebParam(name = "infoUserName") String infoUserName) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        User userContainer = new User();
        userContainer.setUserName(infoUserName);
        try {
            List<User> users = userService.getUserByGivenCriteria(UserCriteria.UserName, userContainer);
            for (User userInList : users)
            {
                return userInList;
            }
            return null;
        }
        catch (Exception ex){ return null;}
    }

    @WebMethod(operationName = "getAllOrders")
    public List<Order> getAllOrders(@WebParam(name = "user") String user,@WebParam(name = "password") String password) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        return orderService.getAllOrders();
    }

    @WebMethod(operationName = "getOrdersByStatus")
    public List<Order> getOrdersByStatus(@WebParam(name = "user") String user,@WebParam(name = "password") String password,@WebParam(name = "state") Order.StateType state) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        List<Order> orders = orderService.getAllOrders();
        ArrayList<Order> retOrders = new ArrayList<Order>();
        for (Order order : orders)
        {
            if (order.getState() == state)
                    retOrders.add(order);
        }
        return retOrders;
    }

    @WebMethod(operationName = "getAllProducts")
    public List<Product> getAllProducts(@WebParam(name = "user") String user,@WebParam(name = "password") String password) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        return productService.getAllProducts();
    }

    @WebMethod(operationName = "getAllProductsSortedByPrice")
    public List<Product> getAllProductsSortedByPrice(@WebParam(name = "user") String user,@WebParam(name = "password") String password) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        try {
            return productService.getSortedProductsByPrice();
        }
        catch (Exception ex) {
            return null;
        }
    }

    @WebMethod(operationName = "getAllProductsByPrice")
    public List<Product> getAllProductsByPrice(@WebParam(name = "user") String user,@WebParam(name = "password") String password,@WebParam(name = "largerOrSmaller") ProductsOpsBean.LergerSmaller largerOrSmaller,@WebParam(name = "price") double  price) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        try {
            return productService.getProductsByPrice(largerOrSmaller, price);
        }
        catch (Exception ex) {
            return null;
        }
    }

    @WebMethod(operationName = "getAllProductsByCategory")
    public List<Product> getAllProductsByCategory(@WebParam(name = "user") String user,@WebParam(name = "password") String password,@WebParam(name = "category") Category category) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        try {
            Product prodContainer = new Product();
            prodContainer.setCategory(category);
            return productService.getProductsByGivenCriteria(ProductsOpsBean.ProductCriteria.category, prodContainer);
        }
        catch (Exception ex) {
            return null;
        }
    }

    @WebMethod(operationName = "getAllCategories")
    public List<Category> getAllCategories(@WebParam(name = "user") String user,@WebParam(name = "password") String password) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        return productService.getAllCategories();
    }

    @WebMethod(operationName = "getProductNumOnEachCategory")
    public HashMap<String,Number> getProductNumOnEachCategory(@WebParam(name = "user") String user,@WebParam(name = "password") String password) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        HashMap<String,Number> retHash = new HashMap<String,Number>();
        List<Category> categories = productService.getAllCategories();
        for (Category cat : categories)
        {
            Product prodContainer = new Product();
            prodContainer.setCategory(cat);
            try {
                retHash.put(cat.getName(), productService.getProductsByGivenCriteria(ProductsOpsBean.ProductCriteria.category, prodContainer).size());
            } catch (Exception e) {
                return null;
            }
        }
        return retHash;
    }

    @WebMethod(operationName = "getProductOrderUserSum")
    public Number getProductOrderUserSum(@WebParam(name = "user") String user,@WebParam(name = "password") String password,@WebParam(name = "product") Product product ) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        try {
            List<Order> orders = orderService.getOrdersUserNamesByPurchasedProduct(product);
            return orders.size();
        }
        catch (Exception ex) {
            return null;
        }
    }

    @WebMethod(operationName = "getBestSellingProduct")
    public Product getBestSellingProduct(@WebParam(name = "user") String user,@WebParam(name = "password") String password) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }

        try {
            return productService.getMostSaleableProduct();
        } catch (Exception ex) {
            return null;
        }
    }


}
