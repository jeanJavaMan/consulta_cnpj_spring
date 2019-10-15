/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jeanderson.consulta.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author jeand
 */
public class PortalTransparencia {

    private Document doc;
    private final String link_pagina_inicial, link_busca_termo;
    private String cnpj;
    private final PessoaJuridica pessoaJuridica;
    private final List<QuadroSocietario> lista_quadrosocietario;

    public PortalTransparencia() {
        this.link_pagina_inicial = "http://www.portaltransparencia.gov.br/";
        this.link_busca_termo = "http://www.portaltransparencia.gov.br/busca/resultado?termo=";
        this.pessoaJuridica = new PessoaJuridica();
        this.lista_quadrosocietario = new ArrayList<>();
    }

    public boolean buscarDados(String cnpj) throws Exception {
        this.cnpj = cnpj;
        this.doc = Jsoup.connect(link_busca_termo + cnpj).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").ignoreContentType(true).get();
        String json_resposta = doc.body().text();
        JSONObject json = new JSONObject(json_resposta);
        String link = (String) json.getJSONArray("registros").getJSONObject(0).get("link");
        this.doc = Jsoup.connect(this.link_pagina_inicial + link).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").get();
        if (this.doc.select("section.dados-tabelados").size() > 0) {
            this.pegar_dados_pessoa_juridica();
            this.pegar_quadro_societario();
            return true;
        } else {
            return false;
        }
    }

    private void pegar_quadro_societario() {
        if (this.doc.select("div.box-tabela-completa").size() > 0) {
            Elements trs = doc.select("div.box-tabela-completa > table > tbody > tr");
            for (Element tr : trs) {
                QuadroSocietario q = new QuadroSocietario();
                q.setSocio(tr.getElementsByTag("td").get(0).text());
                q.setQualificacao(tr.getElementsByTag("td").get(1).text());
                this.lista_quadrosocietario.add(q);
            }
        }

    }

    private void pegar_dados_pessoa_juridica() {
        Element section = this.doc.selectFirst("section.dados-tabelados");
        Elements divs = section.select("div > div");
        for (Element div : divs) {
            if (div.getElementsByTag("span").size() > 0) {
                String descritivo = div.getElementsByTag("span").first().text();
                String texto = div.getElementsByTag("strong").first().text();
                this.pessoaJuridica.addDados(texto, descritivo);
            }
        }
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public List<QuadroSocietario> getLista_quadrosocietario() {
        return lista_quadrosocietario;
    }
    
    

}
