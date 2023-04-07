//2.0

import java.io.Serializable;
import java.util.Date;

public class Accountant extends Profile implements Serializable, User {
    //2.1
    public Accountant(String id) {
        this.username = id;
    }

    //2.2
    public Accountant(String title, String username, String phoneNumber,
                      String password, String occupation,
                      String CNIC, String email, String address) {
        super(title, username, phoneNumber, password,
                occupation, CNIC, email, address);
    }

    //2.3
    // Deducts funds from user account as withdraw
    public boolean withdrawMoney(String accNo, double amount) {

        int ind = Bank.users.indexOf(new AccountHolder(accNo));

        Account temp = (Account) Bank.users.get(ind);

        if (temp.getBalance() > amount) {

            temp.setBalance(temp.getBalance() - amount);
            return true;

        } else {

            return false;

        }

    }

    //2.4
    // Adds funds to user accounts as deposit
    public boolean depositMoney(String accNo, double amount) {

        int ind = Bank.users.indexOf(new AccountHolder(accNo));

        Account temp = (Account) Bank.users.get(ind);
        if (temp.getBalance() > amount) {
            temp.setBalance(temp.getBalance() + amount);
            temp.updateTransactionHistory("self", amount, null);
            return true;
        } else {
            return false;
        }
    }

    // 2.5
    public boolean depositMoney(String accHolderName, String accNo, double amount, String depositorsName, String reason) {

        int ind = Bank.users.indexOf(new AccountHolder(accNo));

        Account temp = (Account) Bank.users.get(ind);

        if (temp.getBalance() > amount) {

            temp.setBalance(temp.getBalance() + amount);
            temp.updateTransactionHistory(accNo, amount, reason + " Depositor: " + depositorsName);

            return true;

        } else {

            return false;

        }

    }

    // 2.6
    private void payBill(String billID, double amount) {
        System.out.println(amount + "rupees are paid against bill# " + billID);
    }

    //2.7
    public void menu() {
        System.out.println();

        int opt;
        String accNum, accHolderName, accTitle;
        double amount;
        Menu home = new Menu(new String[]{"Deposit", "Withdraw",
                "Pay Bills", "Pay Installment", "Settings", "Logout"});

        while (true) {
            Date date = new Date();
            System.out.println("\n" + date + "\n");
            home.show();
            opt = home.getOption();

            switch (opt) {
            /*default:
                System.out.print("Invalid Input!");
                break;*/
                case 0:
                    Bank.quit();
                    break;
                case 1:
                    // Deposit
                    Menu deposit = new Menu(new String[]{"Self-deposit", "Others"});
                    deposit.show();
                    opt = deposit.getOption();
                    if (opt == 1) {
                        System.out.print("Please Enter the following details: ");
                        System.out.print("Account Number: ");
                        accNum = Bank.input.next();
                        System.out.print("Account Holder's Name: ");
                        accHolderName = Bank.input.next();
                        System.out.print("Deposit Amount: ");
                        amount = Bank.input.nextDouble();
                        depositMoney(accNum, amount);
                        accNum = "";
                        accHolderName = "";
                        amount = 0;
                    } else if (opt == 2) {
                        String depositorsName, reason;
                        System.out.print("Please Enter the following details: ");
                        System.out.print("Account Number: ");
                        accNum = Bank.input.next();
                        System.out.print("Account Holder's Name(without space): ");
                        accHolderName = Bank.input.next();
                        System.out.print("Deposit Amount: ");
                        amount = Bank.input.nextDouble();
                        System.out.print("Depositer's Name(without space): ");
                        depositorsName = Bank.input.next();
                        System.out.print("Reason(without space): ");
                        reason = Bank.input.next();
                        depositMoney(accHolderName, accNum, amount, depositorsName, reason);
                        accHolderName = "";
                        accNum = "";
                        amount = 0;
                    } else
                        System.out.print("Invalid Input!");
                    break;
                case 2:
                    // Withdraw
                    boolean cond = false;
                    while (!cond) {
                        System.out.print("Account Number: ");
                        accNum = Bank.input.next();


                        Menu withdraw = new Menu(new String[]{"500", "1000", "5000", "10000", "20000", "Other Amount", "Return to Main menu"});
                        System.out.print("Select Amount:");
                        withdraw.show();
                        opt = withdraw.getOption();


                        switch (opt) {

                            case 0:
                                Bank.quit();
                                break;
                            case 1:
                                cond = withdrawMoney(accNum, 500);
                                break;
                            case 2:
                                cond = withdrawMoney(accNum, 1000);
                                break;
                            case 3:
                                cond = withdrawMoney(accNum, 5000);
                                break;
                            case 4:
                                cond = withdrawMoney(accNum, 10000);
                                break;
                            case 5:
                                cond = withdrawMoney(accNum, 20000);
                                break;
                            case 6:
                                System.out.print("Enter amount: ");
                                amount = Bank.input.nextDouble();
                                cond = withdrawMoney(accNum, amount);
                                break;
                            case 7:
                                menu();
                                cond = true;
                                break;
                            // Default is not required
                            default:
                                System.out.print("Invalid Input!");
                                break;
                        }
                        accNum = "";
                        accTitle = "";
                        amount = 0;
                        break;
                    }
                case 3:
                    // Pay Bills
                    System.out.print("Bill ID: ");
                    accNum = Bank.input.next();
                    System.out.print("Amount: ");
                    amount = Bank.input.nextDouble();
                    payBill(accNum, amount);
                    accNum = "";
                    amount = 0;
                    break;

                case 4: // Pay Installment
                    String appNo;
                    System.out.println("Application No: ");
                    appNo = Bank.input.next();

                    if (Loan.payInstallment(appNo)) {
                        System.out.println("Installment(s) payed successfully");
                    } else
                        System.out.println("Payment Failed");
                    break;
                case 5: // Settings
                    Menu settings = new Menu(new String[]{"Change Password",
                            "Change Phone Number", "Change Email", "Return to main menu"});

                    settings.show();
                    opt = settings.getOption();

                    switch (opt) {
                        case 0:
                            Bank.quit();
                            break;
                        case 1:
                            String newPass, newPass2, oldPass;
                            System.out.print("Old Password: ");
                            oldPass = Bank.input.next();
                            System.out.print("New Password: ");
                            newPass = Bank.input.next();
                            System.out.print("Re-enter new password: ");
                            newPass2 = Bank.input.next();
                            if (newPass.equals(newPass2) && !newPass.equals(oldPass)) {
                                if (changePassword(oldPass, newPass)) {
                                    System.out.print("Password change Successful");
                                } else
                                    System.out.print("Password change Unsuccessful");
                            } else
                                System.out.print("Password change unsuccessful");
                            break;
                        case 2:
                            String newPhoneNum, password;
                            System.out.print("Enter new phone number: ");
                            newPhoneNum = Bank.input.next();
                            System.out.print("Enter Password: ");
                            password = Bank.input.next();
                            if (changePhoneNumber(newPhoneNum, password)) {
                                System.out.print("PhoneNumber Changed!");
                            } else {
                                System.out.print("PhoneNumber Change UnSuccessful!");
                            }

                        case 3:
                            String newEmail;
                            System.out.print("Enter new email: ");
                            newEmail = Bank.input.next();
                            if (!newEmail.equals(this.email)) {
                                setEmail(email);

                                System.out.print("Change Successful!");
                            } else
                                System.out.print("Change Unsuccessful!");
                            break;
                        case 4: // Return to main menu
                            break;
                    }
                case 6: // Logout
                    Bank.logout();
                    break;
            }
        }
    }
}