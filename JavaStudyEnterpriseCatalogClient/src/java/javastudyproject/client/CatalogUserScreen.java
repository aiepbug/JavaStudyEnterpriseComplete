/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javastudyproject.model.Product;
import javastudyproject.model.Category;
import javastudyproject.service.ProductsOpsBean;
import javastudyproject.reporter.SystemReporter;

/**
 * the product management screen for administrator user
 * @author eyarkoni
 */
public class CatalogUserScreen  {

    private BufferedReader reader;

    public CatalogUserScreen() throws Exception {

        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Products Management\n-----------------------------------------------");
        System.out.println("1. Add new Product");
        System.out.println("2. Add new Category");
        System.out.print("Your choise: ");

        try {
           int choise=  Integer.parseInt(reader.readLine());
           switch (choise)
           {
               case 1:
               {
                    AddNewProduct();
               }break;
               case 2:
               {
                    AddNewCategory();
               }break;
           }
           new CatalogUserScreen();

        } 
        catch (Exception e)
        {
            SystemReporter.report("Failed to perform last action, error: " + e.getMessage());
            new CatalogUserScreen();
        }
    }

    /**
     * adding a new product
     * @throws IOException
     */
    private void AddNewProduct() throws IOException, Exception {

        List<Category> categories = Main.categoryService.getAllCategories();

        System.out.print("name: ");
        String name = reader.readLine();
        System.out.print("serial number: ");
        String serial = reader.readLine();
        System.out.print("price: ");
        double price = Double.parseDouble(reader.readLine());
        System.out.print("quantity: ");
        int quantity = Integer.parseInt(reader.readLine());
        System.out.println("Select category from list -> ");
        if ( categories.isEmpty() )
        {
            SystemReporter.report("There is no categories yet, please create one\n", true);
        }
        for (int i=0; i< categories.size(); i++)
            System.out.println(i+". "+categories.get(i).getName());
        System.out.print("Select category Number:  ");
        int catnum = Integer.parseInt(reader.readLine());
        Category category = categories.get(catnum);
        
        Product newproduct = new Product(name, category, serial, price, quantity);
        
        // --- Creating the new product by using the queue ejb---
        Main.catalogService.sendNewProductMessage(newproduct);
    }
    /**
     * adding a new category
     */
    private void AddNewCategory() throws Exception {
        System.out.print("Category name: ");
        String name = reader.readLine();
        Category newcategory = new Category(name);

        // --- Creating the new category by using the queue ejb---
        Main.catalogService.sendNewCategoryMessage(newcategory);
    }
}
