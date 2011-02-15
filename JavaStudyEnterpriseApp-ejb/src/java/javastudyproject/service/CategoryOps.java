/*
 *Categoty interface
 */

package javastudyproject.service;

import java.util.List;
import javastudyproject.model.Category;
import javax.ejb.Remote;

/**
 *
 * @author alon
 */
@Remote
public interface CategoryOps {
    public List<Category> getAllCategories();
}
