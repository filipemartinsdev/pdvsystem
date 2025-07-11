package src.com.pdvsystem;

import java.util.Scanner;

public class App {
    private static boolean isOn;
    private static final Scanner scan = new Scanner(System.in);

    private static Session currentSession;

    static {
        ProductRepository productRepository = new ProductRepositoryImpl();

        isOn = false;

        int in;
        System.out.println("--- PDV ---");

        while(!isOn){
            System.out.print(">>> ");
            in = scan.nextInt();
            scan.nextLine();

            Product product = productRepository.getProductById(in);
            if (product == null) {
                System.out.println("Item not exists");
                continue;
            }
            currentSession = new Session(product);

            isOn = true;
        }
    }

    public static void init(){
        int input;

        while(isOn) {
            currentSession.printShopList();
            System.out.print(">> ");
            input = scan.nextInt();
            scan.nextLine();

            inputHandler(input);
            System.out.println();
        }

        System.out.println("> [GLOBAL] Session closed.");
    }

    private static void inputHandler(int input){
        if(input == 0){
            isOn = false;
            return;
        }

        currentSession.addItem(input);
    }
}
