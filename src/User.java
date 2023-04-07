//13.0

public interface User {

    // 13.1
    public boolean changePassword(String oldPassword, String newPassword);

    // 13.2
    public boolean equals(Object o);

    // 13.3
    public boolean changePhoneNumber(String newPhoneNumBer, String password);

    // 13.4
    public String toString();

    // 13.5
    public void resetPassword();

}
