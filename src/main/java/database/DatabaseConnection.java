package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    // Configurações (Hardcoded para dev, em produção usaria variáveis de ambiente)
    private static final String URL = "jdbc:postgresql://localhost:5432/kosmos_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Gabrielc2006";

    // A variável estática que guarda a ÚNICA conexão (Singleton)
    private static Connection instance;

    private DatabaseConnection(){}

    public static Connection getInstance() {
        try {
            // Só conecta se não existir conexão ou se ela caiu
            if (instance == null || instance.isClosed()) {
                Class.forName("org.postgresql.Driver"); // Chama o Driver do Maven
                instance = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database: Conectado ao PostgreSQL com sucesso.");
            }
        } catch (Exception e) {
            System.err.println("Database: Falha crítica na conexão.");
            e.printStackTrace();
        }
        return instance;
    }
}
