//8.0

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class LoanApplication implements Serializable {
    private Profile p;
    private String loanType;
    private double amount;
    private boolean approvalStatus;
    private String applicationNo;
    private final static String[] TYPES_OF_LOAN = new String[]{"Car Loan",
            "Home Loan", "Personal Loan"};
    private static final String[] LOAN_DETAILS = new String[]{
            "\n# Financing limit between PKR 200,000 to PKR10,000,000. \n" +
                    "# Repay within a 7 year time period \n" +
                    "# Choose any type of vehicle, new, used, local or imported. \n" +
                    "# Receive financing up to 85% of the value of preferred car \n" +
                    "# Secured insurance at all times for peace of mind\n" +
                    "(with tracker facility) \n" +
                    "# Multiple Partial payment facility as per your convenience \n" +
                    "# Non- Resident Pakistani (NRP) can also avail OOB CarLoan\n" +
                    "facility \n" +
                    "# Our network of over 150 eligible dealers means you have\n" +
                    "lots of brands and cars to choose from \n" +
                    "# OOB has over 1700 branches, you can apply for CarLoan as\n" +
                    "per your convenience by visiting your nearest branch or \n" +
                    "through other alternate channels \n" +
                    "# 24/7 support available through OOB Phone Banking \n" +
                    "# Dedicated after sales service.",
            "\n# Financing limit between PKR 2,000,000 to PKR 40,000,000\n" +
                    "# Flexibility of choosing between fixed and variable mark-up rate\n" +
                    "# Repayment tenure ranging from 5 years to 20 years\n" +
                    "# Free property insurance at all times ensuring peace of mind",
            "\n# Financing limit between PKR 25,000 to PKR 3,000,000\n" +
                    "# Repayment tenure(s) ranging from 12 to 60 months\n" +
                    "# Availability of top-up facility after every 12 months\n" +
                    "# Life insurance coverage"};

    private static final String LOAN_ELIGIBILITY = "\n# Must be a Pakistani national\n" +
            "\n" +
            "# The age of the primary applicant must be 25 years at minimum and\n" +
            "65 years at maximum at the time of maturity. In case of co-applicant\n" +
            "the minimum age should be 21 years and maximum 70 years at the time\n" +
            "of financing maturity.\n" +
            "\n" +
            "# For salaried person, applicant and co-applicant financing maturity\n" +
            "date will be their date of retirement\n" +
            "\n" +
            "# For self-employed, the maximum age of the co-applicant will be\n" +
            "65 years at the time of financing maturity, if business income is being\n" +
            "clubbed and 70 years if its is not.\n" +
            "\n" +
            "# Meezan bank home loan can be availed by the individual as a primary\n" +
            "applicant or with a co-applicant as well. However, it is mandatory for a\n" +
            "co-applicant to be an immediate family member.\n" +
            "\n" +
            "# Minimum income of a salaried person must be PKR 40,000/- per month,\n" +
            "while in case of self-employed it is PKR 75,000/-\n" +
            "\n" +
            "# It is mandatory for a salaried person to be on a permanent job with\n" +
            "at-least 2 years continuous working experience in the same industry. On\n" +
            "the other hand, for business, it is 3 years.\n";

// 8.1
    public LoanApplication(Profile p,
                           String loanType,
                           double amount
    ) {
        this.p = p;
        this.applicationNo = "AP-" + applicationNoGenerator();

        this.loanType = loanType;
        this.amount = amount;

    }
// 8.2
    public LoanApplication(String title, String username,
                           String phoneNumber,
                           String occupation, String CNIC,
                           String email, String address,
                           String loanType,
                           double amount) {
        this.p = new Profile(title, username, phoneNumber, "123456", occupation, CNIC, email, address);
        this.loanType = loanType;
        this.amount = amount;
        this.applicationNo = "AP-" + applicationNoGenerator();


    }

    // 8.3
    public LoanApplication(String applicationNo){
        this.applicationNo = applicationNo;
    }

    // 8.4
    // Generates a random application number for new loan applications
    private String applicationNoGenerator(){
        String abc = "";
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int a = r.nextInt(10);
            abc+=a;
        }
        return abc;
    }

    // 8.5
    public static void applyForNewLoan(String caller, Profile p, String loanType) {
        String title, phoneNum, CNIC, occupation, email, address, username;
        double amount;
        if (caller.equals("bank")) {
            System.out.print("Title(without space): ");
            title = Bank.input.next();
            System.out.print("Phone Number: ");
            phoneNum = Bank.input.next();
            System.out.print("CNIC: ");
            CNIC = Bank.input.next();
            System.out.print("Occupation: ");
            occupation = Bank.input.next();
            System.out.print("Email: ");
            email = Bank.input.next();
            System.out.print("Address(without space): ");
            address = Bank.input.next();
            System.out.println("Amount: ");
            amount = Bank.input.nextDouble();
            username = Manager.generateUserName(title, 3);
            LoanApplication temp = new LoanApplication(title, username, phoneNum, occupation, CNIC, email, address, loanType, amount);
            Bank.loanApplications.add(temp);
            System.out.println("Application: " + temp.applicationNo);

        } else {
            System.out.print("Amount: ");
            amount = Bank.input.nextDouble();
            Bank.loanApplications.add(new LoanApplication(p, loanType, amount));
        }
    }

    // 8.6
    public Profile getP() {
        return p;
    }

    // 8.7
    public String getLoanProcedure(String loanType) {
        return null;
    }

    //8.8
    public String getApplicationNo() {
        return applicationNo;
    }

    // 8.9
    public String getLoanType() {
        return loanType;
    }

    // 8.10
    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    // 8.11
    public double getAmount() {
        return amount;
    }

    // 8.12
    public static String getLoanApplicationStatus(String applicationNumber) {
          boolean approval = Bank.loanApplications.contains(new LoanApplication(applicationNumber));
        if (approval) {
            return "Pending";
        } else {
            return "Approved";
        }
    }

    // 8.13
    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    // 8.14
    public static void menu(Profile p, String caller) {
        System.out.println();

        Menu loanApplicationMenu = new Menu(new String[]{"Types of Loan",
                "Loan Eligibility", "Apply for new Loan", "Check Application Status",
                "Return to Main Menu"});
        int i = 0;
        while (i == 0) {
            loanApplicationMenu.show();

            switch (loanApplicationMenu.getOption()) {
                case 0: // Exit
                    Bank.quit();
                    break;
                case 1: // Types of loans
                    Menu loan_type = new Menu(TYPES_OF_LOAN);
                    loan_type.show();
                    int option = loan_type.getOption();
                    if (option == 0) {
                        Bank.quit();
                    }
                    System.out.println(LOAN_DETAILS[option-1]);
                    break;
                case 2: // Loan Eligibility
                    System.out.println(LOAN_ELIGIBILITY);
                    break;
                case 3: // Apply for new Loan
                    loan_type = new Menu(TYPES_OF_LOAN);
                    loan_type.show();
                    option = loan_type.getOption();
                    switch (option) {
                        case 0: // Exit
                            Bank.quit();
                            break;

                        default:
                            applyForNewLoan(caller, p, TYPES_OF_LOAN[option - 1]);
                            break;
                    }

                    break;
                case 4: // Check Application Status
                    System.out.print("Enter Application Number: ");
                    String applicationNo = Bank.input.next();
                    System.out.println(getLoanApplicationStatus(applicationNo));
                    break;
                case 5: // Return to main menu
                    i = 10;
                    break;

            }
        }
    }

    @Override
    // 8.15
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanApplication that = (LoanApplication) o;
        return applicationNo.equals(that.applicationNo);
    }

    @Override
    // 8.16
    public String toString() {
        return "\nName: "+ p.title+
                "\nOccupation: "+ p.occupation+
                "\nLoan Type: " + loanType +
                "\nAmount: " + amount +
                "\nApproval Status: " + approvalStatus +
                "\nApplication No: " + applicationNo;
    }
}
