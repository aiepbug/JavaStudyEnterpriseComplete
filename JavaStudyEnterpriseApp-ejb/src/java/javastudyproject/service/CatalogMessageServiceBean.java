package javastudyproject.service;

import javastudyproject.model.Category;
import javastudyproject.model.Product;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Stateless
public class CatalogMessageServiceBean implements CatalogMessageService {

    
    @Resource(mappedName = "orderCatalogFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "orderCatalogQueue")
    private Queue queue;

    @Override
    public void sendNewCategoryMessage(Category category) throws Exception {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(queue);

        TextMessage message = session.createTextMessage();
        message.setObjectProperty("create_category", true);
        message.setObjectProperty("categoryName", category.getName());

        messageProducer.send(message);
    }

    @Override
    public void sendNewProductMessage(Product product) throws Exception
    {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer(queue);
        
        TextMessage message = session.createTextMessage();
        message.setObjectProperty("create_product", true);
        message.setObjectProperty("productName", product.getName());
        message.setObjectProperty("productSerial", product.getSerialNumber());
        message.setObjectProperty("productQuantity", product.getQuantity());
        message.setObjectProperty("productCategoryId", product.getCategory().getRunId());
        message.setObjectProperty("productPrice", product.getPrice());

        messageProducer.send(message);
    }
}
