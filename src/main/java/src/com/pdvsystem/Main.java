package src.com.pdvsystem;

import src.com.pdvsystem.pdv.App;
import src.com.pdvsystem.io.InputManager;

import src.com.pdvsystem.pdv.Role;

public class Main {
    public static void main(String[] args) {

        src.com.pdvsystem.pdv.App.init();

        InputManager.closeScanner();
    }
}