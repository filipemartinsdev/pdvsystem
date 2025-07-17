package src.com.pdvsystem.db;

import src.com.pdvsystem.pdv.Role;
import src.com.pdvsystem.pdv.RoleManager;

import java.util.List;


public class User { // TODO: FINISH THIS
    private final String NAME;
    private final int ID;

    private List<Role> roleList;

    public User(String name, int id, int[] roles){
        this.NAME = name;
        this.ID = id;
        this.roleList = RoleManager.toRoleList(roles);
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
