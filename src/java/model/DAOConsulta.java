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
public class DAOConsulta {

    public void inserir(Consulta consulta) {
        if (Dados.getListaConsulta().isEmpty()) {
            consulta.setId(1);
        } else {
            consulta.setId(Dados.getListaConsulta().get(Dados.getListaConsulta().size() - 1).getId() + 1);
        }
        Dados.getListaConsulta().add(consulta);
    }

    public void alterar(Consulta consulta) {
        for (Consulta c : Dados.getListaConsulta()) {
            if (c.getId().equals(consulta.getId())) {
                Dados.getListaConsulta().set(Dados.getListaConsulta().indexOf(c), consulta);
                break;
            }
        }
    }

    public void excluir(int id) {
        for (Consulta c : Dados.getListaConsulta()) {
            if (c.getId().equals(id)) {
                Dados.getListaConsulta().remove(Dados.getListaConsulta().indexOf(c));
                break;
            }
        }
    }
    
    public Consulta getConsultaId(int id) {
        Consulta consulta = null;
        for (Consulta c : Dados.getListaConsulta()) {
            if (c.getId().equals(id)) {
                consulta = c;
                break;
            }
        }
        return consulta;
    }

}
