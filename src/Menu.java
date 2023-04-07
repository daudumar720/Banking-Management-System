// 10.0

import java.util.Scanner;
public class Menu {


    private static Object Menu;
    String[]mOptions;
//10.1
    Menu(String[] menuOptions) {
        mOptions = menuOptions;
    }


    class INVALID_OPTION_EXCEPTION extends Exception{
        public INVALID_OPTION_EXCEPTION( ) {
            super("Invalid Option Selected!");
        }
    }
    //10.2
    // Displays the menu on the console
    void show() {

        System.out.println();

        for(int i=0;i<mOptions.length;i++)
            System.out.println(i+1+". "+mOptions[i]);
        System.out.print("Select options or 0 to exit: ");

    }
//    10.3
    // takes input from the user
    int getOption() {
        int option = -1;
        Scanner input = new Scanner(System.in);
        try {
            option = input.nextInt();
            if (option<0 && option> mOptions.length)
                throw new INVALID_OPTION_EXCEPTION();
        }
        catch (Exception e){
            System.out.print("Invalid Input! Enter Again: ");
            getOption();
        }

        return option;
    }
}
