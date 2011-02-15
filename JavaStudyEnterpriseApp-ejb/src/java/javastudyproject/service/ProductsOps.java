/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.service;

import java.util.List;
import javastudyproject.model.Category;
import javastudyproject.model.Product;
import javastudyproject.service.ProductsOpsBean.LergerSmaller;
import javastudyproject.service.ProductsOpsBean.ProductCriteria;
import javax.ejb.Remote;

/**
 * Products interface
 * @author alon
 */
@Remote
public interface ProductsOps {

    public void updateProductByName(ProductCriteria criteria, String name, Product productContainer) throws Exception;
    public List<Product> getProductsByGivenCriteria(ProductCriteria criteria, Product productContainer) throws Exception;
    public void printProductInfo(String name) throws Exception;
    public void deleteProduct(String name) throws Exception;
    public void printProductInfoImpl(Product product) throws Exception;
    public List<Product> getProductsByPrice(LergerSmaller by, double price) throws Exception;
    public void printProductsByCategory(Category category) throws Exception;
    public Product getMostSaleableProduct() throws Exception;
    public List<Product> getSortedProductsByPrice() throws Exception;
    public void printAllCategories() throws Exception;
    public void addNewCategory(String name) throws Exception;
    public void addNewProduct(String name, String serialNum, double price, int quantity, Category category) throws Exception;
    public List<Product> getAllProducts();
    public void printAllProducts() throws Exception;
    public List<Category> getAllCategories();
}
