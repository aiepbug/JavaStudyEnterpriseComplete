package javastudyproject.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;


@MessageDriven(mappedName = "orderCatalogQueue", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
    })
public class MessageDrivenBean implements MessageListener {
    
    public MessageDrivenBean() {
    }

    @Override
    public void onMessage(Message message)
    {
        System.out.println("new message has arrived");

    }

    
}
