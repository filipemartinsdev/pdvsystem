package src.com.pdvsystem.db;

import src.com.pdvsystem.pdv.Role;
import src.com.pdvsystem.pdv.RoleManager;

import java.util.List;


public class User {
    private final String NAME;
    private final int ID;

    private final List<Role> roleList;

    public User(String name, int id, List<Role> roles){
        this.NAME = name;
        this.ID = id;
        this.roleList = roles;
    }

    public String getName() {
        return this.NAME;
    }

    public int getId() {
        return this.ID;
    }

    public List<Role> getRoles(){
        return this.roleList;
    }
}
