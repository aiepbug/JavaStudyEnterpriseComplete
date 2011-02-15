/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.service;

import javastudyproject.model.Category;
import javastudyproject.model.Product;
import javax.ejb.Remote;

/**
 *
 * @author EladYarkoni
 */
@Remote
public interface CatalogMessageService {
    public void sendNewCategoryMessage(Category category) throws Exception;
    public void sendNewProductMessage(Product product) throws Exception;
}
