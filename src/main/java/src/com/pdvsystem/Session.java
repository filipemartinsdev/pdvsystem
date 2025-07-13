package src.com.pdvsystem;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Session {
    private final Map<Long, Product> shopMap;
    private final List<Long> shopList;

    private boolean isOpen;

    public Session(Product firstProduct){
        this.isOpen = true;

        this.shopList = new ArrayList<>();
        this.shopList.add(firstProduct.getId());

        this.shopMap = new HashMap<>();
        this.shopMap.put(firstProduct.getId(), firstProduct);
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

        System.out.print('+');
        for(int i = 0; i<89; i++){
            System.out.print('-');
        }
        System.out.print("+\n");
    }

    public void addItem(long itemId){
        ProductRepository productRepository = new ProductRepositoryImpl();
        Product product = productRepository.getProductById(itemId);

        if(product!=null){
            if(this.shopMap.containsKey(itemId)){
                product.setSortId(this.shopList.size()+1);
                this.shopMap.get(itemId).incrementUnitiesInOrder();
            }
            else {
                shopMap.put(itemId, product);
                this.shopList.add(itemId);
            }

            return;
        }
        System.out.println("[ERROR] Item not exists.");
    }

    private boolean removeItem(long itemId) {
        if (this.shopMap.containsKey(itemId)) {
            this.shopMap.remove(itemId);
            this.shopList.remove(itemId);
            return true;
        }
        return false;
    }

//    USER FUNCTIONS
    public void cancelItem(){
        System.out.print(">> Item #");
        int id = App.scan.nextInt();

        if (removeItem(shopList.get(id-1) ) ){
            System.out.println("Item #"+id+" has canceled.");
            return;
        }
        System.out.println("[ERROR] Item not canceled.");
    }

    public void finish(){
        this.isOpen = false;
        App.closeSession();
        System.out.println("[GLOBAL] Session finished.");
    }

//    GETTERS

    public boolean isOpen() {
        return isOpen;
    }
}
