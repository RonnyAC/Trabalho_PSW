/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import control.SecretariaControler;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import model.Paciente;

/**
 *
 * @author Ronny
 */
@Named(value = "pacienteListagemBean")
@Dependent
public class PacienteListagemBean {

    /**
     * Creates a new instance of PacienteListagemBean
     */
    public PacienteListagemBean() {
    }
    
    public List<Paciente> getPacientes(){
        return SecretariaControler.getInstance().listarPacientes();
    }    
}
