package javastudyproject.mdb;

import java.util.List;
import javastudyproject.model.Category;
import javastudyproject.service.ProductsOps;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;


@MessageDriven(mappedName = "orderCatalogQueue", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
    })
public class MessageDrivenBean implements MessageListener {

    @EJB ProductsOps productService;

    public MessageDrivenBean() {
    }

    @Override
    public void onMessage(Message message)
    {
        try {
            if (message.propertyExists("create_category"))
            {
                // create new category
                productService.addNewCategory(message.getStringProperty("categoryName"));
            }
            else if (message.propertyExists("create_product"))
            {
                // craete new product
                Category catContainer = null;
                List<Category> categories = productService.getAllCategories();
                for (Category cat : categories)
                {
                    if (cat.getRunId() == Integer.parseInt(message.getStringProperty("productCategoryId")))
                    {
                        catContainer = cat;
                        break;
                    }
                }

                productService.addNewProduct(message.getStringProperty("productName"),
                                             message.getStringProperty("productSerial"),
                                             Double.parseDouble(message.getStringProperty("productPrice")),
                                             Integer.parseInt(message.getStringProperty("productQuantity")),
                                             catContainer);
            }
        }
        catch (Exception ee) {}
    }

    
}
