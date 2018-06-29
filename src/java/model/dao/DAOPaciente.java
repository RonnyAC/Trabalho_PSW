/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Adicionais;
import model.Paciente;
import model.conexao.ConectaSQLite;

/**
 *
 * @author Ronny
 */
public class DAOPaciente extends DAO {

    public boolean inserir(Paciente paciente) {
        ConectaSQLite.conectar();
        int newID = buscarID("SELECT coalesce(MAX(id), 0)+1 as newID FROM tbl_pacientes");

        String sqlInsert = "INSERT INTO tbl_pacientes("
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
            preparedStatement.setString(2, paciente.getNome());
            preparedStatement.setString(3, paciente.getCpf());
            preparedStatement.setString(4, paciente.getRg());
            preparedStatement.setDate(5, paciente.getDataNascimento());
            preparedStatement.setString(6, paciente.getEndereco());
            preparedStatement.setString(7, paciente.getTelefoneCelular());
            preparedStatement.setString(8, paciente.getEmail());
            preparedStatement.setString(9, paciente.getTipoConvenio());

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

    public boolean alterar(Paciente paciente) {
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;

        String sqlUpdate = "UPDATE tbl_pacientes"
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

            preparedStatement.setString(1, paciente.getNome());
            preparedStatement.setString(2, paciente.getCpf());
            preparedStatement.setString(3, paciente.getRg());
            preparedStatement.setDate(4, paciente.getDataNascimento());
            preparedStatement.setString(5, paciente.getEndereco());
            preparedStatement.setString(6, paciente.getTelefoneCelular());
            preparedStatement.setString(7, paciente.getEmail());
            preparedStatement.setString(8, paciente.getTipoConvenio());
            preparedStatement.setInt(9, paciente.getId());

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
        String sqlDelete = "DELETE FROM tbl_pacientes WHERE id = ?";

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

    public Paciente buscar(int id) {
        Paciente paciente = new Paciente();
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sqlSelect = "SELECT * FROM tbl_pacientes WHERE id = ?";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            paciente.setId(resultSet.getInt("id"));
            paciente.setNome(resultSet.getString("nome"));
            paciente.setCpf(resultSet.getString("cpf"));
            paciente.setRg(resultSet.getString("rg"));
            paciente.setDataNascimento(resultSet.getDate("data_nascimento"));
            paciente.setEndereco(resultSet.getString("endereco"));
            paciente.setTelefoneCelular(resultSet.getString("telefone_celular"));
            paciente.setEmail(resultSet.getString("email"));

            paciente.setFuma(resultSet.getBoolean("fuma"));
            paciente.setBebe(resultSet.getBoolean("bebe"));
            paciente.setColesterol(resultSet.getBoolean("colesterol"));
            paciente.setDiabete(resultSet.getBoolean("diabete"));
            paciente.setDoencaCardiaca(resultSet.getBoolean("doenca_cardiaca"));

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

        paciente.setCirurgias(buscarAdicionais(paciente.getId(), "cirurgia"));
        paciente.setAlergias(buscarAdicionais(paciente.getId(), "alergia"));

        return paciente;
    }

    public List<Paciente> listar() {
        return null;
    }

    private boolean checaCpf(String cpf) {
        return false;
    }

    private List<Adicionais> buscarAdicionais(int id, String tipo) {
        List<Adicionais> lista = new ArrayList<>();
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sqlSelect = "SELECT * FROM tbl_adicionais WHERE id_paciente = ? AND tipo = ? ";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlSelect);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, tipo);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Adicionais adicional = new Adicionais();

                adicional.setNome(resultSet.getString("nome"));
                adicional.setDescricao(resultSet.getString("descricao"));
                adicional.setTipo(resultSet.getString("tipo"));

                lista.add(adicional);
            }

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
        return lista;
    }

}
