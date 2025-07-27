package src.com.pdvsystem;

import src.com.pdvsystem.db.User;
import src.com.pdvsystem.db.UserRepository;
import src.com.pdvsystem.db.UserRepositoryImpl;
import src.com.pdvsystem.io.InputManager;
import src.com.pdvsystem.pdv.PdvApp;
import src.com.pdvsystem.pdv.Role;

public class MainApp {
    public static void main(String[] args) {
        MainApp.run();
    }

    private static boolean isLogged = false;
    private static boolean wantToExit = false;

    private static User loggedUser;

    private static void run() {
        while(!isLogged && !wantToExit){
            System.out.println("\n>>>>>> HOME <<<<<<\n");

            System.out.println("[1] Login");
            System.out.println("[2] Sair");

            String input = InputManager.readString(">> ");
            if(input.equals("1")){
                LoginApp.run();

                if (isLogged) {
                    PdvApp.run();
                }
                continue;
            }

            else if(input.equals("2")){
                MainApp.wantToExit = true;
                return;
            }

            else {
                System.out.println("[ERROR] Opção inválida.");
                continue;
            }
        }

        InputManager.closeScanner();
    }


    public static User getLoggedUser(){
        return MainApp.loggedUser;
    }


    public static void setLogged(boolean value){
        MainApp.isLogged = value;
    }

    public static void setWantToExit(boolean value){
        MainApp.wantToExit = value;
    }

    public static void setLoggedUser(User user) {
        MainApp.loggedUser = user;
    }


    public static void closeSession(){
        MainApp.loggedUser = null;
        MainApp.isLogged = false;
    }
}