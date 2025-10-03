package src.com.pdvsystem.pdv.pdvadmin.routines.inventorycontrol;

import src.com.pdvsystem.io.InputManager;
import src.com.pdvsystem.pdv.pdvadmin.routines.RoutineApp;

public class InventoryControlApp implements RoutineApp {
    private static boolean wantToExit = false;

    @Override
    public void run(){
        System.out.println("\n>>>>>> Inventory Control <<<<<<\n");

        while (!wantToExit){
            printHome();
            InputManager.readString("> ");
        }
    }

    @Override
    public void close() {
        wantToExit = false;
    }

    private static void printHome(){
        System.out.println(); 
    }
}
