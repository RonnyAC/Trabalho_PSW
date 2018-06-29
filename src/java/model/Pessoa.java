/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ronny
 */
public abstract class Pessoa {
    private int id;
    private String nome;
    private String cpf;
    private String rg;
    private Date dataNascimento;
    private String endereco;
    private String telefoneCelular;
    private String email;
    private String tipoConvenio;
    
    private List<Pessoa> listaPessoa = new ArrayList<>();

    public Pessoa(int id, String nome, String cpf, String rg, Date dataNascimento, String endereco, String telefoneCelular, String email, String tipoConvenio) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefoneCelular = telefoneCelular;
        this.email = email;
        this.tipoConvenio = tipoConvenio;
    }

    public Pessoa() {
    }
    
    public int getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public String getEmail() {
        return email;
    }

    public String getTipoConvenio() {
        return tipoConvenio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipoConvenio(String tipoConvenio) {
        this.tipoConvenio = tipoConvenio;
    }
}
