/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.webservices;

import java.util.List;
import javastudyproject.model.Order;
import javastudyproject.model.Product;
import javastudyproject.service.OrderOps;
import javastudyproject.service.ProductsOps;
import javastudyproject.service.UserOps;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author EladYarkoni
 */
@WebService(
       name="UsersWS",
       serviceName="UsersWebService",
       targetNamespace="http://javaee6.com/jaxws/sample")
@Stateless
public class UsersWebService {

    @EJB ProductsOps productService;
    @EJB UserOps userService;
    @EJB OrderOps orderService;

    @WebMethod(operationName = "getProductList")
    public List<Product> getProductList(@WebParam(name = "user") String user,@WebParam(name = "password") String password) {
        try {
            userService.authenticate(user, password);
        }
        catch (Exception ex)
        {
            return null;
        }
        return productService.getAllProducts();
    }

}
