package src.com.pdvsystem.db;

import java.util.List;

public interface ProductRepository {
    Product getProductByCode(long code);

    void sell(long productCode, float qtd);

    void sellAll(List<Product> list);
}