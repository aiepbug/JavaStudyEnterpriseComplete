/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.client;

import javastudyproject.service.CatalogMessageService;
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
    static CatalogMessageService catalogService;
    @EJB
    static CategoryOps categoryService;

    public static void main(String[] args) {

        try
        {
             new CatalogUserScreen();
        }
        catch(Exception e)
        {
            System.out.println("Catched an exeption: " + e.getMessage());
        }
    }


}
