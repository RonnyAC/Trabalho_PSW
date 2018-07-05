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
                + "tipo_convenio, "
                + "fuma, "
                + "bebe, "
                + "colesterol, "
                + "diabete, "
                + "doenca_cardiaca)"
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

            preparedStatement.setBoolean(10, paciente.isFuma());
            preparedStatement.setBoolean(11, paciente.isBebe());
            preparedStatement.setBoolean(12, paciente.isColesterol());
            preparedStatement.setBoolean(13, paciente.isDiabete());
            preparedStatement.setBoolean(14, paciente.isDoencaCardiaca());

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
        for (Adicionais adicional : paciente.getCirurgias()) {
            inserirAdicionais(newID, adicional);
        }

        for (Adicionais adicional : paciente.getAlergias()) {
            inserirAdicionais(newID, adicional);
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
                + " tipo_convenio = ?,"
                + " fuma = ?,"
                + " bebe = ?,"
                + " colesterol = ?,"
                + " diabete =?,"
                + " doenca_cardiaca"
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
            preparedStatement.setBoolean(9, paciente.isFuma());
            preparedStatement.setBoolean(10, paciente.isBebe());
            preparedStatement.setBoolean(11, paciente.isColesterol());
            preparedStatement.setBoolean(12, paciente.isDiabete());
            preparedStatement.setBoolean(13, paciente.isDoencaCardiaca());
            preparedStatement.setInt(14, paciente.getId());

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
        for (Adicionais adicionais : paciente.getCirurgias()) {
            alterarAdicionais(paciente.getId(), adicionais);
        }

        for (Adicionais adicionais : paciente.getAlergias()) {
            alterarAdicionais(paciente.getId(), adicionais);
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

        excluirAdicionais(id);

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
        List<Paciente> pacientes = new ArrayList<>();
        ConectaSQLite.conectar();

        ResultSet resultSet = null;
        Statement statement = null;
        String sqlSelect = "SELECT * FROM tbl_medicos";

        try {
            Paciente paciente = new Paciente();
            statement = ConectaSQLite.criarStatement();
            resultSet = statement.executeQuery(sqlSelect);
            while (resultSet.next()) {

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

                pacientes.add(paciente);
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

        for (Paciente paciente : pacientes) {
            paciente.setCirurgias(buscarAdicionais(paciente.getId(), "cirurgia"));
            paciente.setAlergias(buscarAdicionais(paciente.getId(), "alergia"));
        }

        return pacientes;
    }
    
    
    /*
        Persistencia para os atributos adicionais do Paciente (cirurgias e alergias),
        apenas a classe DAOPaciente tem acesso a estes metodos.
    */
    private List<Adicionais> buscarAdicionais(int idPaciente, String tipo) {
        List<Adicionais> lista = new ArrayList<>();
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sqlSelect = "SELECT * FROM tbl_adicionais WHERE id_paciente = ? AND tipo = ? ";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlSelect);
            preparedStatement.setInt(1, idPaciente);
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

    private boolean inserirAdicionais(int idPaciente, Adicionais adicional) {
        ConectaSQLite.conectar();
        String sqlInsert = "INSERT INTO tbl_adicionais ("
                + "id_paciente, "
                + "tipo, "
                + "nome, "
                + "descricao)"
                + "VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = ConectaSQLite.criarPreparedStatement(sqlInsert);

        try {
            preparedStatement.setInt(1, idPaciente);
            preparedStatement.setString(2, adicional.getTipo());
            preparedStatement.setString(3, adicional.getNome());
            preparedStatement.setString(4, adicional.getDescricao());

        } catch (SQLException e) {
        }

        return true;
    }

    private boolean alterarAdicionais(int idPaciente, Adicionais adicionais) {
        ConectaSQLite.conectar();

        PreparedStatement preparedStatement = null;

        String sqlUpdate = "UPDATE tbl_adicionais"
                + " SET "
                + " nome = ?,"
                + " descricao = ?,"
                + " tipo = ?"
                + " WHERE id_paciente = ?";

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlUpdate);

            preparedStatement.setString(1, adicionais.getNome());
            preparedStatement.setString(2, adicionais.getDescricao());
            preparedStatement.setString(3, adicionais.getTipo());
            preparedStatement.setInt(4, idPaciente);

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

    private boolean excluirAdicionais(int idPaciente) {

        ConectaSQLite.conectar();
        String sqlDelete = "DELETE FROM tbl_adicionais WHERE id_paciente = ?";

        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = ConectaSQLite.criarPreparedStatement(sqlDelete);

            preparedStatement.setInt(1, idPaciente);

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

}
