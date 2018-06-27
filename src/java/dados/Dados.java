/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

import java.util.ArrayList;
import java.util.List;
import model.Consulta;
import model.Paciente;

/**
 *
 * @author Ronny
 */
public class Dados {

    private static final List<Paciente> LISTAPACIENTES = new ArrayList();
    private static final List<Consulta> LISTACONSULTAS = new ArrayList();

//    geeters and setters
    public static List<Paciente> getListaPacientes() {
        return LISTAPACIENTES;
    }

    public static List<Consulta> getListaConsulta() {
        return LISTACONSULTAS;
    }
}
