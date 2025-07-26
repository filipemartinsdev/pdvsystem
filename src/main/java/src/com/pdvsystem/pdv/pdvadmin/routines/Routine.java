package src.com.pdvsystem.pdv.pdvadmin.routines;

public enum Routine {
    INVENTORY_CONTROL(100),
    FINANCE(200),
    MANAGEMENT(220);

    final int ID;
    Routine(int id){
        this.ID = id;
    }

    public int getId(){
        return this.ID;
    }
}
