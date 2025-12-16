package database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
    // Configurações (Hardcoded para dev, em produção usaria variáveis de ambiente)
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    // A variável estática que guarda a ÚNICA conexão (Singleton)
    private static Connection instance;

    private DatabaseConnection(){}

    public static Connection getInstance() {
        if (URL == null || USER == null || PASSWORD == null) {
            loadConfig();
        }
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

    private static void loadConfig() {
        Properties prop = new Properties();
        String configFileName = "config.properties";

        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                System.err.println("Arquivo de configuração não encontrado no Classpath!");
                return;
            }
            prop.load(input);
            URL = prop.getProperty("db.url");
            USER = prop.getProperty("db.user");
            PASSWORD = prop.getProperty("db.password");

            System.out.println("Configuração carregada..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
