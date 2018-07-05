/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import model.conexao.ConectaSQLite;

/**
 *
 * @author Ronny
 */
public class DAOFuncionarios extends DAO {

    /**
     *
     * @param funcionario
     * @return
     */
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

    /**
     *
     * @param funcionario
     * @return
     */
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

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean excluir(int id) {
        ConectaSQLite.conectar();
        String sqlDelete = "DELETE FROM tbl_funcionarios WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlDelete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
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

    /**
     *
     * @param id
     * @return
     */
    public Funcionario buscar(int id) {
        Funcionario funcionario = new Funcionario();
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sqlSelect = "SELECT * FROM tbl_medicos WHERE id = ?";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            funcionario.setId(resultSet.getInt("id"));
            funcionario.setNome(resultSet.getString("nome"));
            funcionario.setCpf(resultSet.getString("cpf"));
            funcionario.setRg(resultSet.getString("rg"));
            funcionario.setDataNascimento(resultSet.getDate("data_nascimento"));
            funcionario.setEndereco(resultSet.getString("endereco"));
            funcionario.setTelefoneCelular(resultSet.getString("telefone_celular"));
            funcionario.setEmail(resultSet.getString("email"));

            funcionario.setCargo(resultSet.getString("cargo"));
            funcionario.setMatricula(resultSet.getInt("matricula"));

        } catch (SQLException e) {

        } finally {
            try {
                preparedStatement.close();
                resultSet.close();
                ConectaSQLite.desconectar();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar: " + ex.getMessage());
            }
        }
        return funcionario;
    }

    /**
     *
     * @return
     */
    public List<Funcionario> listar() {
        List<Funcionario> funcionarios = new ArrayList<>();
        ConectaSQLite.conectar();

        ResultSet resultSet = null;
        Statement statement = null;
        String sqlSelect = "SELECT * FROM tbl_funcionarios";

        try {
            statement = ConectaSQLite.criarStatement();
            resultSet = statement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setCpf(resultSet.getString("cpf"));
                funcionario.setRg(resultSet.getString("rg"));
                funcionario.setDataNascimento(resultSet.getDate("data_nascimento"));
                funcionario.setEndereco(resultSet.getString("endereco"));
                funcionario.setTelefoneCelular(resultSet.getString("telefone_celular"));
                funcionario.setEmail(resultSet.getString("email"));
                
                funcionario.setCargo(resultSet.getString("cargo"));
                funcionario.setMatricula(resultSet.getInt("matricula"));

                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {

        } finally {
            try {
                statement.close();
                resultSet.close();
                ConectaSQLite.desconectar();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar: " + ex.getMessage());
            }
        }
        return funcionarios;
    }
}
