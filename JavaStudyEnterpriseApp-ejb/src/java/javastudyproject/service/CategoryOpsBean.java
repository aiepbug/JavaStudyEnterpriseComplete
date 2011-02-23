package javastudyproject.service;

import java.util.List;
import javastudyproject.model.Category;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The class handling category operations
 * @author alon
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER) //Default
@TransactionAttribute(TransactionAttributeType.REQUIRED) //Default
public class CategoryOpsBean implements CategoryOps{

     @PersistenceContext(unitName="JavaStudyProject")
    private EntityManager em;

    public CategoryOpsBean() {
       
    }

    
    public List<Category> getAllCategories()
    {
       Query query = em.createQuery("SELECT o FROM Category o");
       return (List<Category>) query.getResultList();
    }
}
