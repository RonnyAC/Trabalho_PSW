/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import control.SecretariaControler;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import model.Paciente;

/**
 *
 * @author Ronny
 */
@Named(value = "pacienteCadastroBean")
@SessionScoped

public class PacienteCadastroBean implements Serializable {
    private String nome;
    private String cpf;
    private String rg;
    private String dataNascimento;
    private String endereco;
    private String telefoneCelular;
    private String email;
    private String tipoConvenio;
    
    /**
     * Creates a new instance of PacienteCadastroBean
     */
    public PacienteCadastroBean() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoConvenio() {
        return tipoConvenio;
    }

    public void setTipoConvenio(String tipoConvenio) {
        this.tipoConvenio = tipoConvenio;
    }
    
    public String salvar(){
        SecretariaControler.getInstance().salvarPaciente(new Paciente(nome, cpf,
                rg, dataNascimento, endereco, telefoneCelular, email, tipoConvenio));
        return "pacientes";
    }
    
    public String solicitaEdicao(Paciente paciente){
        this.cpf = paciente.getCpf();
        
        paciente = SecretariaControler.getInstance().buscarPaciente(cpf);
        SecretariaControler.getInstance().salvarPaciente(paciente);
        
        return "cadastro";
    }
    
    public String excluir(Paciente paciente){
        this.cpf = paciente.getCpf();
        SecretariaControler.getInstance().excluirPaciente(cpf);
        
        return "index";
    }
}
