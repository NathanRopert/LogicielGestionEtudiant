package fr.sae201.gestiongroupesetudiant.models;

public interface DAO {
    public static final String DB_URL = "jdbc:mysql://localhost:8889/"
            + "SAE204?zeroDateTimeBehavior=convertToNull";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER = "Nathan";  // 'root' by default if not changed
    public static final String PASS = "root"; //
}
