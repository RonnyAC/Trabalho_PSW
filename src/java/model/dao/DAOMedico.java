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
import model.Medico;
import model.conexao.ConectaSQLite;

/**
 *
 * @author Ronny
 */
public class DAOMedico extends DAO {

    public boolean inserir(Medico medico) {
        ConectaSQLite.conectar();
        int newID = buscarID("SELECT coalesce(MAX(id), 0)+1 as newID FROM tbl_medicos");

        String sqlInsert = "INSERT INTO tbl_medicos("
                + "id, "
                + "nome, "
                + "cpf, "
                + "rg, "
                + "data_nascimento, "
                + "endereco, "
                + "telefone_celular, "
                + "email, "
                + "tipo_convenio, "
                + "crm, "
                + "especializacao)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = ConectaSQLite.criarPreparedStatement(sqlInsert);

        try {
            preparedStatement.setInt(1, newID);
            preparedStatement.setString(2, medico.getNome());
            preparedStatement.setString(3, medico.getCpf());
            preparedStatement.setString(4, medico.getRg());
            preparedStatement.setDate(5, medico.getDataNascimento());
            preparedStatement.setString(6, medico.getEndereco());
            preparedStatement.setString(7, medico.getTelefoneCelular());
            preparedStatement.setString(8, medico.getEmail());
            preparedStatement.setString(9, medico.getTipoConvenio());
            preparedStatement.setString(10, medico.getCrm());
            preparedStatement.setString(11, medico.getEspecializacao());

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

    public boolean alterar(Medico medico) {
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;

        String sqlUpdate = "UPDATE tbl_medico"
                + " SET "
                + " nome = ?,"
                + " cpf = ?,"
                + " rg = ?,"
                + " data_nascimento = ?,"
                + " endereco = ?,"
                + " telefone_celular = ?,"
                + " email = ?,"
                + " tipo_convenio = ?,"
                + " crm = ?,"
                + " especializacao = ?"
                + " WHERE id = ?";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlUpdate);

            preparedStatement.setString(1, medico.getNome());
            preparedStatement.setString(2, medico.getCpf());
            preparedStatement.setString(3, medico.getRg());
            preparedStatement.setDate(4, medico.getDataNascimento());
            preparedStatement.setString(5, medico.getEndereco());
            preparedStatement.setString(6, medico.getTelefoneCelular());
            preparedStatement.setString(7, medico.getEmail());
            preparedStatement.setString(8, medico.getTipoConvenio());
            preparedStatement.setString(9, medico.getCrm());
            preparedStatement.setString(10, medico.getEspecializacao());
            preparedStatement.setInt(11, medico.getId());

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

    @Override
    public boolean excluir(int id) {
        ConectaSQLite.conectar();
        String sqlDelete = "DELETE FROM tbl_medico WHERE id = ?";

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

    public Medico buscar(int id) {
        Medico medico = new Medico();
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sqlSelect = "SELECT * FROM tbl_medicos WHERE id = ?";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            medico.setId(resultSet.getInt("id"));
            medico.setNome(resultSet.getString("nome"));
            medico.setCpf(resultSet.getString("cpf"));
            medico.setRg(resultSet.getString("rg"));
            medico.setDataNascimento(resultSet.getDate("data_nascimento"));
            medico.setEndereco(resultSet.getString("endereco"));
            medico.setTelefoneCelular(resultSet.getString("telefone_celular"));
            medico.setEmail(resultSet.getString("email"));
            medico.setCrm(resultSet.getString("crm"));
            medico.setEspecializacao(resultSet.getString("especializacao"));

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
        return medico;
    }

    public List<Medico> listar() {
        List<Medico> medicos = new ArrayList<>();
        ConectaSQLite.conectar();

        ResultSet resultSet = null;
        Statement statement = null;
        String sqlSelect = "SELECT * FROM tbl_medicos";

        try {
            Medico medico = new Medico();
            statement = ConectaSQLite.criarStatement();
            resultSet = statement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                
                medico.setId(resultSet.getInt("id"));
                medico.setNome(resultSet.getString("nome"));
                medico.setCpf(resultSet.getString("cpf"));
                medico.setRg(resultSet.getString("rg"));
                medico.setDataNascimento(resultSet.getDate("data_nascimento"));
                medico.setEndereco(resultSet.getString("endereco"));
                medico.setTelefoneCelular(resultSet.getString("telefone_celular"));
                medico.setEmail(resultSet.getString("email"));
                
                medico.setCrm(resultSet.getString("crm"));
                medico.setEspecializacao(resultSet.getString("especializacao"));

                medicos.add(medico);
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
        return medicos;
    }

}
