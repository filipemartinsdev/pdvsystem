package src.com.pdvsystem.cashier;

import src.com.pdvsystem.db.*;
import src.com.pdvsystem.io.InputHandler;
import src.com.pdvsystem.io.InputManager;

import java.util.Scanner;

public final class FrontEndCashier {
    private static boolean isAppOn = true;
    private static boolean isSessionOn = false;

    public static Scanner scan = new Scanner(System.in);

    private static Session currentSession;

    public static void init(User user){
        String input;
        Product product;

        while(FrontEndCashier.isAppOn) {
            while(!FrontEndCashier.isSessionOn && FrontEndCashier.isAppOn) {
                System.out.println(">>>>>> CASHIER <<<<<<");
                System.out.print(">>> ");
                input = FrontEndCashier.scan.nextLine();

//                check if INPUT != productCode
                if (!InputHandler.strIsLong(input)) {
                    IOManager.inputManager(input);
                    continue;
                }

//                Connect Database
                ProductRepository productRepository = new ProductRepositoryImpl();
                product = productRepository.getProductById(Long.parseLong(input));

//                check IF product not exists
                if (product == null) {
                    System.out.println("[ERROR] Item not exists");
                    continue;
                }

//                Init Session
                FrontEndCashier.initNewSession();
                FrontEndCashier.currentSession.addItem(product.getId(), product.getUnitiesInOrder());
            }
            if (!FrontEndCashier.isSessionOn) return;


            FrontEndCashier.currentSession.printShopList();

//            Get AND Process userInput
            input = InputManager.readString(">> ");
            IOManager.inputManager(input);

            System.out.println();
        }
    }

    public static void initNewSession(){
        FrontEndCashier.isSessionOn = true;
        FrontEndCashier.currentSession = new Session();
    }

    public static void closeSession(){
        FrontEndCashier.isSessionOn = false;
        FrontEndCashier.currentSession = null;
    }

    public static void closeApp(){
        FrontEndCashier.isAppOn = false;
        System.out.println("> [GLOBAL] Session closed.");
    }

    public static boolean isAppOn() {
        return FrontEndCashier.isAppOn;
    }

    public static boolean isSessionOn() {
        return isSessionOn;
    }

    public static Session getCurrentSession(){
        return currentSession;
    }
}