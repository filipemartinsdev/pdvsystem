package src.com.pdvsystem;

public class Product {
    private final String name;
    private final int id;
    private final float price;
    private final String unity;

    private float unitiesInOrder;

    public Product(int id, String name, float price, String unity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.unity = unity;

        this.unitiesInOrder = 1;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public float getUnitiesInOrder() {
        return unitiesInOrder;
    }

//    public void incrementUnitiesInOrder(int unities){
//        this.unitiesInOrder+=unities;
//    }

    public void incrementUnitiesInOrder(){
        this.unitiesInOrder++;
    }

    public float getPrice() {
        return price;
    }

    public String getUnity() {
        return unity;
    }
}
