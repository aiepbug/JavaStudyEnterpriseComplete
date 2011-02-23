package javastudyproject.schedulerservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javastudyproject.emailsender.EmailSenderService;
import javastudyproject.model.Order;
import javastudyproject.model.Product;
import javastudyproject.model.User;
import javastudyproject.service.OrderOps;
import javastudyproject.service.ProductsOps;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class SchedulerTimerServiceBean implements SchedulerTimerService {

    @EJB
    private OrderOps orderService;
    @EJB
    private ProductsOps productService;
    @EJB
    private EmailSenderService emailService;

    //@Schedule(dayOfWeek = "*", hour = "*", minute = "*/1", timezone = "GMT+2") // for debugging
    @Schedule(minute = "00", hour = "08", dayOfWeek="*", timezone="GMT+2")
    public void sendOrderUpdate()
    {
        HashMap<User, ArrayList<Order>> orderUsers = new HashMap<User, ArrayList<Order>>();
        orderUsers = orderService.GetAllOrdersForAllUser();

        Set set = orderUsers.entrySet();

        Iterator i = set.iterator();

        while(i.hasNext())
        {
            Map.Entry<User, ArrayList<Order>> me = (Map.Entry<User, ArrayList<Order>>)i.next();
           try
             {
                 emailService.sendOrderUpdateToUser(me.getKey(), me.getValue());
             }
             catch(Exception e)
             {
                 System.out.println("Failed to send email to user: "
                         + me.getKey().getEmail() + ", reason: " + e.getMessage() );
             }
        }
    }

    //@Schedule(dayOfWeek = "*", hour = "*", minute = "*/3", timezone = "GMT+2")//For debug
    @Schedule(minute = "59", hour = "23", dayOfWeek="*", timezone="GMT+2")
    private void sendProductPopularityStatistics()
    {
        String toAddress = "alonpis@gmail.com";
        try
        {
            Product mostPopular = productService.getMostSaleableProduct(true);
            Product leastPopular = productService.getLeastSaleableProductOnLast24Hours();
            emailService.sendAdminStatistics(toAddress, mostPopular, leastPopular);

        }
        catch (Exception e)
        {
            System.out.println("Failed to send statistics email to: " +
                    toAddress + ", Reason: " + e.getMessage());
        }
    }
 
}
