/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ronny
 */
public class ConectaSQLite {
    private static  Connection conexao;

    /**
     * Metodo que conecta com o Banco de Dados.
     * @return 
     */
    public static boolean conectar() {
        try {
            String url = "jdbc:sqlite:banco de dados/adb.db";
            conexao = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        System.out.println("Conectou");
        return true;
    }
    
    /**
     * Método para desconectar do banco de dados
     * @return 
     */
    public static boolean desconectar() {
        try {
            if (conexao.isClosed() == false) {
                conexao.close();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        System.out.println("Desconectou");
        return true;
    }

    
    public static Statement criarStatement() {
        try {
            return conexao.createStatement();
        } catch (SQLException e) {
            return null;
        }
    }

    public static PreparedStatement criarPreparedStatement(String sql) {
        try {
            return conexao.prepareStatement(sql);
        } catch (SQLException e) {
            return null;
        }
    }
}
