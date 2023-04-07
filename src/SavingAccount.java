// 12.0

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SavingAccount extends Account implements Serializable {
    static final double WITHDRAWAL_LIMIT = 500000 ;
    double amountWithdrawn;
    final static double profitPercentage = 7; //

    static double serviceCharges = 200;

    // 12.1
    // Constructor
   public SavingAccount(String title,String username,  String phoneNumber, String password,
                         String occupation, String CNIC, String email, String address,
                        String accountNumber, String accountType,
                         int pin,  double balance, boolean cardActivation) {
        super(title, username, phoneNumber, password, occupation, CNIC,
                email, address,  accountNumber, accountType,
                 pin,   balance-serviceCharges, cardActivation);
        addInterest();
    }

    // 12.2
    // Inner class to define a task to be executed after a specified time period
    class addInterestToAmount extends TimerTask {
        public void run(){
            setBalance(getBalance()+(getBalance()/100)*profitPercentage) ;
                   }
    }

    // 12.3
    // Schedules and adds interest when certain period of time has passed
    private void addInterest(){
        Timer timer = new Timer();
        TimerTask task = new SavingAccount.addInterestToAmount();
        timer.schedule(task,(long) (2.628*Math.pow(10,9)), (long) (2.628*Math.pow(10,9)));

    }

    @Override
    // 12.4
    // To transfer ammount to other accounts
    protected boolean interBankFundsTransfer(String receiverAccNo, double amount, String reason) {
        if (getBalance()>amount && Bank.users.contains(new AccountHolder(receiverAccNo))&&
                amount<(WITHDRAWAL_LIMIT-amountWithdrawn)){
            Account accountHolder= ((Account)(Bank.users.get(Bank.users.indexOf(new AccountHolder(receiverAccNo)))));
            accountHolder.setBalance(accountHolder.getBalance() + amount);
            return true;
        }

        return false;
    }

    // 12.5
    //To call menu
    public void menu() {
        System.out.println();
        Date date = new Date();
        System.out.println(date + "\n");
        String accNum;
        double amount;
        while (true) {
            System.out.println("Account Title: " + getTitle());
            System.out.println("Account Number: " + getAccountNumber());
            System.out.println("Annual withdrawal Limit: " + WITHDRAWAL_LIMIT);
            System.out.println("Balance: " + getBalance());

            Menu home = new Menu(new String[]{"Funds Transfer", "Bill Payment", "Donations", "Transaction History", "Loans", "Settings", "Logout"});
            home.show();
            int opt = home.getOption();

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

                case 4: // Transactions History
                    System.out.println(getTransactionHistory());
                    break;

                case 5: // Loans
                    Menu loan = new Menu(new String[]{"New Loan", "Return to main menu"});
                    loan.show();
                    opt = Bank.input.nextInt();
                    switch (opt){
                        case 0: // Exit
                            break;
                        case 1: // New Loan
                            LoanApplication.menu(new Profile(title,username,phoneNumber,"123456",occupation,CNIC,email, address),"Saving Account");
                            break;
                        case 2: //Return to main menu
                            break;
                    }
                    break;

                case 6: // Settings
                    settingsMenu();
                    break;

                case 7: // Logout
                    Bank.logout();
                    break;
            }
        }
    }

}
