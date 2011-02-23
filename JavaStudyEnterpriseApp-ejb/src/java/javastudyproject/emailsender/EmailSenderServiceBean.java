package javastudyproject.emailsender;

import java.util.ArrayList;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javastudyproject.model.Order;
import javastudyproject.model.Order.StateType;
import javastudyproject.model.Product;
import javastudyproject.model.User;
import javastudyproject.reporter.SystemReporter;
import javastudyproject.service.UserOps;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless
public class EmailSenderServiceBean implements EmailSenderService {

    @EJB
    private UserOps userService;
    /**
     * Please configure the email session using this instructions:
     * http://javaeenotes.blogspot.com/2010/04/using-javamail-api-with-glassfish-and.html
     * @param toAddress
     * @param subject
     * @param body
     * @throws Exception
     */
   public void sendMail(String toAddress, String subject, String body) throws Exception
   {

        String fromAddress;
        try
        {
            fromAddress = userService.GetEmailSourceAddress();
        }
        catch (Exception e)
        {
            System.out.println(e + " configure default user");
            fromAddress = "admin@javaproj.com";
        }

        Session session = setupConnection();

        try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(fromAddress));
                msg.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(toAddress, false));
                msg.setSubject(subject);
                MimeBodyPart mbp = new MimeBodyPart();
                mbp.setText(body);
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp);
                msg.setContent(mp);

                //Send the message
                Transport.send(msg);
        }
        catch (MessagingException e)
        {
            SystemReporter.report("Failed to send email with this error: " + e.getMessage());
        }
    }

   /**
    * Setup email session using app server email configuration
    * @return
    * @throws NamingException
    */
   private static Session setupConnection() throws NamingException
    {
         InitialContext initial = new InitialContext();
         Session session =
         (Session) initial.lookup(
         "mail/OrderSystemMail");
        return session;
    }

   /**
    * User order email update
    * @param user
    * @param orders
    * @throws Exception
    */
   public void sendOrderUpdateToUser(User user, ArrayList<Order> orders) throws Exception
   {
       ArrayList<Order> releventOrders = new ArrayList<Order>();
       for (Order order: orders)
       {
           if (order.getState() != StateType.Finished)
           {
               releventOrders.add(order);
           }
       }
       if (releventOrders == null)
           return;
       String subject = "Orders update email";
       String body = "Dear " + user.getFirstName() + " this is your order info:\n";
       for (Order releventOrder : releventOrders)
       {
           body = body + "\nOrder number: "+ releventOrder.getRunId() + " ; Order state: "
                   + releventOrder.getState()+ " ; Delivery date: " + 
                   (releventOrder.getDeliveryDate() == null ? "Self pickup" : releventOrder.getDeliveryDate());
       }
       sendMail(user.getEmail(), subject, body);
   }

   /**
    * Administrator statistics update
    * @param toAddress
    * @param mostPopular
    * @param leastPopular
    * @throws Exception
    */
   public void sendAdminStatistics(String toAddress, Product mostPopular, Product leastPopular) throws Exception
   {
       String subject = "Orders statistics";
       String body = "The most popular product is: "+ mostPopular.getName()+ 
                " with amount of: "+ mostPopular.getQuantity()+ ", " +
                "The least popular product is: "+leastPopular.getName()+
                " with amount of: " + leastPopular.getQuantity();
       sendMail(toAddress, subject, body);
   }
}
