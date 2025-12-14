package com.kosmos.dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {

    // 1. CADASTRAR PRODUTO
    public void registerProduct(int artistId, String title, String description, double price, String imageUrl) {
        String sql = "INSERT INTO products (artist_id, title, description, price, image_url) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, artistId);
            stmt.setString(2, title);
            stmt.setString(3, description);
            stmt.setDouble(4, price);
            stmt.setString(5, imageUrl);

            stmt.executeUpdate();
            System.out.println("✅ ProductDAO: Obra '" + title + "' cadastrada!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar: " + e.getMessage());
        }
    }

    // 2. LISTAR VITRINE (Corrigido: Sem o texto da setinha)
    public void listarVitrine() {

        String sql = """
            SELECT p.id, p.title, p.description, p.price, u.username 
            FROM products p
            JOIN users u ON p.artist_id = u.id
            WHERE p.available = TRUE 
        """;

        System.out.println("\n--- 🎨 VITRINE DISPONÍVEL ---");
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(String.format(
                        "ID: %d | Obra: %-15s | Desc: %-20s | R$ %.2f | Artista: @%s",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"), // <--- Agora traz a descrição!
                        rs.getDouble("price"),
                        rs.getString("username")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. BAIXA NO ESTOQUE
    public void marcarComoVendido(int productId) {
        String sql = "UPDATE products SET available = FALSE WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. AUXILIAR: BUSCAR ID POR TÍTULO
    public int buscarIdPorTitulo(String titulo) {
        String sql = "SELECT id FROM products WHERE title = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public void listarProdutosDoArtista(int artistId) {
        String sql = "SELECT title, price FROM products WHERE artist_id = ?";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, artistId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("--- PORTFÓLIO DO ARTISTA " + artistId + " ---");
            while(rs.next()) {
                System.out.println("Obra: " + rs.getString("title") + " | R$ " + rs.getDouble("price"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}