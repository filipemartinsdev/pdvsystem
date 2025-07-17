package src.com.pdvsystem.db;

import src.com.pdvsystem.pdv.Role;

public interface UserRepository {
//    CREATE
    boolean saveUser(User user);


//    READ

    boolean userExists(int userId);

    User getUserById(int userId);

    User getUserByName(String username);

    boolean isUserOn(int userId);

    boolean userHasRole(int userId, Role role);

    boolean authUser(int userId, String password);


//    UPDATE

    void updateUserActive(int userId, boolean active);

    void updatePassword(int userId, String newPassword);

//    DELETE

    void deleteUser(int userId);
}
