package src.com.pdvsystem.pdv;

import src.com.pdvsystem.db.User;
import src.com.pdvsystem.db.UserRepository;
import src.com.pdvsystem.db.UserRepositoryImpl;
import src.com.pdvsystem.io.InputManager;

public class App {
    private static boolean isOn = true;
    private static User currentUser = null;

    public static void init(){
        while(App.isOn){
            System.out.println("------ PDV ------\n");
            initLogin();

            LoggedApp.init(currentUser);
        }
    }

    private static void initLogin(){
        while(true){
            System.out.println(">>> LOGIN <<<");

            String username = InputManager.readString(">> Usuario: ");
            String password = InputManager.readString(">> Senha: ");

            UserRepository userRepository = new UserRepositoryImpl();
            User user = userRepository.getUserByName(username);

            if(user==null){
                System.out.println("[ERROR] Usuario inexistente.");
            }
            else if(!userRepository.authUser(user.getId(), password)){
                System.out.println("[ERROR] Senha incorreta.");
            }
            else {
                currentUser = user;
                break;
            }
        }
    }

    public static void exit(){
        InputManager.closeScanner();
        App.isOn = false;


//        Login.logoutUser(App.currentUser);
    }

    public static boolean isOn(){
        return App.isOn;
    }
}