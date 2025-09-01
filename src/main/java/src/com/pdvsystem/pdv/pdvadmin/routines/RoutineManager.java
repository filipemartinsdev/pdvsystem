package src.com.pdvsystem.pdv.pdvadmin.routines;

import src.com.pdvsystem.pdv.pdvadmin.routines.finance.FinanceApp;
import src.com.pdvsystem.pdv.pdvadmin.routines.inventorycontrol.InventoryControlApp;

public class RoutineManager {
    public static void runRoutine(Routine routine){
            switch (routine){
            case INVENTORY_CONTROL:
                new InventoryControlApp().run();
                break;
            case FINANCE:
                new FinanceApp().run();
                break;
            default:
                System.out.println("[ERROR] Rotina inexistente");
                break;
        }
    }
}