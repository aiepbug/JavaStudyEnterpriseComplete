package javastudyproject.schedulerservice;

import java.util.List;
import javastudyproject.emailsender.EmailSenderService;
import javastudyproject.model.Order;
import javastudyproject.model.Product;
import javastudyproject.model.User;
import javastudyproject.service.OrderOps;
import javastudyproject.service.ProductsOps;
import javastudyproject.service.UserOps;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

@Stateless
public class SchedulerTimerServiceBean implements SchedulerTimerService {

    @EJB OrderOps orderService;
    @EJB UserOps userService;
    @EJB ProductsOps productService;
    @EJB EmailSenderService emailService;

    //@Schedule(minute = "00", hour = "08", dayOfWeek="*", timezone="GMT+2")
    @Schedule(dayOfWeek = "*", hour = "*", minute = "*/1", timezone = "GMT+2") //For debug
    private void sendOrderUpdate()
    {
        List<User> users  = userService.getAllUsers();

        for (User user: users)
        {
             List<Order> currOrders = null;
             List<Order> orders = orderService.getAllOrders();
             for (Order order: orders)
             {
                 if(order.getUser().getEmail().equals(user.getEmail()))
                 {
                    currOrders.add(order);
                 }
            }
             if (currOrders != null)
             {
                 try
                 {
                    emailService.sendOrderUpdateToUser(user, currOrders);
                 }
                 catch(Exception e)
                 {
                     System.out.println("Failed to send email to user: "
                             + user.getEmail() + ", reason: " + e.getMessage() );
                 }
             }
        }
    }

  //@Schedule(minute = "59", hour = "23", dayOfWeek="*", timezone="GMT+2")
    @Schedule(dayOfWeek = "*", hour = "*", minute = "*/3", timezone = "GMT+2")//For debug
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
