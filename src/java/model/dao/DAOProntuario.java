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
import model.Medico;
import model.Paciente;
import model.Prontuario;
import model.conexao.ConectaSQLite;

/**
 *
 * @author Ronny
 */
public class DAOProntuario extends DAO {

    /**
     *
     * @param prontuario
     * @return
     */
    public boolean inserir(Prontuario prontuario) {
        ConectaSQLite.conectar();
        int newID = buscarID("SELECT coalesce(MAX(id), 0)+1 as newID FROM tbl_prontuarios");

        String sqlInsert = "INSERT INTO tbl_prontuarios("
                + "id, "
                + "id_paciente, "
                + "data, "
                + "id_medico, "
                + "sintomas, "
                + "diagnosticos, "
                + "prescricao)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = ConectaSQLite.criarPreparedStatement(sqlInsert);

        try {
            preparedStatement.setInt(1, newID);
            preparedStatement.setInt(2, prontuario.getMedico().getId());
            preparedStatement.setDate(3, prontuario.getData());
            preparedStatement.setInt(4, prontuario.getPaciente().getId());
            preparedStatement.setString(5, prontuario.getSintomas());
            preparedStatement.setString(6, prontuario.getDiagnosticos());
            preparedStatement.setString(7, prontuario.getPrescricaoTratamento());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }
            ConectaSQLite.desconectar();
        }
        return true;
    }

    /**
     *
     * @param prontuario
     * @return
     */
    public boolean alterar(Prontuario prontuario) {
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;

        String sqlUpdate = "UPDATE tbl_prontuario"
                + " SET "
                + " id_medico = ?,"
                + " data = ?,"
                + " id_paciente = ?,"
                + " sintomas = ?,"
                + " diagnosticos = ?,"
                + " prescricao = ?"
                + " WHERE id = ?";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlUpdate);

            preparedStatement.setInt(1, prontuario.getMedico().getId());
            preparedStatement.setDate(2, prontuario.getData());
            preparedStatement.setInt(3, prontuario.getPaciente().getId());
            preparedStatement.setString(4, prontuario.getSintomas());
            preparedStatement.setString(5, prontuario.getDiagnosticos());
            preparedStatement.setString(6, prontuario.getPrescricaoTratamento());
            preparedStatement.setInt(7, prontuario.getId());

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
        String sqlDelete = "DELETE FROM tbl_prontuario WHERE id = ?";
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
    public Prontuario buscar(int id) {
        DAOMedico daoMedico = null;
        DAOPaciente daoPaciente = null;

        Integer idMedico = 0;
        Integer idPaciente = 0;

        Medico medico = new Medico();
        Paciente paciente = new Paciente();

        Prontuario prontuario = new Prontuario();
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;
        ResultSet dados = null;

        String sqlSelect = "SELECT * FROM tbl_prontuario WHERE id = ?";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            dados = preparedStatement.executeQuery();

            prontuario.setId(dados.getInt("id"));
            prontuario.setData(dados.getDate("data"));
            prontuario.setSintomas(dados.getString("sintomas"));
            prontuario.setDiagnosticos(dados.getString("diagnosticos"));
            prontuario.setPrescricaoTratamento(dados.getString("prescricao"));

            idMedico = dados.getInt("id_medico");
            idPaciente = dados.getInt("id_paciente");

        } catch (SQLException e) {

        } finally {
            try {
                preparedStatement.close();
                dados.close();
                ConectaSQLite.desconectar();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar: " + ex.getMessage());
            }
        }

        medico = daoMedico.buscar(idMedico);
        paciente = daoPaciente.buscar(idPaciente);

        prontuario.setMedico(medico);
        prontuario.setPaciente(paciente);

        return prontuario;
    }

    /**
     *
     * @return
     */
    public List<Prontuario> listar() {
        List<Prontuario> prontuarios = new ArrayList<>();
        List<DAOProntuario.CompletaProntuario> medicos = new ArrayList<>();
        List<DAOProntuario.CompletaProntuario> pacientes = new ArrayList<>();

        ConectaSQLite.conectar();

        ResultSet dados = null;
        Statement statement = null;

        String sqlSelect = "SELECT * FROM tbl_consultas";

        try {
            statement = ConectaSQLite.criarStatement();
            dados = statement.executeQuery(sqlSelect);
            while (dados.next()) {
                Prontuario prontuario = new Prontuario();
                DAOProntuario.CompletaProntuario medico = new DAOProntuario.CompletaProntuario();
                DAOProntuario.CompletaProntuario paciente = new DAOProntuario.CompletaProntuario();

                prontuario.setId(dados.getInt("id"));
                prontuario.setData(dados.getDate("data"));
                prontuario.setSintomas(dados.getString("sintomas"));
                prontuario.setDiagnosticos(dados.getString("diagnosticos"));
                prontuario.setPrescricaoTratamento(dados.getString("prescricao"));

                medico.setIdProntuario(dados.getInt("id"));
                medico.setIdObjeto(dados.getInt("id_medico"));

                paciente.setIdProntuario(dados.getInt("id"));
                paciente.setIdObjeto(dados.getInt("id_paciente"));

                medicos.add(medico);
                pacientes.add(paciente);

                prontuarios.add(prontuario);
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
        prontuarios = InsereDadosConsulta(prontuarios, medicos, pacientes);

        return prontuarios;
    }

    /*
        Métodos auxiliares para o @DAOProntuario
     */
    private List<Prontuario> InsereDadosConsulta(List<Prontuario> prontuario,
            List<DAOProntuario.CompletaProntuario> medicos, List<DAOProntuario.CompletaProntuario> pacientes) {
        DAOMedico daoMedico = null;
        DAOPaciente daoPaciente = null;

        for (Prontuario p : prontuario) {
            for (DAOProntuario.CompletaProntuario m : medicos) {
                if (p.getId().equals(m.getIdObjeto())) {
                    prontuario.get(prontuario.indexOf(p)).setMedico(daoMedico.buscar(m.getIdObjeto()));
                    break;
                }
            }

            for (DAOProntuario.CompletaProntuario c : pacientes) {
                if (p.getId().equals(c.getIdProntuario())) {
                    prontuario.get(prontuario.indexOf(p)).setPaciente(daoPaciente.buscar(c.getIdObjeto()));
                    break;
                }
            }
        }

        return prontuario;
    }

    private class CompletaProntuario {

        Integer idObjeto;
        Integer idProntuario;

        public Integer getIdObjeto() {
            return idObjeto;
        }

        public void setIdObjeto(Integer idObjeto) {
            this.idObjeto = idObjeto;
        }

        public Integer getIdProntuario() {
            return idProntuario;
        }

        public void setIdProntuario(Integer idConsulta) {
            this.idProntuario = idConsulta;
        }

    }
}
