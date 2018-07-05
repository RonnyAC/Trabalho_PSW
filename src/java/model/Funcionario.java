/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Ronny
 */
public class Funcionario extends Pessoa{
    private int matricula;
    private String Cargo;
    
    public Funcionario(){
        
    }

    public Funcionario(int id, String nome, String cpf, String rg, Date dataNascimento, String endereco, String telefoneCelular, String email, String tipoConvenio, int matricula) {
        super(id, nome, cpf, rg, dataNascimento, endereco, telefoneCelular, email, tipoConvenio);
        this.matricula = matricula;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }
    
    
    
}
