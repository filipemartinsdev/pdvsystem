package src.com.pdvsystem.cashier;

import src.com.pdvsystem.db.Product;
import src.com.pdvsystem.db.ProductRepository;
import src.com.pdvsystem.db.ProductRepositoryImpl;
import src.com.pdvsystem.io.InputHandler;

public class PriceChecker {
    public static void init(){
        System.out.println();
        System.out.println("--- Consulta Preço ---");
        System.out.println(" Tecle ENTER para sair");

        while(true){
            System.out.println();
            System.out.print(">>> ");
            String input = FrontEndCashier.scan.nextLine();

            if(input.isBlank()){
                return;
            }

            if(!InputHandler.strIsLong(input)){
                System.out.println("[ERROR] Invalid code");
                continue;
            }

            ProductRepository productRepository = new ProductRepositoryImpl();
            Product product = productRepository.getProductById(Long.parseLong(input));

            if (product==null) {
                System.out.println("[ERROR] Produto não cadastrado");
                continue;
            }

            System.out.printf(
                    "%s --- R$%.2f %s\n",
                    product.getName(),
                    product.getPrice(),
                    product.getUnity()
            );
        }
    }
}
