package src.com.pdvsystem.pdv.pdvadmin;

import src.com.pdvsystem.io.InputManager;
import src.com.pdvsystem.pdv.pdvadmin.routines.Routine;

public class PdvAdminApp {
    private static boolean wantToExit;

    public static void run(){
        PdvAdminApp.wantToExit = false;

        while(!wantToExit){
            System.out.println("\n>>>>>> PDV ADMIN <<<<<<");
            System.out.println("> Tecle ENTER para sair");

            printHomeOptions();
            String input = InputManager.readString("  >> ");
            homeInputHandler(input);
        }
    }

    private static void printHomeOptions(){
        System.out.println();
        for (Routine r : Routine.values()){
            System.out.printf("%-10d%s\n", r.getId(), r.name());
        }
        System.out.println();
    }

    private static void homeInputHandler(String input){
        if (input.isBlank()){
            PdvAdminApp.wantToExit = true;
            return;
        }
    }
}
