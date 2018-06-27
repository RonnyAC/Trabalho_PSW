/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ronny
 */
public class Consulta {
    private Integer id;
    private String data;
    private String horario;
    private String medico;
    private Paciente paciente;
    private String tipo;

    public Consulta(Integer id, String data, String horario, String medico, Paciente paciente, String tipo) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.medico = medico;
        this.paciente = paciente;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }

    public String getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getTipo() {
        return tipo;
    }
    
    
}
