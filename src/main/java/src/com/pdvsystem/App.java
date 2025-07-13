package src.com.pdvsystem;

import java.util.Scanner;

public final class App {
    private static boolean isAppOn = true;
    private static boolean isSessionOn = false;

    public static Scanner scan = new Scanner(System.in);

    private static Session currentSession;

    public static void closeSession(){
        App.isSessionOn = false;
        App.currentSession = null;
    }

    public static void init(){
        String input;

        Product product;
        while(App.isAppOn) {
            while(!App.isSessionOn && App.isAppOn) {
                System.out.println("--- PDV ---");
                System.out.print(">>> ");
                input = App.scan.nextLine();

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

                App.currentSession = new Session(product);
                App.isSessionOn = true;
            }

            if (!App.isSessionOn) return;

            App.currentSession.printShopList();
            System.out.print(">> ");
            input = App.scan.nextLine();

            InputHandler.manager(input, currentSession);
            System.out.println();
        }
    }

    public static void closeApp(){
        App.isAppOn = false;
        scan.close();
        System.out.println("> [GLOBAL] Session closed.");
    }

    public static boolean isAppOn() {
        return App.isAppOn;
    }

    public static boolean isSessionOn() {
        return isSessionOn;
    }
}