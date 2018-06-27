/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dados.Dados;

/**
 *
 * @author Ronny
 */
public class DAOPaciente {

    public void inserir(Paciente paciente) {
        Dados.getListaPacientes().add(paciente);
    }

    public void alterar(Paciente paciente) {
        for (Paciente p : Dados.getListaPacientes()) {
            if (p.getCpf().equals(paciente.getCpf())) {
                Dados.getListaPacientes().set(Dados.getListaPacientes().indexOf(p), paciente);
                break;
            }
        }
    }

    public void excluir(String cpf) {
        for (Paciente p : Dados.getListaPacientes()) {
            if (p.getCpf().equals(cpf)) {
                Dados.getListaPacientes().remove(Dados.getListaPacientes().indexOf(p));
                break;
            }
        }
    }

    public Paciente getPacienteCpf(String cpf) {
        Paciente paciente = null;
        for (Paciente p : Dados.getListaPacientes()) {
            if (p.getCpf().equals(cpf)) {
                paciente = p;
                break;
            }
        }
        return paciente;
    }

    public boolean checaCpf(String cpf) {
        boolean checa = false;
        for (Paciente p : Dados.getListaPacientes()) {
            if (p.getCpf().equals(cpf)) {
                checa = true;
                break;
            }
        }
        return checa;
    }

}
