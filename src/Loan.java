// 7.0

import java.io.Serializable;
import java.time.LocalDate;

public class Loan implements Serializable {

    private double loanAmount;
    private String applicationNo;
    private Profile profile;
    private final int TOTAL_INSTALLMENT = 120;
    private double installmentAmount;
    private int installmentsLeft;
    private int installmentsPaid = 0;
    private String loanType;
    private LocalDate startingDate = LocalDate.now();
    private LocalDate nextDue = LocalDate.now().plusMonths(1);

    // 7.1
    public Loan(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    // 7.2
    public Loan(Profile profile,
                String loanType, double loanAmount, String applicationNo) {
        this.profile = profile;// new Profile(title,username,phoneNumber,password,occupation,CNIC,email,address);
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.installmentAmount = loanAmount / TOTAL_INSTALLMENT;
        this.installmentsLeft = TOTAL_INSTALLMENT - installmentsPaid;
        this.applicationNo = applicationNo;

    }

    // 7.3
    public double getRemainingLoanAmount() {
        return installmentsLeft * installmentAmount;
    }

    // 7.4
    public double getLoanAmount() {
        return loanAmount;
    }

    // 7.5
    public double getInstallmentAmount() {
        return installmentAmount;
    }

    // 7.6
    public int getInstallmentsLeft() {
        return installmentsLeft;
    }

    // 7.7
    public LocalDate getNextDue() {
        return nextDue;
    }

    // 7.8
    // Pays installments of a loan, more then one installments can be paied at once
    public static boolean payInstallment(String applicationNo) {
        Loan temp = new Loan(applicationNo);
        if (Bank.activeLoans.contains(temp)) {
            System.out.println("Installment Amount: " + temp.installmentAmount);
            System.out.print("No of installments to pay: ");
            int noOfInstallments = Bank.input.nextInt();
            temp.installmentsPaid += noOfInstallments;
            temp.installmentsLeft -= noOfInstallments;
            return true;
        } else {
            return false;
        }

    }

    @Override
    // 7.9
    public String toString() {
        return "\nName: " + profile.title +
                "\nOccupation: " + profile.occupation +
                "\nLoan Amount: " + loanAmount +
                "\nAmount Left: " + getRemainingLoanAmount() +
                "\nApplicationNo: " + applicationNo +
                "\nTotal Installments: " + TOTAL_INSTALLMENT +
                "\nInstallment Amount: " + installmentAmount +
                "\nInstallments Left: " + installmentsLeft +
                "\nInstallments Paid: " + installmentsPaid +
                "\nLoan Type: " + loanType +
                "\nStarting Date: " + startingDate +
                "\nNext Due: " + nextDue;
    }

}


