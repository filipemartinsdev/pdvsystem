package src.com.pdvsystem.pdv;

import src.com.pdvsystem.MainApp;
import src.com.pdvsystem.cashier.CashierApp;
import src.com.pdvsystem.io.InputManager;
import src.com.pdvsystem.pdv.pdvadmin.PdvAdminApp;

public class PdvApp {
    private static boolean wantToExit = false;

    public static void run(){
        while(!wantToExit){
            System.out.println("\n>>>>>> PDV <<<<<<\n");
            printHomeOptions();

            String input = InputManager.readString("  >> ");
            homeInputHandler(input);
        }

        MainApp.closeSession();
    }

    private static void printHomeOptions(){
        System.out.println("    [1] Abrir PDV Frente de Caixa");
        System.out.println("    [2] Acessar PDV Admin");
        System.out.println("    [3] Sair");
    }

    private static void homeInputHandler(String input){
//        ACCESS PDV CASHIER
        if(input.equals("1")){
            if( MainApp.getLoggedUser().getRoles().contains(Role.CASHIER_OPERATOR) ) {
                CashierApp.run();
                return;
            }

            else {
                System.out.println("[ERROR] Usuário sem permissão");
            }
        }

//        ACCESS PDV ADMIN
        if(input.equals("2")){
            if ( MainApp.getLoggedUser().getRoles().contains(Role.PDV_OPERATOR) ) {
                PdvAdminApp.run();
                return;
            }

            else {
                System.out.println("[ERROR] Usuário sem permissão");
            }
        }

//        EXIT
        if (input.equals("3")){
            PdvApp.wantToExit = true;
            return;
        }

        System.out.println("[ERROR] Opção inválida.");
    }
}
