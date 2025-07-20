package src.com.pdvsystem.db;

import java.util.List;

public interface ProductRepository {
    Product getProductById(long id);

    void sell(long productId, float qtd);

    void sellAll(List<Product> list);
}