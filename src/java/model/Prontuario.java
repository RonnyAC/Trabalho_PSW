/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Ronny
 */
public class Prontuario {
    private int id;
    private Paciente paciente;
    private Date Data;
    private Medico medico;
    private String sintomas;
    private String diagnosticos;
    private String prescricaoTratamento;

    public Prontuario() {
    }

    public Prontuario(int id, Paciente paciente, Date Data, Medico medico, String sintomas, String diagnosticos, String prescricaoTratamento) {
        this.id = id;
        this.paciente = paciente;
        this.Data = Data;
        this.medico = medico;
        this.sintomas = sintomas;
        this.diagnosticos = diagnosticos;
        this.prescricaoTratamento = prescricaoTratamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Date getData() {
        return Data;
    }

    public void setData(Date Data) {
        this.Data = Data;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(String diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public String getPrescricaoTratamento() {
        return prescricaoTratamento;
    }

    public void setPrescricaoTratamento(String prescricaoTratamento) {
        this.prescricaoTratamento = prescricaoTratamento;
    }
    
    
    
}
