package src.com.pdvsystem;

import java.sql.*;

public class ProductRepositoryImpl implements ProductRepository{
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/pdv";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "020407";

    @Override
    public Product getProductById(int id) {
        String sql = "SELECT id, name, CAST(price AS NUMERIC), unity FROM products WHERE id = ?";
        try {
            Connection dbConnection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = dbConnection.prepareStatement(sql);
            statement.setInt(1, id);
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
