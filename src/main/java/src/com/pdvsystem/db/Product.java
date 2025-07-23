package src.com.pdvsystem.db;

public class Product {
    private final String name;
    private final long id;
    private final float price;
    private final String unity;
    private int sortId;

    private float unitiesInOrder;

    public Product(long id, String name, float price, String unity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.unity = unity;

        this.unitiesInOrder = 1;
    }

    public String getName() {
        return this.name;
    }

    public long getId() {
        return id;
    }

    public float getUnitiesInOrder() {
        return unitiesInOrder;
    }

    public void incrementUnitiesInOrder(float qtd){
        this.unitiesInOrder+=qtd;
    }

    public void setUnitiesInOrder(float qtd){
        this.unitiesInOrder=qtd;
    }

    public float getPrice() {
        return price;
    }

    public String getUnity() {
        return unity;
    }

    public void setSortId(int id){
        this.sortId = id;
    }
}
