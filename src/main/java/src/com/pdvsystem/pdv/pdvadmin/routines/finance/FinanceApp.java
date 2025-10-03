package src.com.pdvsystem.pdv.pdvadmin.routines.finance;

import src.com.pdvsystem.io.InputManager;
import src.com.pdvsystem.pdv.pdvadmin.routines.RoutineApp;

public class FinanceApp implements RoutineApp {
    private static boolean wantToExit = false;

    @Override
    public void run() {
        System.out.println("\n>>>>>> Finance Control <<<<<<\n");

        while (!wantToExit){
            printHome();
            InputManager.readString("> ");
        }
    }

    @Override
    public void close() {
        wantToExit = false;
    }

    private static void printHome(){}
}
