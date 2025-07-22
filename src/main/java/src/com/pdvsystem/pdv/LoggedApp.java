package src.com.pdvsystem.pdv;

import java.util.Arrays;
import java.util.List;
import src.com.pdvsystem.db.User;
import src.com.pdvsystem.cashier.FrontEndCashier;
import src.com.pdvsystem.db.UserRepository;
import src.com.pdvsystem.db.UserRepositoryImpl;
import src.com.pdvsystem.io.InputHandler;
import src.com.pdvsystem.io.InputManager;
import src.com.pdvsystem.pdv.routines.Routine;

public class LoggedApp {
    private static boolean isOn = false;
    private static User loggedUser = null;

    public static void init(User user){
        loggedUser = user;
        LoggedApp.isOn = true;
        List<Role> loggedUserRoles = user.getRoles();

        System.out.println("------ PDV HOME ------\n");
        System.out.println("> Bem vindo!");

        if(
                loggedUserRoles.contains(Role.PDV_TRAINEE) ||
                loggedUserRoles.contains(Role.PDV_OPERATOR) ||
                loggedUserRoles.contains(Role.TECHNIC) ||
                loggedUserRoles.contains(Role.ADMIN)
        ) {
            LoggedApp.initPDV();
        }

        else if (
                loggedUserRoles.contains(Role.CASHIER_TRAINEE) ||
                loggedUserRoles.contains(Role.CASHIER_OPERATOR)
        ) {
            FrontEndCashier.init(LoggedApp.loggedUser);
        }

        LoggedApp.logout();
    }

    private static void initPDV() {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.updateUserActive(LoggedApp.loggedUser.getId(), true);

        while(LoggedApp.isOn){
            if (
                    loggedUser.getRoles().contains(Role.PDV_OPERATOR) ||
                    loggedUser.getRoles().contains(Role.PDV_TRAINEE)
            ) {
                System.out.println();
                System.out.println("    [1] Acessar PDV");
                System.out.println("    [2] Abrir frente de caixa");

                String input = InputManager.readString("   > ");
                int inputInt;
                if (InputHandler.strIsLong(input)){
                    inputInt = Integer.parseInt(input);
                } else {
                    System.out.println("[ERROR] Opção inválida.");
                    continue;
                }

                if (inputInt == 2){
                    FrontEndCashier.init(loggedUser);
                    continue;
                } else if (inputInt != 1) {
                    System.out.println("[ERROR] Opção inválida.");
                    continue;
                }
            }

            while (true){
                System.out.println("\n>>>>>>> ROTINAS <<<<<<<");
                System.out.println("> Tecle ENTER para sair\n");
                for (Routine routine : Routine.values()){
                    System.out.println("["+routine.getId()+"] "+routine);
                }
                String input = InputManager.readString("   > ");

                if (input.isBlank()){
                    LoggedApp.isOn = false;
                    break;
                }

                int in;
                if (InputHandler.strIsLong(input)){
                    in = Integer.parseInt(input);
                } else {
                    System.out.println("[ERROR] Opção inválida");
                    continue;
                }


            }
        }
    }

    private static void initRoutine(Routine routine){
        if(!Arrays.asList(Routine.values()).contains(routine)){
            System.out.println("[ERROR] Rotina inválida.");
        };
    }

    private static void logout(){
        LoggedApp.isOn = false;

        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.updateUserActive(LoggedApp.loggedUser.getId(), false);
    }

    public static boolean tryLogin(String userName, String userPassword){
        UserRepository userRepository = new UserRepositoryImpl();
        User user = userRepository.getUserByName(userName);

        if(user==null){
            System.out.println("[ERROR] Usuario inexistente.");
            return false;
        }
        else if(!userRepository.authUser(user.getId(), userPassword)){
            System.out.println("[ERROR] Senha incorreta.");
            return false;
        }
        else if(userRepository.isUserOn(user.getId())){
            System.out.println("[ERROR] Usuario esta sendo utilizado.");
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isOn() {
        return isOn;
    }
}
