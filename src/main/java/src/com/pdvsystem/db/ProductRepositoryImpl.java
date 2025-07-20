package src.com.pdvsystem.db;

import java.sql.*;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository{


    @Override
    public Product getProductById(long id) {
        String sql = "SELECT id, name, CAST(price AS NUMERIC), unity FROM products WHERE id = ?";
        try {
            Connection dbConnection = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = dbConnection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                return new Product(id, result.getString(2), result.getFloat(3), result.getString(4));
            }

            dbConnection.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] Erro de conexão.");
//            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void sell(long productId, float qtd) {
        String sql = "UPDATE products SET qtd_inventory = qnt_inventory - ? WHERE id = ?;";
        try {
            Connection dbConnection = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = dbConnection.prepareStatement(sql);
            statement.setFloat(1, qtd);
            statement.setLong(2, productId);
            statement.executeUpdate();

            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sellAll(List<Product> list){
        String sqlUpdate = "UPDATE products SET qtd_inventory = qtd_inventory - ? WHERE id = ?;";
        try {
            Connection dbConnection = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            for (Product product : list){
                PreparedStatement statement = dbConnection.prepareStatement(sqlUpdate);
                statement.setFloat(1, product.getUnitiesInOrder());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
