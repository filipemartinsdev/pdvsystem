package src.com.pdvsystem.io;

import java.util.Scanner;

public class InputManager {
    private static Scanner scanner = null;

    public static String readString(String prompt){
        if (scanner==null){
            scanner = new Scanner(System.in);
        }

        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int readInt(String prompt){
        if (scanner==null){
            scanner = new Scanner(System.in);
        }

        System.out.print(prompt);
        int out = scanner.nextInt();
        scanner.nextLine();

        return out;
    }

    public static long readLong(String prompt){
        if (scanner==null){
            scanner = new Scanner(System.in);
        }

        System.out.print(prompt);
        long out = scanner.nextLong();
        scanner.nextLine();

        return out;
    }

    public static void closeScanner(){
        if (scanner!=null){
            scanner.close();
        }
    }
}
