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
public class Paciente {
    //Atributos
    private final String nome;
    private final String cpf;
    private final String rg;
    private final String dataNascimento;
    private final String endereco;
    private final String telefoneCelular;
    private final String email;
    private final String tipoConvenio;

    public Paciente(String nome, String cpf, String rg, String dataNascimento, String endereco, String telefoneCelular, String email, String tipoConvenio) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefoneCelular = telefoneCelular;
        this.email = email;
        this.tipoConvenio = tipoConvenio;
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

    public String getDataNascimento() {
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
    
    
}
