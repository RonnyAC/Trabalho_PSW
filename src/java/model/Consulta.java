/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Time;
import model.enuns.TipoConsulta;

/**
 *
 * @author Ronny
 */
public class Consulta {
    private Integer id;
    private Date data;
    private Time horario;
    private Medico medico;
    private Paciente paciente;
    private Funcionario funcionario;
    private TipoConsulta tipo;

    public Consulta(Integer id, Date data, Time horario, Medico medico, Paciente paciente, Funcionario secretatia, TipoConsulta tipo) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.medico = medico;
        this.paciente = paciente;
        this.funcionario = secretatia;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Date getData() {
        return data;
    }

    public Time getHorario() {
        return horario;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }
    
    public Funcionario getSecretaria() {
        return funcionario;
    }

    public TipoConsulta getTipo() {
        return tipo;
    }
    
    
}
