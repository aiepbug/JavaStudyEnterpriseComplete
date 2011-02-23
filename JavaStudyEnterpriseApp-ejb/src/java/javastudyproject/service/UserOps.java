/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.service;

import java.util.List;
import javastudyproject.model.User;
import javastudyproject.service.UserOpsBean.UserCriteria;
import javastudyproject.service.UserOpsBean.UserType;
import javax.ejb.Remote;

/**
 * User interface
 * @author alon
 */
@Remote
public interface UserOps {

    public User authenticate(String userName, String password) throws Exception;
    public void addNewUser(
            UserType type,
            String userName,
            String id,
            String firstName,
            String lastName,
            String email,
            String password,
            String age) throws Exception;
    public void addNewUser(
            UserType type,
            String userName,
            String id,
            String firstName,
            String lastName,
            String email,
            String password,
            String age,
            String ownningUserName,
            int readOnlyUserOrderId) throws Exception;
     public void updateUserDetailsByUserName(UserCriteria criteria, String userName, User userContainer) throws Exception;
     public List<User> getUserByGivenCriteria(UserCriteria criteria, User userContainer) throws Exception;
     public void printUserInfo(String userName) throws Exception;
     public void deleteUser(String userName) throws Exception;
     public List<User> getAllUsers();
     public void printAllUsers() throws Exception;
     public void createAdminUserIfNeeded() throws Exception;
     public String GetEmailSourceAddress() throws Exception;
     public void StoreEmailSourceAddress(String email);

}
