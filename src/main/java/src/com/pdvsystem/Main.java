package src.com.pdvsystem;

import src.com.pdvsystem.db.UserRepository;
import src.com.pdvsystem.db.UserRepositoryImpl;
import src.com.pdvsystem.pdv.MainApp;
import src.com.pdvsystem.io.InputManager;

public class Main {
    public static void main(String[] args) {
        MainApp.init();


        InputManager.closeScanner();
    }
}