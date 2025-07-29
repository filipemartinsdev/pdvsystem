package src.com.pdvsystem.db;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseInfo {
<<<<<<< HEAD
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/pdv";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "";
=======
//    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/pdv";
//    private static final String DB_USER = "postgres";
//    private static final String DB_PASSWORD = "";

//    private static final String SUPABASE_HOST = "db.mqigqsbovtyufzmpiyxb.supabase.co:5432";

    static {
        dotenv = Dotenv.load();
    }

    private static final Dotenv dotenv;

    private static final String SUPABASE_HOST = dotenv.get("DB_HOST");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASS");


    private static final String DB_URL = "jdbc:postgresql://"+SUPABASE_HOST+"/postgres";

>>>>>>> develop

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
