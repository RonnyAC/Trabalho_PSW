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
import model.Consulta;
import model.conexao.ConectaSQLite;

/**
 *
 * @author Ronny
 */
public class DAOConsulta extends DAO {

    public boolean inserir(Consulta consulta) {
        ConectaSQLite.conectar();
        int newID = buscarID("SELECT coalesce(MAX(id), 0)+1 as newID FROM tbl_consultas");

        String sqlInsert = "INSERT INTO tbl_consultas("
                + "id,"
                + "data,"
                + "horario,"
                + "crm_medico,"
                + "id_paciente,"
                + "matricula_secretaria,"
                + "tipo)"
                + " VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = ConectaSQLite.criarPreparedStatement(sqlInsert);

        try {
            preparedStatement.setInt(1, newID);
            preparedStatement.setDate(2, consulta.getData());
            preparedStatement.setTime(3, consulta.getHorario());
            preparedStatement.setString(4, consulta.getMedico().getCrm());
            preparedStatement.setInt(5, consulta.getPaciente().getId());
            preparedStatement.setInt(6, consulta.getSecretaria().getId());
            preparedStatement.setString(7, consulta.getTipo().getDescricao());

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

    public boolean alterar(Consulta consulta) {
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;

        String sqlUpdate = "UPDATE tbl_consultas"
                + " SET "
                + " data = ?,"
                + " horario = ?,"
                + " crm_medico = ?,"
                + " id_paciente = ?,"
                + " matricula_funcionario = ?,"
                + " tipo = ?"
                + " WHERE id = ?";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlUpdate);
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
    public boolean excluir(int id){
        ConectaSQLite.conectar();
        String sqlDelete = "DELETE FROM tbl_consultas WHERE cpf = " + id;
        
        PreparedStatement preparedStatement = ConectaSQLite.criarPreparedStatement(sqlDelete);
        
        try {
            
        } catch (Exception e) {
        }
        return true;
    }

    public Consulta buscar(int id) {
        return null;
    }
    
    public List<Consulta> listar(){
        return null;
    }

}
