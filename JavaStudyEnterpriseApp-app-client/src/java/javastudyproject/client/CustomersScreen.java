/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javastudyproject.reporter.SystemReporter;
import javastudyproject.model.User;
import javastudyproject.service.UserOps;
import javastudyproject.service.UserOpsBean;
import javax.ejb.EJB;

/**
 * the customers management administrator screen
 * @author eyarkoni
 */
public class CustomersScreen {

    private BufferedReader reader;

    public CustomersScreen() throws Exception {

        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nCustomers  menu\n-----------------------------------------------");
        System.out.println("1. Add new user");
        System.out.println("2. Update existing user");
        System.out.println("3. Delete user");
        System.out.println("4. Print user info\n");
        System.out.println("5. Back to main menu\n");
        System.out.print("Your choise: ");

        try {
           int choise=  Integer.parseInt(reader.readLine());
            switch (choise)
            {
                case 1: { 
                    AddNewUser();
                }break;
                case 2: {
                    UpdateExistingUser();
                }break;
                case 3: {
                    System.out.print("Select user name to delete:  ");
                    String userID = reader.readLine();
                    Main.userService.deleteUser(userID);
                }break;
                case 4: {
                    System.out.print("Select user name:  ");
                    String username = reader.readLine();
                    User userContainer = new User();
                    userContainer.setUserName(username);
                    List<User> users = Main.userService.getUserByGivenCriteria(UserOpsBean.UserCriteria.UserName, userContainer);
                    for (User usr : users)
                    {
                            printUserInfoImpl(usr);
                    }
                }break;
                case 5: {
                    new AdministratorScreen();
                }break;
            }
            new CustomersScreen();
        }
        catch (Exception e)
        {
            SystemReporter.report("Failed to perform last action, error: " + e.getMessage());
            System.out.println("1. Try again");
            System.out.println("2. Back to main menu\n");

            System.out.println("Ente your choise: ");

            int choise=  Integer.parseInt(reader.readLine());
            switch (choise)
            {
                case 1:
                    new CustomersScreen();
                    break;
                case 2:
                    new AdministratorScreen();
                    break;
            }
        }
    }

    private void printUserInfoImpl(User user) throws Exception
    {
              SystemReporter.report("User info:", new String[] {
                    "User name: " + user.getUserName(),
                    "User id: " + user.getId(),
                    "User first name: " + user.getFirstName(),
                    "User last name: " + user.getLastName(),
                    "User email: " + user.getEmail(),
                    "User age: " + user.getAge(),
                    "Create date: " + user.getCreateDate(),
              });
              SystemReporter.report("--------------------------------");
    }

    
    /**
     * Adding new user by using the UserOps class, administrator can add any user type
     * @throws Exception
     */
    private void AddNewUser() throws Exception {

        try {
            System.out.print("user type (1. Administrator 2. ReadWrite): ");
            int userType = Integer.parseInt(reader.readLine());
            System.out.print("first name: ");
            String origName = reader.readLine();
            System.out.print("last name: ");
            String lastName = reader.readLine();
            System.out.print("user id: ");
            String id = reader.readLine();
            System.out.print("user name: ");
            String userName = reader.readLine();
            System.out.print("user password: ");
            String password = reader.readLine();
            System.out.print("user email: ");
            String email = reader.readLine();
            System.out.print("user age: ");
            String age = reader.readLine();
            switch (userType) {
                case 1: {
                    // adding administrator
                    Main.userService.addNewUser(UserOpsBean.UserType.Administrator, userName, id, origName, lastName, email, password, age);
                }break;
                case 2: {
                    //Adding ReadWriteUsers
                    Main.userService.addNewUser(UserOpsBean.UserType.ReadWriteUser, userName, id, origName, lastName, email, password, age);
                }break;
            }
        }
        catch (Exception e)
        {
            SystemReporter.report("Failed to add user, error: " + e.getMessage());
            System.out.println("1. Try again");
            System.out.println("2. Back to main menu\n");

            System.out.println("Ente your choise: ");

            int choise=  Integer.parseInt(reader.readLine());
            switch (choise)
            {
                case 1:
                    this.AddNewUser();
                    break;
                case 2:
                    new CustomersScreen();
                    break;
            }
        }
    }
/**
 * update exist user from user database by using the class UserOps
 * @throws Exception
 */
 private void UpdateExistingUser() throws Exception {

        try {
            System.out.print("Select user name to update: ");
            String  username = reader.readLine();
            SystemReporter.report("Select field to update ",
                new String[] {
                "1. User name",
                "2. First name",
                "3. Last name",
                "4. Email",
                "5. Password",
                "6. Age"});

            int fieldchoise = Integer.parseInt(reader.readLine());
            System.out.print("Enter new value");
            String newValue = reader.readLine();

            switch (fieldchoise)
            {
                case 1:
                {
                    Main.userService.updateUserDetailsByUserName(
                            UserOpsBean.UserCriteria.UserName,
                            username, new User().setUserName(newValue));
                }break;
                case 2:
                {
                    Main.userService.updateUserDetailsByUserName(
                            UserOpsBean.UserCriteria.FirstName,
                            username, new User().setFirstName(newValue));
                }break;
                case 3:
                {
                    Main.userService.updateUserDetailsByUserName(
                            UserOpsBean.UserCriteria.LastName,
                            username, new User().setLastName(newValue));
                }break;
                case 4:
                {
                    Main.userService.updateUserDetailsByUserName(
                            UserOpsBean.UserCriteria.Email,
                            username, new User().setEmail(newValue));
                }break;
                case 5:
                {
                    Main.userService.updateUserDetailsByUserName(
                            UserOpsBean.UserCriteria.Password,
                            username, new User().setPassword(newValue));
                }break;
                case 6:
                {
                    Main.userService.updateUserDetailsByUserName(
                            UserOpsBean.UserCriteria.Age,
                            username, new User().setAge(newValue));
                }break;
            }
        }
        catch (Exception e)
        {
            SystemReporter.report("Failed to update user details, error: " + e.getMessage());
            System.out.println("1. Try again");
            System.out.println("2. Back to main menu\n");

            System.out.println("Ente your choise: ");

            int choise=  Integer.parseInt(reader.readLine());
            switch (choise)
            {
                case 1:
                    this.UpdateExistingUser();
                    break;
                case 2:
                    new CustomersScreen();
                    break;
            }
        }
    }


}
