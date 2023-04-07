// 1.0

import java.io.Serializable;
import java.time.LocalDate;


public abstract class Account extends AccountHolder implements Serializable {
    private final LocalDate dateOfOpening = LocalDate.now();
    private String accountType;
    private int pin;
    private final double SERVICE_CHARGES = 200;
    private String transactionHistory;
    private double balance;
    private boolean cardActivation;

    // 1.1

    public Account(String title, String username, String phoneNumber,
                   String password, String occupation,
                   String CNIC, String email, String address,
                   String accountNumber, String accountType,
                   int pin, double balance, boolean cardActivation) {
        super(title, username, accountNumber,
                phoneNumber, password, occupation,
                CNIC, email, address);

        this.accountType = accountType;
        this.pin = pin;
        this.balance = balance-SERVICE_CHARGES;
        this.cardActivation = cardActivation;
        updateTransactionHistory("BANK",200,"Service Charges");
    }



    // 1.2
    public Account(String accountNumber) {
        super(accountNumber);
    }


     //1.3
     //Funds transfer for transfering funds within the bank
    protected abstract boolean interBankFundsTransfer(String receiverAccNo,
                                                      double amount, String reason);
    //1.4
    private boolean intraBankFundsTransfer(String receiverAccNo,
                                           String bankName, double amount, String reason) {
        if (amount < balance) {
            balance -= amount;
            updateTransactionHistory(receiverAccNo, amount, reason);
            return true;
        } else {
            return false;
        }
    }

    // 1.5
    // Deducts funds from the account to pay bills
    public boolean payBill(String billId, double amount) {

        if (amount > balance) {
            balance -= amount;
            updateTransactionHistory(billId, amount);
            return true;
        } else
            return false;
    }

    // 1.6
    // Deducts funds from account as donations
    public boolean donations(String organization, String accountNumber,
                             double amount) {
        if (amount > balance) {
            balance -= amount;

            updateTransactionHistory(organization, accountNumber, amount);
        }
        return true;
    }

    // 1.7
    // appends all transaction details to field
    public void updateTransactionHistory(String billId, double amount) {
        LocalDate date = LocalDate.now();
        transactionHistory += "\n" + billId + "\t" + date + "\t"
                + amount;

    }

    // 1.8
    // Overloaded function to update transfer history on the basis of Reciever's account number
    public void updateTransactionHistory(String receiverAccNo,
                                         double amount, String reason) {
        LocalDate date = LocalDate.now();
        transactionHistory += "\nReceiver's Account No: " + receiverAccNo + "\t" + date + "\tAmount: " + amount + "\tReason: " + reason;

    }

    // 1.9
    // Overloaded function to update transfer history on the basis of Receiving Organizations
    public void updateTransactionHistory(String organization, String accountNumber, double amount) {
        LocalDate date = LocalDate.now();
        transactionHistory += "\nReceiver's Account No: " + accountNumber + "\tOrganization: " + organization + "\t" + date + "\tAmount: " + amount;

    }

    //1.10
    // Changes account pin for the user if the password is correct
    private boolean changePin(int oldPin, int newPin, String password) {
        if (oldPin == pin && password.equals(this.password) && (!(oldPin == newPin))) {
            pin = newPin;
            return true;
        } else
            return false;
    }

    // 1.11
    // Returns account holder's title
    public String getTitle() {
        return title;
    }

    // 1.12
    // Returns transaction history
    public String getTransactionHistory() {
        return transactionHistory;
    }

    // 1.13
    // Returns account balance
    public double getBalance() {
        return balance;
    }

    // 1.14
    // returns card activation status
    public boolean isCardActivation() {
        return cardActivation;
    }

    // 1.15
    // sets account balance to given amount
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // 1.16
    public abstract void menu();

    // 1.17
    // Submenu for Funds transfer
    public void fundsTransferMenu() {
        System.out.println();

        int opt;
        String accNum, reason;
        double amount;
        Menu fundsTransferMenu = new Menu(new String[]{"Inter-Bank", "Intra-Bank", "Return to main menu"});
        fundsTransferMenu.show();
        opt = fundsTransferMenu.getOption();
        switch (opt) {
            case 0:
                Bank.quit();
                break; // Exit
            case 1: // Inter-Bank
                System.out.print("Account Number: ");
                accNum = Bank.input.next();
                System.out.print("Amount: ");
                amount = Bank.input.nextDouble();
                System.out.println("Enter Reason(without space): ");
                reason = Bank.input.next();
                if (interBankFundsTransfer(accNum, amount, reason.trim())) {
                    System.out.println("Transaction Completed Successfully!");
                } else {
                    System.out.println("Transaction Failed!");
                }
                accNum = "";
                amount = 0;
                break;
            case 2: // Intra-Bank
                String bankName;
                System.out.print("Bank Name(without space): ");
                bankName = Bank.input.next();
                System.out.print("Account Number: ");
                accNum = Bank.input.next();
                System.out.print("Amount: ");
                amount = Bank.input.nextDouble();
                System.out.println("Enter Reason(without space): ");
                reason = Bank.input.next();
                intraBankFundsTransfer(accNum, bankName, amount, reason.trim());
                break;
            case 3: // Return to main menu
                break;
        }
    }

    // 1.18
    // Submenu for settings
    public void settingsMenu() {
        System.out.println();

        int opt;
        String status;
        if (isCardActivation())
            status = "Active";
        else
            status = "Inactive";

        Menu settings = new Menu(new String[]{"Change Password", "Change Phone Number",
                "Change Email", "Change Pin", "Toggle Card Activation\t\t\tCurrently Card is: " + status,
                "Return to main menu"});

        settings.show();
        opt = settings.getOption();
        switch (opt) {
            case 0:
                Bank.quit();
                break; // Exit

            case 1: // Change Password
                String newPass, newPass2, oldPass;
                System.out.println("Old Password: ");
                oldPass = Bank.input.next();
                System.out.println("New Password: ");
                newPass = Bank.input.next();
                System.out.println("Re-enter new password: ");
                newPass2 = Bank.input.next();

                if (newPass.equals(newPass2)) {
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
                    System.out.println("Phone Number changed Successfully!");
                } else {
                    System.out.println("PhoneNumber Change Unsuccessful!");
                }
                password = "";
                break;

            case 3: // Change Email
                String newEmail;
                System.out.print("Enter new email: ");
                newEmail = Bank.input.next();
                if (!newEmail.equals(super.email)) {
                    super.setEmail(newEmail);
                    System.out.println("Change Successful!");
                } else
                    System.out.println("Change Unsuccessful!");
                break;

            case 4: // Change Pin
                int newPin, newPin2, oldPin;
                System.out.println("Enter old Pin: ");
                oldPin = Bank.input.nextInt();
                System.out.println("Enter new Pin: ");
                newPin = Bank.input.nextInt();
                System.out.println("Re-Enter new Pin: ");
                newPin2 = Bank.input.nextInt();
                System.out.println("Enter Password: ");
                password = Bank.input.next();
                if (oldPin != newPin) {
                    if (newPin == newPin2) {
                        if (changePin(oldPin, newPin, password)) {
                            System.out.println("Pin Changed Successfully!");
                        } else {
                            System.out.println("Password Incorrect");
                        }

                    } else {
                        System.out.println("New Pin do not match");
                    }
                } else {
                    System.out.println("Old Pin similar to new Pin");
                }
                break;

            case 5: // Toggle Card Activation
                if (isCardActivation()) {
                    cardActivation = !isCardActivation();
                    System.out.println("Card Activation Status Updated to: Inactive");
                } else {
                    cardActivation = !isCardActivation();
                    System.out.println("Card Activation Status Updated to: Active");
                }
                break;

            case 6: // Return to main menu
                break;
        }
    }

    // 1.19
    // Submenu for donations
    public void donationsMenu() {
        System.out.println();

        String accNum;
        double amount;
        String organizationName;
        System.out.println("Organization's Name(without space): ");
        organizationName = Bank.input.next();
        System.out.println("Account Number: ");
        accNum = Bank.input.next();
        System.out.println("Amount: ");
        amount = Bank.input.nextDouble();
        donations(organizationName, accNum, amount);
    }

    // 1.20
    // Submenu forbill payment
    public void billPaymentMenu() {
        System.out.println();

        String billID, accNum;
        double amount;
        System.out.print("Bill ID: ");
        billID = Bank.input.next();
        System.out.print("Amount: ");
        amount = Bank.input.nextDouble();
        this.payBill(billID, amount);
    }

    @Override
    // 1.21
    public String toString() {
        System.out.println();

        return  "Date Of Opening = " + dateOfOpening +
                "\nAccountType = " + accountType +
                "\nBalance = " + balance +
                "\nCardActivation = " + cardActivation +
                "\nAccountNumber = " + accountNumber +
                "\nTitle = " + title +
                "\nPhoneNumber = " + phoneNumber +
                "\nUsername = " + username +
                "\nOccupation = " + occupation +
                "\nCNIC = " + CNIC +
                "\nEmail = " + email +
                "\nAddress = " + address;
    }
}
