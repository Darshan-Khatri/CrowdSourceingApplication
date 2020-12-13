/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author darsh
 */

//Roland orea
@Named(value = "createAccount")
@ManagedBean
@RequestScoped
public class CreateAccount {
    private String Id;
    private String Password;
    private String Name;
    private String Acc_type;
    public CreateAccount() {}
    public String getId() {
        return this.Id;
    }    
    public String getPassword() {
        return this.Password;
    }
    public String getName() {
        return this.Name;
    }
    public String getAcc_type() {
        return this.Acc_type;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public void setId(String Id) {
        this.Id = Id;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }
    public void setAcc_type(String Acc_type) {
        this.Acc_type = Acc_type;
    }
    public String createAccount() {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return ("Error occured in createAccount");
        }
        DataBaseService Data = new DataBaseService();
        return Data.insertUserAccount(Id, Password, Name);
    }

}
