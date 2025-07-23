package src.com.pdvsystem;

import src.com.pdvsystem.pdv.MainApp;
import src.com.pdvsystem.io.InputManager;

import src.com.pdvsystem.cashier.IOManager;

public class Main {
    public static void main(String[] args) {
        MainApp.init();

        InputManager.closeScanner();
    }
}