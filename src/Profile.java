// 11.0

import java.io.Serializable;
import java.util.Objects;

public class Profile implements User, Serializable {
    protected String title;
    protected String phoneNumber;
    protected String username = "";
    String accountNumber = "";
    protected String password;
    String occupation;
    String CNIC;
    String email;
    String address;

    //11.1
    public Profile(String title, String username,
                   String phoneNumber, String password,
                   String occupation, String CNIC,
                   String email, String address) {
        this.title = title;

        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.occupation = occupation;
        this.CNIC = CNIC;
        this.email = email;
        this.address = address;
    }

    //11.2
    public Profile() {

    }

    //11.3
    public Profile(String accountNo) {
        this.accountNumber = accountNo;
    }

    public Profile(String username, String password) {
        this.username = username;
        this.password = password;
    }
//11.4
    public void resetPassword() {
        this.password = "123456";
    }
//11.5
    public void setEmail(String email) {
        this.email = email;
    }
//11.6
    public String getTitle() {
        return title;
    }
//11.7
    public String getUsername() {
        return username;
    }
//11.8
    @Override
    //Changes password if old pasword is entered correctly
    public boolean changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(password)) {
            password = newPassword;
            return true;
        } else {
            return false;
        }

    }


    @Override
    //Changes phone number if Password is correctly entered
    public boolean changePhoneNumber(String newPhoneNumBer, String password) {
        if (password.equals(this.password)) {
            this.phoneNumber = newPhoneNumBer;
            return true;
        } else
            return false;


    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Profile profile = (Profile) o;
        if (accountNumber.equals("")) {
            return username.equals(profile.username);
        } else {
            return accountNumber.equals(profile.accountNumber);
        }
    }
}
