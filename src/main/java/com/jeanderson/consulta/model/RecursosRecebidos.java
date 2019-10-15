/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jeanderson.consulta.model;

/**
 *
 * @author jeand
 */
public class RecursosRecebidos {
    private String texto, valores, valores_sigilosos;

    public RecursosRecebidos() {
        this.texto = "";
        this.valores = "";
        this.valores_sigilosos = "";
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getValores() {
        return valores;
    }

    public void setValores(String valores) {
        this.valores = valores;
    }

    public String getValores_sigilosos() {
        return valores_sigilosos;
    }

    public void setValores_sigilosos(String valores_sigilosos) {
        this.valores_sigilosos = valores_sigilosos;
    }
    
    
    
    
}
