package src.com.pdvsystem.cashier;

import src.com.pdvsystem.db.Product;
import src.com.pdvsystem.db.ProductRepository;
import src.com.pdvsystem.db.ProductRepositoryImpl;
import src.com.pdvsystem.io.InputHandler;
import src.com.pdvsystem.io.InputManager;
import src.com.pdvsystem.io.MaxLengthPrinter;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Session {
//    Map<Long ProductID, OBJ Product>
    private final Map<Long, Product> shopMap;

//    List<Long ProductID>
    private final List<Long> shopList;

    private Product lastProduct;

    private float subtotal;

    public Session(){
        this.shopList = new ArrayList<>();
        this.shopMap = new HashMap<>();
    }


    public void addItem(long itemId, float qtd){
        ProductRepository productRepository = new ProductRepositoryImpl();
        Product product = productRepository.getProductByCode(itemId);

        if(product!=null){
            product.setUnitiesInOrder(qtd);

//            Check IF the product is in the shopList
            if(this.shopMap.containsKey(itemId)){
                product.setSortId(this.shopList.size()+1);

                this.shopMap.get(itemId).incrementUnitiesInOrder(qtd);
                this.lastProduct = product;
            }

            else {
                shopMap.put(itemId, product);
                this.shopList.add(itemId);
                this.lastProduct = product;
            }

            updateSubtotal();
            return;
        }
        System.out.println("[ERROR] Item not exists.");
    }

    public Product getLastProduct(){
        return this.lastProduct;
    }

    public void updateSubtotal(){
        this.subtotal = 0;
        for(Long productId : this.shopList){
            subtotal+=this.shopMap.get(productId).getPrice() * this.shopMap.get(productId).getUnitiesInOrder();
        }
    }

    public void cancelItem(){
        if (this.shopList.size() == 1){
            System.out.println("[ERROR] Can't set empty list.");
            return;
        }

        String id = InputManager.readString(">> Item #");
        if (!InputHandler.strIsLong(id)){
            System.out.println("Opção inválida.");
        }

        int idInt = Integer.parseInt(id);

        if(idInt <= 0 || idInt > this.shopList.size()){
            System.out.println("Opção inválida.");
        }

        if (removeItem(shopList.get(idInt-1) ) ){
            System.out.println("Item #"+idInt+" has canceled.");
            this.updateSubtotal();
            return;
        }
        System.out.println("[ERROR] Item not canceled.");
    }

    private boolean removeItem(long itemId) {
        if (this.shopMap.containsKey(itemId)) {
            this.shopMap.remove(itemId);
            this.shopList.remove(itemId);
            updateSubtotal();
            return true;
        }
        updateSubtotal();
        return false;
    }


    public boolean finish(){
        System.out.println();

        Payment payment = new Payment(this.subtotal);
        payment.run();

        if (payment.isComplete()){
            System.out.println("AGUARDE...");

            ProductRepository productRepository = new ProductRepositoryImpl();
//            for (long productId : this.shopList){
//                productRepository.sell(productId, shopMap.get(productId).getUnitiesInOrder());
//            }
            List<Product> productList = new ArrayList<>();

            for (Product product:this.shopMap.values()){
                productList.add(product);
            }

            productRepository.sellAll(productList);

            System.out.println(">> Sessão finalizada <<\n");
            return true;
        }
        return false;
    }


    public void printShopList(){
        MaxLengthPrinter maxPrinter = new MaxLengthPrinter(20);

        System.out.print('+');
        for(int i = 0; i<89; i++){
            System.out.print('-');
        }
        System.out.print("+\n");

        System.out.printf(
                "| %-5s | %-15s | %-20s | %12s | %8s | %12s |\n",
                "", "codigo", "nome", "preco", "qtd", "total"
        );

        System.out.print('+');
        for(int i = 0; i<89; i++){
            System.out.print('-');
        }
        System.out.print("+\n");

        int idCount = 1;
        for (long productId:this.shopList){
            Product product = this.shopMap.get(productId);

//            #ID / BAR CODE / NAME / PRICE / UNITIES / TOTAL
            System.out.printf(
                    product.getUnity().equals("kg")?
                            "| #%-4d | %-15d | %-20s | R$%10.2f | %5.3f %s | R$%10.2f |\n": // print for KG price
                            "| #%-4d | %-15d | %-20s | R$%10.2f | %4.0f %s | R$%10.2f |\n", // print for UNI price
                    idCount,
                    product.getCode(),
                    maxPrinter.get(product.getName()),
                    product.getPrice(),
                    product.getUnitiesInOrder(),
                    product.getUnity(),
                    product.getUnitiesInOrder()*product.getPrice()
            );
            idCount++;
        }

//        PRINT LAST ITEM
        System.out.print('+');
        for(int i = 0; i<89; i++){
            System.out.print('-');
        }
        System.out.print("+\n");


        System.out.printf(
                this.lastProduct.getUnity().equals("kg")?
                        "| %-20s R$%-10.2f %5.3f %-3s |\n":
                        "| %-20s R$%-10.2f %5.0f %-3s |\n",
                maxPrinter.get(this.lastProduct.getName()),
                this.lastProduct.getPrice(),
                this.lastProduct.getUnitiesInOrder(),
                this.lastProduct.getUnity()
        );
        System.out.println("+---------------------------------------------+");


//        PRINT SUBTOTAL


        System.out.printf("| SUBTOTAL = R$%-10.2f |\n", this.subtotal);
        System.out.println("+-------------------------+");
    }
}