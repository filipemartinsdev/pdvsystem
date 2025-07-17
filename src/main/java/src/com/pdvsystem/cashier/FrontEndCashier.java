package src.com.pdvsystem.cashier;

import src.com.pdvsystem.db.*;
import src.com.pdvsystem.io.InputHandler;

import java.util.Scanner;

public final class FrontEndCashier {
    private static boolean isAppOn = true;
    private static boolean isSessionOn = false;

    public static Scanner scan = new Scanner(System.in);

    private static Session currentSession;

    public static void init(User user){
        UserRepository userRepository = new UserRepositoryImpl();

        if (userRepository.isUserOn(user.getId())){
            System.out.println("[ERROR] O usuário "+user.getName()+" está sendo utilizado.");
            return;
        }

        String input;
        Product product;

        while(FrontEndCashier.isAppOn) {
            while(!FrontEndCashier.isSessionOn && FrontEndCashier.isAppOn) {
                System.out.println("--- PDV ---");
                System.out.print(">>> ");
                input = FrontEndCashier.scan.nextLine();

                if (!InputHandler.strIsLong(input)) {
                    InputHandler.homeManager(input);
                    continue;
                }

                ProductRepository productRepository = new ProductRepositoryImpl();
                product = productRepository.getProductById(Long.parseLong(input));

                if (product == null) {
                    System.out.println("[ERROR] Item not exists");
                    continue;
                }

                FrontEndCashier.currentSession = new Session(product);
                FrontEndCashier.isSessionOn = true;
            }

            if (!FrontEndCashier.isSessionOn) return;

            FrontEndCashier.currentSession.updateSubtotal();
            FrontEndCashier.currentSession.printShopList();
            System.out.print(">> ");
            input = FrontEndCashier.scan.nextLine();

            InputHandler.manager(input, currentSession);
            System.out.println();
        }
    }

    public static void closeSession(){
        FrontEndCashier.isSessionOn = false;
        FrontEndCashier.currentSession = null;
    }

    public static void closeApp(){
        FrontEndCashier.isAppOn = false;
        scan.close();
        System.out.println("> [GLOBAL] Session closed.");
    }

    public static boolean isAppOn() {
        return FrontEndCashier.isAppOn;
    }

    public static boolean isSessionOn() {
        return isSessionOn;
    }
}