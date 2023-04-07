// 3.0

import java.io.Serializable;


public class AccountHolder extends Profile implements Serializable,User {

    // 3.1
    public AccountHolder(String accountNumber){
        this.accountNumber = accountNumber;
    }

    // 3.2
    public AccountHolder(String title,
                         String username,
                         String accountNumber,
                         String phoneNumber,
                         String password,
                         String occupation,
                         String CNIC,
                         String email,
                         String address) {
        super(title, username,
                phoneNumber,
                password,
                occupation,
                CNIC,
                email,
                address);
        this.accountNumber = accountNumber;

    }
    // 3.3
    public String getAccountNumber() {
        return accountNumber;
    }
}
