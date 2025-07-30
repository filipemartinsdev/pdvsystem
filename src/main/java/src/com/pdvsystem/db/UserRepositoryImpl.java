package src.com.pdvsystem.db;

import src.com.pdvsystem.pdv.Role;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Array;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public boolean userHasRole(int userId, Role... roles) {
        String sql = "SELECT roles FROM users WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(
                    DatabaseInfo.getDbUrl(),
                    DatabaseInfo.getDbUser(),
                    DatabaseInfo.getDbPassword()
            );

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Array rolesArray = result.getArray("roles");
                Integer[] arr = (Integer[]) rolesArray.getArray();

                int rolesFoundCount = 0;

                for(Role r : roles){
                    for(int i : arr){
                        if (i == r.getID()) {
                            rolesFoundCount++;
                        }
                    }
                }

                if (rolesFoundCount == roles.length) {
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override // TODO: IMPLEMENT THIS
    public List<Role> getUserRoles(int userId) {
        return List.of();
    }

    @Override
    public boolean authenticateUser(int userId, String password) {
        String sql = "SELECT password FROM users WHERE code = ?;";
        boolean out = false;

        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            result.next();
            conn.close();

//            CHECK IF USER EXISTS
            if (result.next()){
                if (password.equals(result.getString(1))){
                    out = true;
                }
                else {
                    System.out.println("[ERROR] Senha incorreta");
                }
            }

            else {
                System.out.println("[ERROR] Usuário inexistente");
            }

            conn.close();
            return out;
        } catch (SQLException e) {
//            System.out.println("[ERRO DE CONEXÃO] Tente novamente mais tarde");
            throw new RuntimeException(e);
//            return false;
        }
    }

    @Override
    public boolean authenticateUser(String userName, String password) {
        String sql = "SELECT password FROM users WHERE name = ?;";
        boolean out = false;

        try {
            Connection conn = DriverManager.getConnection(DatabaseInfo.getDbUrl(), DatabaseInfo.getDbUser(), DatabaseInfo.getDbPassword());

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userName);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                if (password.equals(result.getString(1))) {
                    out = true;
                }

                else {
                    System.out.println("[ERROR] Senha incorreta");
                }
            }

            else {
                System.out.println("[ERROR] Usuário inexistente");
            }

            conn.close();
            return out;
        } catch (SQLException e) {
//            System.out.println("[ERRO DE CONEXÃO] Tente novamente mais tarde");
            throw new RuntimeException(e);
//            return false;
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

            Array arraySql = result.getArray("roles");
            Integer[] roles = (Integer[]) arraySql.getArray();
            List<Role> roleList = new ArrayList<>();

            for (int roleId : roles) {
                Role r = Role.getRole(roleId);
                if (r!=null) {
                    roleList.add(Role.getRole(roleId));
                }
            }

            User user = new User(
                    result.getString(2),
                    result.getInt(1),
                    roleList
            );

            conn.close();
            return user;

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
                System.out.println("[ERROR] User not exists.");
                conn.close();

                return null;
            }

            Array arraySql = result.getArray("roles");
            Integer[] roles = (Integer[]) arraySql.getArray();
            List<Role> roleList = new ArrayList<>();

            for (int roleId : roles) {
                Role r = Role.getRole(roleId);
                if (r!=null) {
                    roleList.add(Role.getRole(roleId));
                }
            }

            User user = new User(
                    result.getString(2),
                    result.getInt(1),
                    roleList
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
