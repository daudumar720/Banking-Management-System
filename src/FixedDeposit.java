//6.0

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FixedDeposit extends Profile implements Serializable {


    static double interest;
    private LocalDate dateOfFixing;
    private LocalDate dateOfMaturity;
    private double amount;
    private double profit;

//6.1
    public FixedDeposit(String name, String username,
                        String phoneNumber, String password,
                        String occupation, String CNIC, String email,
                        String address, int durationInYears, double amount, double profit) {

        super(name, username, phoneNumber, password,
                occupation, CNIC, email, address);
        this.dateOfFixing = LocalDate.now();
        this.dateOfMaturity = LocalDate.now().plusYears(durationInYears);
        this.amount = amount;
        this.profit = profit;
        addInterest();
    }
// 6.2
    // inner class
    //Adds interest after a certain period of time which is defined
    private class addInterestToAmount extends TimerTask {
        public void run() {
            profit += (amount / 100) * (interest / 30);
            System.out.println(profit);

        }
    }
    //6.3
    //Schedules and adds interest when certain period of time has passed
    private void addInterest() {
        Timer timer = new Timer();
        TimerTask task = new addInterestToAmount();
        timer.schedule(task, (long) (2.628 * Math.pow(10, 9)), (long) (2.628 * Math.pow(10, 9)));

    }


//6.4
    // It can only be used to withdraw profit
    private boolean withdrawMoney(double amount) {
        if (amount <= profit) {
            profit -= amount;
            return true;
        } else {
            return false;
        }
    }
//6.5
    // to call menu
    public void menu() {
        System.out.println();

        Date date = new Date();
        System.out.println(date + "\n");
        double amount;
        Menu home = new Menu(new String[]{"Get fixed deposit details", "Withdraw Profit", "Settings"});
        home.show();
        int opt = home.getOption();

        switch (opt) {
            case 0:
                Bank.quit();
                break; // Exit
            case 1: // Get fixed deposit details
                System.out.println(this);
                break;
            case 2: //Withdraw Profit
                System.out.println("\nTotal Available Profit: " + profit + "\n");
                Menu withdrawMenu = new Menu(new String[]{"Withdraw All", "Withdraw specific amount"});
                withdrawMenu.show();
                opt = withdrawMenu.getOption();
                switch (opt) {
                    case 0:
                        Bank.quit();
                        break; // Exit
                    case 1: // Withdraw All
                        withdrawMoney(profit);
                        break;
                    case 2: // Withdraw specific amount
                        System.out.println("Amount: ");
                        amount = Bank.input.nextDouble();
                        withdrawMoney(amount);
                        break;
                }
            case 3: // Settings
                Menu settings = new Menu(new String[]{"Change Password", "Change Phone Number", "Change Email"});
                settings.show();
                opt = settings.getOption();

                switch (opt) {
                    case 0:
                        Bank.quit(); // Exit
                    case 1: // Change Password
                        String newPass, newPass2, oldPass;
                        System.out.println("Old Password: ");
                        oldPass = Bank.input.next();
                        System.out.println("New Password: ");
                        newPass = Bank.input.next();
                        System.out.println("Re-enter new password: ");
                        newPass2 = Bank.input.next();
                        if (newPass.equals(newPass2) && !newPass.equals(oldPass)) {
                            if (changePassword(oldPass, newPass)) {
                                System.out.println("Password change successful");
                            } else
                                System.out.println("Password change unsuccessful");
                        } else
                            System.out.println("Password change unsuccessful");
                        break;
                    case 2: // Change Phone Number
                        String newPhoneNum, password;
                        System.out.println("Enter new phone number: ");
                        newPhoneNum = Bank.input.next();
                        System.out.println("Enter Password: ");
                        password = Bank.input.next();
                        if (changePhoneNumber(newPhoneNum, password)) {
                            System.out.println("Phone Number Changed SuccessFully!");
                        } else {
                            System.out.println("Password change Unsuccessful!");
                        }
                        break;
                    case 3:// Change Email
                        String newEmail;
                        System.out.print("Enter new email: ");
                        newEmail = Bank.input.next();
                        if (!newEmail.equals(this.email)) {
                            setEmail(email);
                            System.out.println("Change Successful!");
                        } else
                            System.out.println("Change Unsuccessful!");
                        break;

                }
        }
    }
//6.6
    @Override
    public String toString() {
        System.out.println();

        return
                "dateOfFixing=" + dateOfFixing +
                        "\ndateOfMaturity=" + dateOfMaturity +
                        "\namount=" + amount +
                        "\nprofit=" + profit;
    }
}


