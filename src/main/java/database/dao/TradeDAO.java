package com.kosmos.dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TradeDAO {

    public void registrarCompra(int buyerId, int productId) {
        String sql = "INSERT INTO trades (buyer_id, product_id, status) VALUES (?, ?, 'CONCLUIDO')";

        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, buyerId);
            stmt.setInt(2, productId);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                // Tira da vitrine imediatamente
                ProductDAO pDao = new ProductDAO();
                pDao.marcarComoVendido(productId);
                System.out.println("💰 TradeDAO: Venda registrada com sucesso!");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro na venda: " + e.getMessage());
        }
    }

    public void gerarRelatorioVendas() {
        String sql = """
            SELECT t.id, u.username, p.title, p.price, t.trade_date
            FROM trades t
            JOIN users u ON t.buyer_id = u.id
            JOIN products p ON t.product_id = p.id
            ORDER BY t.trade_date DESC
        """;

        System.out.println("\n--- 📜 HISTÓRICO DE VENDAS ---");
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(String.format(
                        "Venda #%d | Comprador: %-15s | Obra: %-20s | Valor: R$ %.2f",
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------\n");
    }
}