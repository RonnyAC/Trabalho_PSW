/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dados.Dados;
import java.util.List;
import model.DAOPaciente;
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
        if(Dados.getListaPacientes().isEmpty() || !acoesPaciente.checaCpf(paciente.getCpf())){
            acoesPaciente.inserir(paciente);
        }else{
            acoesPaciente.alterar(paciente);
        }
    }
    
    public void excluirPaciente(String cpf){
        acoesPaciente.excluir(cpf);
    }
    
    public Paciente buscarPaciente(String cpf){
        Paciente paciente = acoesPaciente.getPacienteCpf(cpf);
        
        return paciente;
    }
    
    public List<Paciente> listarPacientes(){
        return Dados.getListaPacientes();
    }
}
