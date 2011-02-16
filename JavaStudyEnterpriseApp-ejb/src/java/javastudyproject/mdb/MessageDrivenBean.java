package javastudyproject.mdb;

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

    static ProductsOps productService;

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
            }
        }
        catch (Exception ee) {}
    }

    
}
