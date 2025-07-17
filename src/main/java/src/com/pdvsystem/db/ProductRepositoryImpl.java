package src.com.pdvsystem.db;

import java.sql.*;
import src.com.pdvsystem.db.DatabaseInfo;

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
            throw new RuntimeException(e);
        }
        return null;
    }
}
