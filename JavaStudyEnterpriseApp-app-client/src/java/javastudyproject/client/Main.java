/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.client;

import javastudyproject.service.CategoryOps;
import javastudyproject.service.OrderOps;
import javastudyproject.service.ProductsOps;
import javastudyproject.service.UserOps;
import javax.ejb.EJB;



/**
 *
 * @author EladYarkoni
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    @EJB
    static UserOps userService;
    @EJB
    static OrderOps orderService;
    @EJB
    static CategoryOps categoryService;
    @EJB
    static ProductsOps productService;

    public static void main(String[] args) {

        try
        {
            userService.createAdminUserIfNeeded();
             new LoginScreen();
        }
        catch(Exception e)
        {
            System.out.println("Catched an exeption: " + e.getMessage());
        }
    }


}
