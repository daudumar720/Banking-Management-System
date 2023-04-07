// 9.0

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Manager extends Profile implements Serializable {

    //9.1
    public Manager(String name, String username,
                   String phoneNumber, String password,
                   String CNIC, String email, String address) {
        super(name, username, phoneNumber, password,
                "Manager", CNIC, email, address);
    }

    //9.2
    // used to genetate account number automatically
    private String generateAccNum(int i) {
        String accountNun = null;
        switch (i) {
            case 1:
                accountNun = "CR";

                break;
            case 2:
                accountNun = "SV";
                break;
            case 3:
                accountNun = "FD";
            default:
                accountNun = "EM";
        }
        Random rd = new Random();
        for (int j = 0; j < 10; j++) {
            accountNun += rd.nextInt(10);
        }
        return accountNun;
    }

    //9.3
    // used to genetate username automatically
    public static String generateUserName(String Title, int opt) {
        String userName = null;
        switch (opt) {
            case 1:
                userName = "C";

                break;
            case 2:
                userName = "S";
                break;
            case 3:
                userName = "FD";
            default:
                userName = "E";
        }
        Random rd = new Random();
        for (int j = 0; j < 6; j++) {
            userName += rd.nextInt(10);
        }
        return userName;

    }

    //9.4
    // to delete accountant class
    public boolean delAccountant(String ID) {
        if (Bank.users.contains(new Accountant(ID))) {
            Bank.users.remove(Bank.users.indexOf(new Accountant(ID)));
            return true;
        } else {
            return false;
        }
    }

    //9.5
    // to delete account
    public boolean delAccount(String accountNo) {
        if (Bank.users.contains(new AccountHolder(accountNo))) {
            Bank.users.remove(Bank.users.indexOf(new Accountant(accountNo)));
            return true;
        } else {
            return false;
        }

    }

    //9.6
    // To get specific account's details
    public void getAccountDetails(String accountNo) {
        if (Bank.users.contains(new AccountHolder(accountNo))) {
            System.out.println(Bank.users.get(Bank.users.indexOf(new AccountHolder(accountNo))).toString()
            );
        } else System.out.println("Account not Found");

    }

    //9.7
    // To show menu
    public void menu() {

        String name, accNum, password, title, phoneNum, CNIC, occupation, email, address, uname;
        int opt;
        Menu home = new Menu(new String[]{"Accounts", "Employees",
                "Loans", "Settings", "Logout"});

        while (true) {
            System.out.println();
            Date date = new Date();
            System.out.println(date + "\n");
            System.out.println("Name: " + super.title);
            System.out.println("Total Active Accounts: " + Bank.users.size());
            home.show();
            opt = home.getOption();
            switch (opt) {
                case 0: // Exit
                    Bank.quit();
                    break;
                case 1: // Accounts
                    Menu accounts = new Menu(new String[]{"Create New Account",
                            "Delete Account", "Check Account Details", "View All Accounts",
                            "Return to main menu"});
                    accounts.show();
                    opt = accounts.getOption();
                    switch (opt) {
                        case 0: // Exit
                            Bank.quit();
                            break;
                        case 1: // Create New Account
                            double startingBalance;
                            boolean cardActivation = false;

                            System.out.print("Title (without Space): ");
                            title = Bank.input.next();
                            System.out.print("Phone Number: ");
                            phoneNum = Bank.input.next();
                            System.out.print("CNIC: ");
                            CNIC = Bank.input.next();
                            System.out.print("Occupation: ");
                            occupation = Bank.input.next();
                            System.out.print("Email: ");
                            email = Bank.input.next();
                            System.out.print("Starting Balance: ");
                            startingBalance = Bank.input.nextDouble();
                            System.out.print("Address:(without space) ");
                            address = Bank.input.next();
                            System.out.print("Card Activation: \n1. Active\n2. Inactive" +
                                    "\nChoice: ");
                            opt = Bank.input.nextInt();
                            switch (opt) {
                                default:
                                    System.out.println("Invalid Input");
                                    break;
                                case 1:
                                    cardActivation = true;
                                    break;
                                case 2:
                                    break;
                            }

                            Menu accType = new Menu(new String[]{"Current Account",
                                    "Savings Account", "Fixed Deposit"});
                            accType.show();
                            opt = accType.getOption();
                            switch (opt) {
                                case 0: // Exit
                                    Bank.quit();
                                    break;
                                case 1: // Current Account
                                    uname = generateUserName(title, opt);
                                    String acountNo = generateAccNum(opt);
                                    Bank.users.add(new CurrentAccount(title, uname, phoneNum,
                                            "123456", occupation, CNIC, email, address, acountNo,
                                            "Current Account", 1234, startingBalance, cardActivation));
                                    System.out.println("Username: " + uname);
                                    System.out.println("Account Number: " + acountNo);
                                    System.out.println("Password: 123456");
                                    break;
                                case 2: // Savings Account
                                    uname = generateUserName(title, opt);
                                    acountNo = generateAccNum(opt);
                                    Bank.users.add(new SavingAccount(title, uname, phoneNum,
                                            "123456", occupation, CNIC, email, address, acountNo,
                                            "Current Account", 1234, startingBalance, cardActivation));
                                    System.out.println("Username: " + uname);
                                    System.out.println("Account Number: " + acountNo);
                                    System.out.println("Password: 123456");
                                    break;
                                case 3: // Fixed deposit
                                    int duration;
                                    double amount;
                                    System.out.print("Duration: ");
                                    duration = Bank.input.nextInt();
                                    System.out.print("Amount: ");
                                    amount = Bank.input.nextDouble();
                                    uname = generateUserName(title, opt);
                                    acountNo = generateAccNum(opt);
                                    Bank.users.add(new FixedDeposit(title, uname, phoneNum,
                                            "123456", occupation, CNIC, email, address, duration, amount,
                                            7));
                                    System.out.println("Username: " + uname);
                                    System.out.println("Account Number: " + acountNo);
                                    System.out.println("Password: 123456");
                                    break;
                            }
                            break;
                        case 2: // Delete Account
                            System.out.print("Account Number: ");
                            accNum = Bank.input.next();
                            delAccount(accNum);
                            break;
                        case 3: // Check Account Details
                            System.out.print("Account Number: ");
                            accNum = Bank.input.next();
                            getAccountDetails(accNum);
                            break;
                        case 4: // View All Accounts
                            for (int i = 1; i < Bank.users.size(); i++) {
                                System.out.println();
                                System.out.println("Name: " + ((Profile) (Bank.users.get(i))).getTitle());
                                System.out.println("Username: " + ((Profile) (Bank.users.get(i))).getUsername());
                                System.out.println("Account number: " + ((Profile) (Bank.users.get(i))).accountNumber);
                            }
                            break;
                        case 5: // Return to main menu
                    }
                    break;
                case 2: // Employees
                    Menu employees = new Menu(new String[]{"Add Employee",
                            "Remove Employee", "Return to main menu"});
                    employees.show();
                    opt = employees.getOption();
                    switch (opt) {
                        case 0: // Exit
                            Bank.quit();
                            break;
                        case 1: // Add Employee
                            System.out.print("Name (without space): ");
                            name = Bank.input.next();
                            System.out.print("Phone Number: ");
                            phoneNum = Bank.input.next();
                            System.out.print("CNIC: ");
                            CNIC = Bank.input.next();
                            System.out.print("Email: ");
                            email = Bank.input.next();
                            System.out.print("Address (without space): ");
                            address = Bank.input.next();
                            uname = generateUserName(name, 4);
                            Bank.users.add(new Accountant(name, uname, phoneNum, "123456", "accountant", CNIC, email, address));
                            System.out.println("Username: " + uname);
                            System.out.println("Password: 123456");
                            break;
                        case 2: // Remove Employee
                            String ID;
                            System.out.print("Enter Employee ID: ");
                            ID = Bank.input.next();
                            if (delAccountant(ID)) {
                                System.out.println("Removed SuccessFully");
                            } else {
                                System.out.println("Removal Unsuccessfull");
                            }
                            break;
                        case 3: // Return to main menu
                            break;
                    }
                    break;
                case 3: // Loans
                    Menu loans = new Menu(new String[]{"View Loan Applications",
                            "View Approved Loans", "Return ro main menu"});
                    loans.show();
                    opt = loans.getOption();
                    switch (opt) {
                        case 0: // Exit
                            Bank.quit();
                            break;
                        case 1: // View Loan Applications
                            for (int i = 0; i < Bank.loanApplications.size(); i++) {

                                System.out.println(Bank.loanApplications.get(i).toString());

                                Menu viewLoanApplications = new Menu(new String[]{
                                        "Accept", "Reject", "Skip", "Return to main menu"});
                                viewLoanApplications.show();
                                opt = viewLoanApplications.getOption();
                                switch (opt) {
                                    case 0: //Exit
                                        Bank.quit();
                                        break;
                                    case 1: // Accept
                                        Bank.loanApplications.get(i).setApprovalStatus(true);
                                        break;
                                    case 2: // Reject
                                        Bank.loanApplications.remove(i);
                                        break;
                                    case 3: // Skip
                                        break;
                                    case 4: // Return to main menu
                                        i = Bank.loanApplications.size();
                                        break;
                                }
                                Bank.updateLoanApplications();
                            }
                            break;
                        case 2: // View Approved Loans
                            for (int i = 0; i < Bank.activeLoans.size(); i++) {
                                System.out.print(Bank.activeLoans.get(i).toString());
                            }
                            break;
                        case 3: // Return to main menu
                            break;
                    }
                    break;
                case 4: // Settings
                    Menu settings = new Menu(new String[]{
                            "Reset Password for any account", "Change Password",
                            "Change Email", "Clear All Data", "Return to main menu"});
                    settings.show();
                    opt = settings.getOption();
                    switch (opt) {
                        case 0: // Exit
                            Bank.quit();
                            break;
                        case 1: // Reset password for any account
                            System.out.print("Enter Account Number: ");
                            accNum = Bank.input.next();
                            if (Bank.users.contains(new AccountHolder(accNum))) {
                                ((Profile) (Bank.users.get(Bank.users.indexOf(new AccountHolder(accNum))))).resetPassword();
                                System.out.println("Password Reset Successfully!");
                            } else {
                                System.out.println("Password reset failed!");
                            }
                            break;

                        case 2: // Change password
                            String newPass, newPass2, oldPass;
                            System.out.print("Enter old Password: ");
                            oldPass = Bank.input.next();
                            System.out.print("Enter new password: ");
                            newPass = Bank.input.next();
                            System.out.print("Re-Enter new Password: ");
                            newPass2 = Bank.input.next();
                            if (newPass.equals(newPass2)) {
                                if (changePassword(oldPass, newPass)) {
                                    System.out.println("Password Change Successful");
                                } else {
                                    System.out.println("Password change Failed");
                                }
                            } else
                                System.out.println("New Passwords Do not match");
                            break;
                        case 3: // Change Email
                            System.out.print("New Email: ");
                            email = Bank.input.next();
                            System.out.print("Password: ");
                            password = Bank.input.next();
                            break;
                        case 4: // Clear all data
                            Menu clearAllData = new Menu(new String[]{
                                    "Yes", "No"
                            });
                            System.out.print("Are you sure: ");
                            clearAllData.show();
                            opt = clearAllData.getOption();

                            switch (opt) {
                                case 0: //Exit
                                    Bank.quit();
                                    break;
                                case 1: // Yes
                                    System.out.print("Enter Password: ");
                                    password = Bank.input.next();
                                    if (password.equals(this.password)) {
                                        Bank.clearAllData();
                                    }
                                    break;
                                case 2: // No
                                    break;
                            }
                            break;
                        case 5: // Return to main menu
                            break;
                    }
                    break;
                case 5: // Logout
                    Bank.logout();
                    break;
            }
        }
    }
}
