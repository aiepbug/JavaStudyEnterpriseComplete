package javastudyproject.emailsender;

import java.util.ArrayList;
import javastudyproject.model.Order;
import javastudyproject.model.Product;
import javastudyproject.model.User;
import javax.ejb.Local;

@Local
public interface EmailSenderService {

    public void sendOrderUpdateToUser(User user, ArrayList<Order> orders) throws Exception;
    public void sendAdminStatistics(String toAddress, Product mostPopular, Product leastPopular) throws Exception;
    
}
