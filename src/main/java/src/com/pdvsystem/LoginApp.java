package src.com.pdvsystem;

import src.com.pdvsystem.db.User;
import src.com.pdvsystem.db.UserRepository;
import src.com.pdvsystem.db.UserRepositoryImpl;
import src.com.pdvsystem.io.InputManager;

public class LoginApp {
    public static void run(){
        System.out.println("\n>>>>>>> PDV Login <<<<<<");
        String userName = InputManager.readString(">> Usuário: ");
        String userPassword = InputManager.readString(">> Senha: ");

        UserRepository userRepository = new UserRepositoryImpl();

        if(userRepository.authenticateUser(userName, userPassword)){
            User currentUser = userRepository.getUserByName(userName);

            MainApp.setLoggedUser(currentUser);
            MainApp.setLogged(true);
        }
    }
}
