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
public class ParticipacaoLicitacao {
    String skLicitacao, orgao, unidadeGestora,numeroLicitacao, dataAbertura;

    public String getSkLicitacao() {
        return skLicitacao;
    }

    public void setSkLicitacao(String skLicitacao) {
        this.skLicitacao = skLicitacao;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getUnidadeGestora() {
        return unidadeGestora;
    }

    public void setUnidadeGestora(String unidadeGestora) {
        this.unidadeGestora = unidadeGestora;
    }

    public String getNumeroLicitacao() {
        return numeroLicitacao;
    }

    public void setNumeroLicitacao(String numeroLicitacao) {
        this.numeroLicitacao = numeroLicitacao;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    @Override
    public String toString() {
        return "ParticipacaoLicitacao{" + "skLicitacao=" + skLicitacao + ", orgao=" + orgao + ", unidadeGestora=" + unidadeGestora + ", numeroLicitacao=" + numeroLicitacao + ", dataAbertura=" + dataAbertura + '}';
    }
    
    
    
}
