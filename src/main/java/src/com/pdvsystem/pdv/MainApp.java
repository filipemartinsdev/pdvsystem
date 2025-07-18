package src.com.pdvsystem.pdv;

import src.com.pdvsystem.db.User;
import src.com.pdvsystem.db.UserRepository;
import src.com.pdvsystem.db.UserRepositoryImpl;
import src.com.pdvsystem.io.InputManager;

public class MainApp {
    private static boolean isOn = true;
    private static User currentUser = null;

    public static void init(){
        while(MainApp.isOn){
            System.out.println("\n------ PDV ------");
            initLogin();

            LoggedApp.init(currentUser);
        }
    }

    private static void initLogin(){
        while(true){
            System.out.println("\n>>> LOGIN <<<");

            String username = InputManager.readString(">> Usuario: ");
            String password = InputManager.readString(">> Senha: ");

            UserRepository userRepository = new UserRepositoryImpl();
            User user = userRepository.getUserByName(username);

            if (LoggedApp.tryLogin(username, password) ){
                currentUser = user;
                break;
            }
        }
    }

    public static void exit(){
        InputManager.closeScanner();
        MainApp.isOn = false;


//        Login.logoutUser(MainApp.currentUser);
    }

    public static boolean isOn(){
        return MainApp.isOn;
    }
}