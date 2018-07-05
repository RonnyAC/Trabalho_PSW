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
import model.Consulta;
import model.Funcionario;
import model.Medico;
import model.Paciente;
import model.conexao.ConectaSQLite;

/**
 *
 * @author Ronny
 */
public class DAOConsulta extends DAO {

    /**
     * Método responsavel por inserir uma nova consulta no banco de dados
     *
     * @param consulta --> Objeto referente a uma consulta, possui todos os
     * dados a serem inseridos no banco de dados.
     * @return
     */
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
            preparedStatement.setInt(6, consulta.getFuncionario().getId());
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

    /**
     *
     * @param consulta
     * @return
     */
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

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean excluir(int id) {
        ConectaSQLite.conectar();
        String sqlDelete = "DELETE FROM tbl_consultas WHERE id = ?";
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
    public Consulta buscar(int id) {
        DAOMedico daoMedico = null;
        DAOPaciente daoPaciente = null;
        DAOFuncionarios daoSecretaria = null;

        Integer idMedico = 0;
        Integer idPaciente = 0;
        Integer idFuncionario = 0;

        Medico medico = new Medico();
        Paciente paciente = new Paciente();
        Funcionario funcionario = new Funcionario();
        Consulta consulta = new Consulta();

        ConectaSQLite.conectar();
        PreparedStatement preparedstatement = null;
        ResultSet dados = null;

        String sqlSelect = "SELECT * FROM tbl_consulta WHERE id = ?";

        try {
            preparedstatement = ConectaSQLite.criarPreparedStatement(sqlSelect);
            preparedstatement.setInt(1, id);
            dados = preparedstatement.executeQuery();

            consulta.setId(dados.getInt("id"));
            consulta.setData(dados.getDate("data"));
            consulta.setHorario(dados.getTime("horario"));
            consulta.getTipo().setDescricao(dados.getString("tipo"));

            idMedico = dados.getInt("id_medico");
            idPaciente = dados.getInt("id_paciente");
            idFuncionario = dados.getInt("id_funcionario");

        } catch (SQLException e) {

        } finally {
            try {
                preparedstatement.close();
                ConectaSQLite.desconectar();
            } catch (SQLException e) {

            }
        }

        medico = daoMedico.buscar(idMedico);
        paciente = daoPaciente.buscar(idPaciente);
        funcionario = daoSecretaria.buscar(idFuncionario);

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setFuncionario(funcionario);

        return consulta;
    }

    /**
     *
     * @return
     */
    public List<Consulta> listar() {
        List<Consulta> consultas = new ArrayList<>();
        List<CompletaConsulta> medicos = new ArrayList<>();
        List<CompletaConsulta> pacientes = new ArrayList<>();
        List<CompletaConsulta> funcionarios = new ArrayList<>();

        ConectaSQLite.conectar();

        ResultSet dados = null;
        Statement statement = null;

        String sqlSelect = "SELECT * FROM tbl_consultas";

        try {
            statement = ConectaSQLite.criarStatement();
            dados = statement.executeQuery(sqlSelect);
            while (dados.next()) {
                Consulta consulta = new Consulta();
                CompletaConsulta medico = new CompletaConsulta();
                CompletaConsulta paciente = new CompletaConsulta();
                CompletaConsulta funcionario = new CompletaConsulta();

                consulta.setId(dados.getInt("id"));
                consulta.setData(dados.getDate("data"));
                consulta.setHorario(dados.getTime("horario"));
                consulta.getTipo().setDescricao(dados.getString("tipo"));

                medico.setIdConsulta(dados.getInt("id"));
                medico.setIdObjeto(dados.getInt("id_medico"));

                paciente.setIdConsulta(dados.getInt("id"));
                paciente.setIdObjeto(dados.getInt("id_paciente"));

                funcionario.setIdConsulta(dados.getInt("id"));
                funcionario.setIdObjeto(dados.getInt("id_funcionario"));

                medicos.add(medico);
                pacientes.add(paciente);
                funcionarios.add(funcionario);

                consultas.add(consulta);
            }
        } catch (SQLException e) {

        } finally {
            try {
                dados.close();
                statement.close();
                ConectaSQLite.desconectar();
            } catch (SQLException e) {

            }
        }
        consultas = InsereDadosConsulta(consultas, medicos, pacientes, funcionarios);

        return consultas;
    }
    
    
    /*
        Métodos auxiliares para o @DAOConsulta
    */

    private List<Consulta> InsereDadosConsulta(List<Consulta> consulta,
            List<CompletaConsulta> medicos, List<CompletaConsulta> pacientes,
            List<CompletaConsulta> funcionarios) {
        DAOMedico daoMedico = null;
        DAOPaciente daoPaciente = null;
        DAOFuncionarios daoSecretaria = null;

        for (Consulta c : consulta) {
            for (CompletaConsulta m : medicos) {
                if (c.getId().equals(m.getIdConsulta())) {
                    consulta.get(consulta.indexOf(c)).setMedico(daoMedico.buscar(m.getIdObjeto()));
                    break;
                }
            }

            for (CompletaConsulta p : pacientes) {
                if (c.getId().equals(p.getIdConsulta())) {
                    consulta.get(consulta.indexOf(c)).setPaciente(daoPaciente.buscar(p.getIdObjeto()));
                    break;
                }
            }
            for (CompletaConsulta f : funcionarios) {
                if (c.getId().equals(f.getIdConsulta())) {
                    consulta.get(consulta.indexOf(c)).setFuncionario(daoSecretaria.buscar(f.getIdObjeto()));
                    break;
                }
            }
        }

        return consulta;
    }

    private class CompletaConsulta {

        Integer idObjeto;
        Integer idConsulta;

        public Integer getIdObjeto() {
            return idObjeto;
        }

        public void setIdObjeto(Integer idObjeto) {
            this.idObjeto = idObjeto;
        }

        public Integer getIdConsulta() {
            return idConsulta;
        }

        public void setIdConsulta(Integer idConsulta) {
            this.idConsulta = idConsulta;
        }

    }

}
