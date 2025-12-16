package com.kosmos.dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(String username, String name, String lastName, String email, String password, String role) {
        // O SQL com coringas (?) para segurança
        String sql = "INSERT INTO users (username, name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        // Try-with-resources: Garante que a conexão feche o 'stmt' sozinha, evitando vazamento de memória
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Preenchendo os coringas
            stmt.setString(1, username);
            stmt.setString(2, name);
            stmt.setString(3, lastName);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.setString(6, role);

            // Executa a gravação
            stmt.executeUpdate();
            System.out.println("UserDAO: Usuário @" + username + " cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("UserDAO: Erro ao registrar. Detalhes: " + e.getMessage());
            return false;
        }

        return true;
    }

    public int buscarIdPorUsuario(String username) {
        // 1. O comando SQL: "Me dê o ID da tabela users onde o username é igual a ?"
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 2. Preenche o ? com o nome que queremos buscar
            stmt.setString(1, username);

            // 3. ATENÇÃO: Para buscar, usamos 'executeQuery' (não executeUpdate)
            // O resultado volta numa "tabela virtual" chamada ResultSet
            ResultSet tabelaResultado = stmt.executeQuery();

            // 4. Verificamos se tem alguma linha na resposta
            if (tabelaResultado.next()) {
                // Se entrou aqui, achou! Retorna o número da coluna "id"
                return tabelaResultado.getInt("id");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar: " + e.getMessage());
        }

        // Se chegou aqui, não achou ninguém. Retorna -1.
        return -1;
    }

    public String buscarSenhaPorEmail(String email) {
        String sql = "SELECT password FROM users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getInstance();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet tabelaResultado = stmt.executeQuery();
            if (tabelaResultado.next()) {
                return tabelaResultado.getString("password");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar: " + e.getMessage());
        }

        return "";
    }

    public boolean validarLogin(String email, String password) {
        String sql = "SELECT id FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna TRUE se achou o usuário, FALSE se não achou.

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
