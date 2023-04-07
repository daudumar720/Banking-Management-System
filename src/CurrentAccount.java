// 5.0

import java.io.Serializable;
import java.util.Date;

public class CurrentAccount extends Account implements Serializable {

    // 5.1
    private static double withdrawalLimit;

    // 5.2
    private static double serviceCharges;

    // 5.3

    public CurrentAccount(String title, String username, String phoneNumber, String password,
                          String occupation, String CNIC, String email, String address,
                          String accountNumber, String accountType, int pin,
                          double balance, boolean cardActivation) {

        super(title, username, phoneNumber,
                password, occupation, CNIC,
                email, address, accountNumber,
                accountType, pin, balance, cardActivation);
    }

    // 5.4
    public CurrentAccount(String recieverAccNo) {
        super(recieverAccNo);
    }


    // 5.5

    private static void setWithdrawalLimit(double withdrawalLimit) {
        CurrentAccount.withdrawalLimit = withdrawalLimit;
    }


    @Override
    // 5.6
    //Returns true if interbank transfer is possible.
    protected boolean interBankFundsTransfer(String recieverAccNo, double amount, String reason) {
        if (getBalance() > amount && Bank.users.contains(new AccountHolder(recieverAccNo))) {
            Account accountHolder = ((Account) (Bank.users.get(Bank.users.indexOf(new AccountHolder(recieverAccNo)))));
            accountHolder.setBalance(accountHolder.getBalance() + amount);
            return true;
        }


        return false;
    }

    // 5.7
    public void menu() {


        int opt;
        String accNum;
        double amount;
        Menu home = new Menu(new String[]{"Funds Transfer", "Bill Payment",
                "Donations", "Transaction History", "Loans", "Settings", "Logout"});

        while (true) {
            System.out.println();
            Date date = new Date();
            System.out.println(date + "\n");
            System.out.println("Account Title: " + super.getTitle() +
                    "\nAccount Number: " + super.getAccountNumber() +
                    "\nBalance: " + super.getBalance());

            home.show();
            opt = home.getOption();
            switch (opt) {
                case 0: // Exit
                    Bank.quit();
                    break;

                case 1: // Funds Transfer
                    fundsTransferMenu();
                    break;

                case 2: // Bill Payment
                    billPaymentMenu();
                    break;

                case 3: // Donations
                    donationsMenu();
                    break;

                case 4: // Transaction History
                    System.out.println(getTransactionHistory());
                    break;

                case 5: // Loans
                    Menu loan = new Menu(new String[]{"New Loan", "Return to main menu"});
                    loan.show();
                    opt = Bank.input.nextInt();
                    switch (opt) {
                        case 0: // Exit
                            break;
                        case 1: // New Loan
                            LoanApplication.menu(new Profile(title, username, phoneNumber, "123456", occupation, CNIC, email, address), "CurrentAccount");
                            break;
                        case 2: // Return to main menu
                            break;
                    }
                    break;
                case 6:
                    settingsMenu();
                    break;

                case 7: // Logout
                    Bank.logout();
                    break;


            }
        }
    }

}
