package src.com.pdvsystem.pdv;

public enum Role {
    CASHIER_TRAINEE(0),
    CASHIER_OPERATOR(5),

    PDV_TRAINEE(10),
    PDV_OPERATOR(15),

    TECHNIC(30),
    ADMIN(50);

    private final int ID;

    Role(int id){
        this.ID = id;
    }

    public int getID(){
        return this.ID;
    }

    public static Role getRole(int roleId){
        for (Role r:Role.values()){
            if (r.getID() == roleId){
                return r;
            }
        }

        return null;
    }
}
