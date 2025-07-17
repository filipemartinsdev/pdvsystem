package src.com.pdvsystem.pdv;

import java.util.ArrayList;
import java.util.List;

public class RoleManager {
    public static List<Role> toRoleList(int[] rolesIdList){
        List<Role> outList = new ArrayList<>();

        for (int roleId : rolesIdList){
            for (Role role:Role.values()){
                if (roleId == role.getID()){
                    outList.add(role);
                }
            }
        }

        return outList;
    }
}