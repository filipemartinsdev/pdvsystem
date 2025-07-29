package src.com.pdvsystem.cashier;

import src.com.pdvsystem.io.InputManager;

import javax.imageio.plugins.tiff.TIFFImageReadParam;

public final class CashierApp {
    private static boolean isAppOn;
    private static boolean isSessionOn;

    private static Session currentSession;


    public static void run() {
        String input;
//        Product product;

        CashierApp.isAppOn = true;
        CashierApp.isSessionOn = false;

        while (CashierApp.isAppOn) {
            while (!CashierApp.isSessionOn) {
                System.out.println("\n>>>>>> CASHIER <<<<<<");
                input = InputManager.readString(">>> ");

                IOManager.inputManager(input);

                if (!CashierApp.isAppOn){
                    return;
                }
            }


            CashierApp.currentSession.printShopList();

            input = InputManager.readString(">> ");
            IOManager.inputManager(input);
        }
    }

//    Create new Shop Session
    public static void initNewSession(){
        CashierApp.isSessionOn = true;
        CashierApp.currentSession = new Session();
    }

//    Close the current Shop Session
    public static void closeSession(){
        CashierApp.isSessionOn = false;
        CashierApp.currentSession = null;
    }

//    Exit CashierApp
    public static void closeApp(){
        CashierApp.isAppOn = false;
        System.out.println("> [GLOBAL] Session closed.");
    }


//    GETTERS

    public static boolean isSessionOn() {
        return isSessionOn;
    }

    public static Session getCurrentSession(){


        return currentSession;
    }
}