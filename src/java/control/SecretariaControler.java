/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.List;
import model.dao.DAOPaciente;
import model.Paciente;


/**
 *
 * @author Ronny
 */
public class SecretariaControler {
    private final DAOPaciente acoesPaciente = new DAOPaciente();

    private static SecretariaControler instance;

    public static SecretariaControler getInstance() {
        if (instance == null) {
            instance = new SecretariaControler();
        }
        return instance;
    }
    //TRATAR NAO EXISTENCIA DE CPF PARA ENVIAR MENSAGEM PERGUNTANDO SE DESEJA ALTERAR.
    public void salvarPaciente(Paciente paciente){
        
    }
    
    public void excluirPaciente(String cpf){
        //acoesPaciente.excluir(cpf);
    }
    
    public Paciente buscarPaciente(String cpf){
        Paciente pessoa = new Paciente();
        
        return null;
    }
    
    public List<Paciente> listarPacientes(){
        return null;
    }
}
