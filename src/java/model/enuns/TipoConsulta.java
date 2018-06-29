/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.enuns;

/**
 *
 * @author Ronny
 */
public enum TipoConsulta {
    NORMAL("Normal (duração de 1 hora)"),
    RETORNO("Retorno (duração de 30 minutos)");

    private String descricao;

    private TipoConsulta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
