/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import model.conexao.ConectaSQLite;

/**
 *
 * @author Ronny
 */
public class DAOSecretaria extends DAO {
    public boolean inserir(Funcionario funcionario) {
        ConectaSQLite.conectar();
        int newID = buscarID("SELECT coalesce(MAX(id), 0)+1 as newID FROM tbl_medicos");

        String sqlInsert = "INSERT INTO tbl_funcionarios("
                + "id, "
                + "nome, "
                + "cpf, "
                + "rg, "
                + "data_nascimento, "
                + "endereco, "
                + "telefone_celular, "
                + "email, "
                + "tipo_convenio)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = ConectaSQLite.criarPreparedStatement(sqlInsert);

        try {
            preparedStatement.setInt(1, newID);
            preparedStatement.setString(2, funcionario.getNome());
            preparedStatement.setString(3, funcionario.getCpf());
            preparedStatement.setString(4, funcionario.getRg());
            preparedStatement.setDate(5, funcionario.getDataNascimento());
            preparedStatement.setString(6, funcionario.getEndereco());
            preparedStatement.setString(7, funcionario.getTelefoneCelular());
            preparedStatement.setString(8, funcionario.getEmail());
            preparedStatement.setString(9, funcionario.getTipoConvenio());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAOConsulta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ConectaSQLite.desconectar();
        }
        return true;
    }

    public boolean alterar(Funcionario funcionario) {
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;

        String sqlUpdate = "UPDATE tbl_funcionario"
                + " SET "
                + " nome = ?,"
                + " cpf = ?,"
                + " rg = ?,"
                + " data_nascimento = ?,"
                + " endereco = ?,"
                + " telefone_celular = ?,"
                + " email = ?,"
                + " tipo_convenio = ?"
                + " WHERE id = ?";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlUpdate);
            
            preparedStatement.setString(1, funcionario.getNome());
            preparedStatement.setString(2, funcionario.getCpf());
            preparedStatement.setString(3, funcionario.getRg());
            preparedStatement.setDate(4, funcionario.getDataNascimento());
            preparedStatement.setString(5, funcionario.getEndereco());
            preparedStatement.setString(6, funcionario.getTelefoneCelular());
            preparedStatement.setString(7, funcionario.getEmail());
            preparedStatement.setString(8, funcionario.getTipoConvenio());
            preparedStatement.setInt(9, funcionario.getId());
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println(" SQL Erro: " + e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                ConectaSQLite.desconectar();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar: " + ex.getMessage());
            }
        }
        return true;
    }

    public boolean remover(String cpf) {
        return false;
    }

    public Funcionario buscar(String cpf) {
        return null;
    }

    public List<Funcionario> listar() {
        return null;
    }

    private boolean checaCpf(String cpf) {
        return false;
    }
}
