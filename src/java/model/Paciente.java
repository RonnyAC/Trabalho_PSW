/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Ronny
 */
public class Paciente extends Pessoa{
    private boolean fuma;
    private boolean bebe;
    private boolean colesterol;
    private boolean diabete;
    private boolean doencaCardiaca;
    private List<Adicionais> cirurgias;
    private List<Adicionais> alergias; 

    public Paciente(int id, String nome, String cpf, String rg, Date dataNascimento, String endereco, String telefoneCelular, String email, String tipoConvenio) {
        super(id, nome, cpf, rg, dataNascimento, endereco, telefoneCelular, email, tipoConvenio);
    }

    public Paciente(boolean fuma, boolean bebe, boolean colesterol, boolean diabete, boolean doencaCardiaca, List<Adicionais> cirurgias, List<Adicionais> alergias) {
        this.fuma = fuma;
        this.bebe = bebe;
        this.colesterol = colesterol;
        this.diabete = diabete;
        this.doencaCardiaca = doencaCardiaca;
        this.cirurgias = cirurgias;
        this.alergias = alergias;
    }

    public Paciente() {
        
    }

    public boolean isFuma() {
        return fuma;
    }

    public boolean isBebe() {
        return bebe;
    }

    public boolean isColesterol() {
        return colesterol;
    }

    public boolean isDiabete() {
        return diabete;
    }

    public boolean isDoencaCardiaca() {
        return doencaCardiaca;
    }

    public List<Adicionais> getCirurgias() {
        return cirurgias;
    }

    public List<Adicionais> getAlergias() {
        return alergias;
    }

    public void setFuma(boolean fuma) {
        this.fuma = fuma;
    }

    public void setBebe(boolean bebe) {
        this.bebe = bebe;
    }

    public void setColesterol(boolean colesterol) {
        this.colesterol = colesterol;
    }

    public void setDiabete(boolean diabete) {
        this.diabete = diabete;
    }

    public void setDoencaCardiaca(boolean doencaCardiaca) {
        this.doencaCardiaca = doencaCardiaca;
    }

    public void setCirurgias(List<Adicionais> cirurgias) {
        this.cirurgias = cirurgias;
    }

    public void setAlergias(List<Adicionais> alergias) {
        this.alergias = alergias;
    }
    
}
