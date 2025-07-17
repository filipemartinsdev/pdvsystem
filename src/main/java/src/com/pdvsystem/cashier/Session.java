package src.com.pdvsystem.cashier;

import src.com.pdvsystem.db.Product;
import src.com.pdvsystem.db.ProductRepository;
import src.com.pdvsystem.db.ProductRepositoryImpl;
import src.com.pdvsystem.io.InputHandler;
import src.com.pdvsystem.io.MaxLengthPrinter;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Session {
    private final Map<Long, Product> shopMap;
    private final List<Long> shopList;

    private Product lastProduct;

    private float subtotal;

    private boolean isOpen;

    public Session(Product firstProduct){
        this.isOpen = true;

        this.shopList = new ArrayList<>();
        this.shopList.add(firstProduct.getId());

        this.shopMap = new HashMap<>();
        this.shopMap.put(firstProduct.getId(), firstProduct);

        this.lastProduct = firstProduct;
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
                    product.getId(),
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

    public void addItem(long itemId){
        ProductRepository productRepository = new ProductRepositoryImpl();
        Product product = productRepository.getProductById(itemId);

        if(product!=null){
            if(this.shopMap.containsKey(itemId)){
                product.setSortId(this.shopList.size()+1);
                this.shopMap.get(itemId).incrementUnitiesInOrder();
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

    public boolean isOpen() {
        return isOpen;
    }

    public Product getLastProduct(){
        return this.lastProduct;
    }

//    USER FUNCTION
    public void cancelItem(){
        if (this.shopList.size() == 1){
            System.out.println("[ERROR] Can't set empty list.");
            return;
        }

        System.out.print(">> Item #");
        int id = FrontEndCashier.scan.nextInt();

        if (removeItem(shopList.get(id-1) ) ){
            System.out.println("Item #"+id+" has canceled.");
            this.updateSubtotal();
            return;
        }
        System.out.println("[ERROR] Item not canceled.");
    }

    public boolean finish(){
        System.out.println();

        Payment.printOptions();
        System.out.print(" >> ");
        String input = FrontEndCashier.scan.nextLine();

        if(!InputHandler.strIsLong(input)){
            System.out.println("[ERROR] Invalid option");
            return false;
        }

        Payment.manager(Integer.parseInt(input));

        this.isOpen = false;
        FrontEndCashier.closeSession();
        System.out.println("[GLOBAL] Session finished.");
        return true;
    }

    public void updateSubtotal(){
        this.subtotal = 0;
        for(Long productId : this.shopList){
            subtotal+=this.shopMap.get(productId).getPrice() * this.shopMap.get(productId).getUnitiesInOrder();
        }
    }
}
