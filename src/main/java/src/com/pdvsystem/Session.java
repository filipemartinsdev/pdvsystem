package src.com.pdvsystem;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private final Map<Long, Product> shopMap;
    private int shopListSize;
    private long lastItemId;

    private ProductRepository productRepository;

    public Session(Product firstProduct){
        productRepository = new ProductRepositoryImpl();

        this.shopMap = new HashMap<>();
        this.shopMap.put(firstProduct.getId(), firstProduct);
    }

    public void printShopList(){
//        int i = 1;
//        for (long itemId:this.shopMap.keySet()){
//            System.out.printf("#%d  %-15d %d uni.\n", i, itemId, this.shopMap.get(itemId));
//            i++;
//        }

        MaxLengthPrinter maxPrinter = new MaxLengthPrinter(20);

        System.out.print('+');
        for(int i = 0; i<89; i++){
            System.out.print('-');
        }
        System.out.print("+\n");

        System.out.printf(
                "| %-5s | %-15s | %-20s | %12s | %8s | %12s |\n",
                "", "codigo", "nome", "preco", "qnt", "total"
        );

        System.out.print('+');
        for(int i = 0; i<89; i++){
            System.out.print('-');
        }
        System.out.print("+\n");

        int idCount = 1;
        for (long itemId:this.shopMap.keySet()){
            Product currentProduct = this.shopMap.get(itemId);

//            #ID / BAR CODE / NAME / PRICE / UNITIES / TOTAL
            System.out.printf(
                    currentProduct.getUnity().equals("kg")?
                            "| #%-4d | %-15d | %-20s | R$%10.2f | %5.3f %s | R$%10.2f |\n":
                            "| #%-4d | %-15d | %-20s | R$%10.2f | %4.0f %s | R$%10.2f |\n",
                    idCount, itemId, maxPrinter.get(currentProduct.getName()), currentProduct.getPrice(), currentProduct.getUnitiesInOrder(), currentProduct.getUnity(), currentProduct.getUnitiesInOrder()*currentProduct.getPrice()
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
                shopMap.get(itemId).incrementUnitiesInOrder();
            }
            else {
                shopMap.put(itemId, product);
            }

            this.lastItemId = itemId;
            shopListSize++;
            return;
        }
        System.out.println("[ERROR] Item not exists.");
    }

    public boolean removeItem(long itemId) {
        if (this.shopMap.containsKey(itemId)) {
            this.shopMap.remove(itemId);
            return true;
        }
        return false;
    }
}
