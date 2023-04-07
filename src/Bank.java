//4.0

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Bank implements Serializable {
    static final String FILENAME = "Bank.dat";
    static public ArrayList<User> users;
    static public ArrayList<Loan> activeLoans;
    static public ArrayList<LoanApplication> loanApplications;
    public static final transient Scanner input = new Scanner(System.in);
    static File file = new File("Bank.dat");

    // 4.1 main(Runner)
    public static void main(String[] args) {
        Bank b = null;
        try {
            b = new Bank();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            b.loginMenu();
        }
    }

    //4.2
    //Funtion for FileIO
    public Bank() throws IOException {
        users = new ArrayList<User>();
        activeLoans = new ArrayList<Loan>();
        loanApplications = new ArrayList<LoanApplication>();


        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(FILENAME);
                ObjectInputStream input = new ObjectInputStream(fis);

                users = (ArrayList<User>) input.readObject();
                activeLoans = (ArrayList<Loan>) input.readObject();
                loanApplications = (ArrayList<LoanApplication>) input.readObject();

                input.close();

            } catch (ClassNotFoundException | IOException exception) {

                exception.printStackTrace();
            }
        } else {
            String name, phoneNum, CNIC, email, address;
            file.createNewFile();
            System.out.print("Name: ");
            name = Bank.input.next().trim();
            System.out.print("Phone Number: ");
            phoneNum = Bank.input.next();
            System.out.print("CNIC: ");
            CNIC = Bank.input.next();
            System.out.print("Email: ");
            email = Bank.input.next();
            System.out.print("Address: ");
            address = Bank.input.next();
            users.add(new Manager(name, "MG", phoneNum, "123456", CNIC, email, address));
            writeChangesToFile(FILENAME);
            System.out.println("Username: MG\nPassword: 123456");
        }

    }

    //4.3
    // updates array lists such that, all applications
    // with accepted application status are sent to active loans list
    public static void updateLoanApplications() {
        for (int i = 0; i < loanApplications.size(); i++) {
            if (loanApplications.get(i).isApprovalStatus()) {
                LoanApplication temp = loanApplications.get(i);
                activeLoans.add(new Loan(temp.getP(), temp.getLoanType(), temp.getAmount(), temp.getApplicationNo()));
                loanApplications.remove(i);

            }
        }


    }

    //4.4
    // writes all the changes to a .dat file
    public static void writeChangesToFile(String fileName) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(FILENAME, false));

            output.writeObject(users);
            output.flush();

            output.writeObject(activeLoans);
            output.flush();

            output.writeObject(loanApplications);
            output.flush();


            output.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /*
    4.5
     Verifies the credintials for login and logins in users accordingly
    */
    public static boolean login(String username, String password) {
        boolean ret = false;
        User temp = new Profile(username, password);
        if (users.contains(temp)) {
            int pos = users.indexOf(temp);

            if (users.get(pos) instanceof CurrentAccount) {
                if (((CurrentAccount) users.get(pos)).password.equals(password)) {
                    ((CurrentAccount) users.get(pos)).menu();
                    ret = true;

                } else {
                    System.out.println("Invalid ID/password");
                    ret = false;
                }
            } else if (users.get(pos) instanceof SavingAccount) {
                if (((SavingAccount) users.get(pos)).password.equals(password)) {
                    ((SavingAccount) (users).get(pos)).menu();
                    System.out.println("login1234");
                    ret = true;

                } else {
                    System.out.println("Invalid ID/password");
                    ret = false;
                }
            } else if (users.get(pos) instanceof FixedDeposit) {
                if (((FixedDeposit) users.get(pos)).password.equals(password)) {
                    ((FixedDeposit) users.get(pos)).menu();
                    ret = true;
                } else {
                    System.out.println("Invalid ID/password");
                    ret = false;
                }


            } else if (users.get(pos) instanceof Manager) {
                if (((Manager) users.get(pos)).password.equals(password)) {
                    ((Manager) users.get(pos)).menu();
                    ret = true;
                } else {
                    System.out.println("Invalid ID/password");
                    ret = false;
                }

            } else if (users.get(pos) instanceof Accountant) {
                if (((Accountant) users.get(pos)).password.equals(password)) {
                    ((Accountant) users.get(pos)).menu();
                    ret = true;
                } else {
                    System.out.println("Invalid ID/password");
                    ret = false;
                }


            }
        } else {
            System.out.println("Incorrect Id/ Password");
            ret = false;
        }
        return ret;
    }

    //4.6
    public static void logout() {
        writeChangesToFile(FILENAME);
        loginMenu();
    }

    public static void loginMenu() {
        System.out.println();
        Date date = new Date();
        System.out.println(date + "\n");
        System.out.println("Welcome!");
        String[] w = {"Login", "Loan Application"};
        Menu welcome = new Menu(w);
        welcome.show();

        int opt = welcome.getOption();
        if (opt == 1) {
            while (true) {
                String ID, password;
                System.out.print("Username: ");
                ID = input.next();
                System.out.print("Password: ");
                password = input.next();
                if (login(ID, password)) {
                    break;
                }

            }
        } else if (opt == 2) {
            LoanApplication.menu(null, "bank");
        } else if (opt == 0)
            quit();
        else
            System.out.println("Invalid Input!");

    }

    //4.7
    // Clears all the data by deleting the file on which all the data is saved
    static public void clearAllData() {
        if (file.delete()) {
            System.out.println("Data Cleared");
        }
        System.exit(0);
    }

    //4.8
    // Write changes to file and exits the application
    static public void quit() {
        writeChangesToFile(FILENAME);

        System.exit(0);
    }

}
