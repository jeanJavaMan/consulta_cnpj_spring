/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author jeand
 */
public class DocumentoLicitacao {
    private String detalhamento, dados_detalhados, dados_favorecido, orgao_emitente;
    private final String codigo_empenho;
    private final String url_portal;
    private Document doc;

    public DocumentoLicitacao(String codigo_empenho) {
        this.codigo_empenho = codigo_empenho;
        this.url_portal = "http://www.portaltransparencia.gov.br/despesas/documento/empenho/"+codigo_empenho;
    }
    
    public void carregar_dados() throws Exception{
        this.doc = Jsoup.connect(url_portal).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
        this.pegar_detalhamento();
        this.pegar_dados_detalhados();
        this.pegar_dados_favorecido();
        this.pegar_dados_orgao_emitente();
    }
    
    private void pegar_detalhamento() throws Exception{
        this.detalhamento = this.doc.selectFirst("section.dados-tabelados").html();        
        
    }
    
    private void pegar_dados_detalhados() throws Exception{
        Element section = this.doc.selectFirst("section.dados-detalhados:contains(Dados detalhados do empenho) > div");
        Element  div =section.selectFirst("div.box-tabela-principal:contains(Detalhamento do Gasto)");
        if(div != null){
            div.remove();
        }
        this.dados_detalhados = section.html();
    }
    
    private void pegar_dados_favorecido() throws Exception{
        Element section = this.doc.selectFirst("section.dados-detalhados:contains(Dados do Favorecido) > div");
        Element link = section.selectFirst("a");
        if(link != null){
           link.attr("href", "/pesquisa?pesquisa="+link.text().trim()); 
        }        
        this.dados_favorecido = section.html();
    }
    
    private void pegar_dados_orgao_emitente() throws Exception{
        Element section = this.doc.selectFirst("section.dados-detalhados:contains(Dados do Órgão emitente) > div");
        Element link = section.selectFirst("a");
        if(link != null){
           link.attr("href", ""); 
        }        
        this.orgao_emitente = section.html();
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public String getDados_detalhados() {
        return dados_detalhados;
    }

    public String getCodigo_empenho() {
        return codigo_empenho;
    }    

    public String getDados_favorecido() {
        return dados_favorecido;
    }

    public String getOrgao_emitente() {
        return orgao_emitente;
    }
    
    
    
    
}
