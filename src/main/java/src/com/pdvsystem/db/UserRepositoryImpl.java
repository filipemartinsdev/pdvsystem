package src.com.pdvsystem.db;

import src.com.pdvsystem.pdv.Role;
import src.com.pdvsystem.pdv.util.TypeCasting;

import java.sql.*;
import java.util.Arrays;

public class UserRepositoryImpl implements UserRepository {

//    CREATE

    @Override // TODO: IMPLEMENT THIS
    public boolean saveUser(User user) {
        return false;
    }


//    READ

    @Override
    public boolean userExists(int userId) {
        String sql = "SELECT id FROM users WHERE id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);

            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isUserOn(int userId) {
        if(!userExists(userId)){
            System.out.println("[ERROR] User not exists.");
            return false;
        }

        String sql = "SELECT active FROM users WHERE id = ?;";

        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            result.next();
            conn.close();

            return result.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // TODO: IMPLEMENT THIS
    public boolean userHasRole(int userId, Role role) {
        return false;
    }

    @Override
    public boolean authUser(int userId, String password) {
        if(!userExists(userId)){
            System.out.println("[ERROR] User not exists.");
            return false;
        }

        String sql = "SELECT password FROM users WHERE id = ?;";

        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            result.next();
            conn.close();

            return password.equals(result.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?;";

        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();


//            CHECK IF USER EXISTS
            if(!result.next()){
//                System.out.println("[ERROR] User not exists.");
                conn.close();

                return null;
            }

            Array arraySql = result.getArray(4);
            int[] roles = (int[]) arraySql.getArray();

            User user = new User(
                    result.getString(2),
                    result.getInt(1),
                    roles
            );

            conn.close();
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByName(String username) {
        String sql = "SELECT * FROM users WHERE name = ?;";

        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

//            CHECK IF USER EXISTS
            if(!result.next()){
                System.out.println("[ERROR] User not exists.");
                conn.close();

                return null;
            }

            Array arraySql = result.getArray(4);

            int[] roles;

            Integer[] integerArray = (Integer[]) arraySql.getArray();
            roles = TypeCasting.toIntArray(integerArray);

            User user = new User(
                    result.getString(2),
                    result.getInt(1),
                    roles
            );

            conn.close();
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    UPDATE

    @Override
    public void updateUserActive(int userId, boolean active) {
        if(!userExists(userId)){
            System.out.println("[ERROR] User not exists.");
            return;
        }

        String sql = "UPDATE users SET active = ? WHERE id = ?;";

        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBoolean(1, active);
            statement.setInt(2, userId);

            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePassword(int userId, String newPassword) {
        if(!userExists(userId)){
            System.out.println("[ERROR] User not exists.");
            return;
        }

        String sql = "UPDATE users SET password = ? WHERE id = ?;";

        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setInt(2, userId);

            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    DELETE

    @Override
    public void deleteUser(int userId) {
        if(!userExists(userId)){
            System.out.println("[ERROR] User not exists.");
            return;
        }

        String sql = "DELETE users WHERE id = ?;";

        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);

            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
