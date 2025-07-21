package src.com.pdvsystem.db;

public class DatabaseInfo {
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/pdv";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "eabs290593";

    public static String getDbUrl(){
        return DB_URL;
    }

    public static String getDbUser(){
        return DB_USER;
    }

    public static String getDbPassword(){
        return DB_PASSWORD;
    }
}
