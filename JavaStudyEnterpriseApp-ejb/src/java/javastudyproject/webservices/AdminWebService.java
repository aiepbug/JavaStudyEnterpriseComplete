/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javastudyproject.webservices;

import java.util.List;
import javastudyproject.model.User;
import javastudyproject.service.UserOps;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author EladYarkoni
 */
@WebService()
@Stateless()
public class AdminWebService {

    @EJB UserOps userService;

    @WebMethod(operationName = "getAllUsers")
    public List<User> getAllUsers(@WebParam(name = "user") String user,@WebParam(name = "password") String password) {
        try {
            if (!(userService.authenticate(user, password).toString().equals("AdministratorUser")))
                return null;
        }
        catch (Exception ex)
        {
            return null;
        }
        return userService.getAllUsers();
    }


}
