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
public class Medico extends Pessoa {
    private String crm;
    private String especializacao;
    
    public Medico(){
        
    }

    public Medico(int id, String crm, String especializacao, String nome, String cpf, String rg, Date dataNascimento, String endereco, String telefoneCelular, String email, String tipoConvenio) {
        super(id, nome, cpf, rg, dataNascimento, endereco, telefoneCelular, email, tipoConvenio);
        this.crm = crm;
        this.especializacao = especializacao;
    }

    public String getCrm() {
        return crm;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }
    
    
}
