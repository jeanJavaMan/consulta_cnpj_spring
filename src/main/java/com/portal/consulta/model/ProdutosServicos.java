/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta.model;

/**
 *
 * @author jeand
 */
public class ProdutosServicos {
    private String bens_patrimoniais, servicos,obras, materiais, outros;

    public String getBens_patrimoniais() {
        return bens_patrimoniais;
    }

    public void setBens_patrimoniais(String bens_patrimoniais) {
        this.bens_patrimoniais = bens_patrimoniais;
    }

    public String getServicos() {
        return servicos;
    }

    public void setServicos(String servicos) {
        this.servicos = servicos;
    }

    public String getObras() {
        return obras;
    }

    public void setObras(String obras) {
        this.obras = obras;
    }

    public String getMateriais() {
        return materiais;
    }

    public void setMateriais(String materiais) {
        this.materiais = materiais;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }

    @Override
    public String toString() {
        return "ProdutosServicos{" + "bens_patrimoniais=" + bens_patrimoniais + ", servicos=" + servicos + ", obras=" + obras + ", materiais=" + materiais + ", outros=" + outros + '}';
    }
    
}
