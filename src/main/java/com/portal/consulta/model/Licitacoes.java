/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta.model;

import com.portal.consulta.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author jeand
 */
public class Licitacoes {

    private Document doc;
    private final String codigo;
    private JSONObject licitacao;
    private final List<JSONObject> itens;
    private final List<JSONObject> participantes;
    private final List<JSONObject> empenhos;
    private String codigo_compra;

    public Licitacoes(String codigo) {
        this.codigo = codigo;
        this.codigo_compra = "0";
        this.itens = new ArrayList<>();
        this.participantes = new ArrayList<>();
        this.empenhos = new ArrayList<>();
    }

    public void carregar_dados() throws Exception {
        this.pegar_licitacao();
        this.pegar_codigo_compra();
        this.pegar_itens();
        this.pegar_participantes();
        this.pegar_empenho();
    }

    private void pegar_licitacao() throws Exception {
        this.doc = Jsoup.connect("http://www.transparencia.gov.br/api-de-dados/licitacoes/" + this.codigo).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
        String json_resposta = doc.body().text();
        this.licitacao = new JSONObject(json_resposta);
    }

    private void pegar_itens() throws Exception {
        this.doc = Jsoup.connect("http://www.portaltransparencia.gov.br/licitacoes/item-licitacao/resultado?colunasSelecionadas=descricao%2Cquantidade%2Cvalor%2CcpfCnpjVencedor%2Cnome&skCompra=" + this.codigo_compra).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
        String json_resposta = doc.body().text();
        JSONObject json = new JSONObject(json_resposta);
        JSONArray arrayJson = json.getJSONArray("data");
        for (int i = 0; i < arrayJson.length(); i++) {
            this.itens.add(arrayJson.getJSONObject(i));
        }
    }
    
    private void pegar_participantes() throws Exception {
        this.doc = Jsoup.connect("http://www.portaltransparencia.gov.br/licitacoes/participantes/resultado?colunasSelecionadas=cpfCnpj%2Cnome&skCompra=" + this.codigo_compra).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
        String json_resposta = doc.body().text();
        JSONObject json = new JSONObject(json_resposta);
        JSONArray arrayJson = json.getJSONArray("data");
        for (int i = 0; i < arrayJson.length(); i++) {
            this.participantes.add(arrayJson.getJSONObject(i));
        }
    }
    
    private void pegar_empenho() throws Exception {
        this.doc = Jsoup.connect("http://www.portaltransparencia.gov.br/licitacoes/empenhos/resultado?colunasSelecionadas=empenhoResumido%2CdataEmissao%2Cobservacao%2Cvalor&skCompra=" + this.codigo_compra).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
        String json_resposta = doc.body().text();
        JSONObject json = new JSONObject(json_resposta);
        JSONArray arrayJson = json.getJSONArray("data");
        for (int i = 0; i < arrayJson.length(); i++) {
            this.empenhos.add(arrayJson.getJSONObject(i));
        }
    }

    private void pegar_codigo_compra() {
        try {
            Document doc2 = Jsoup.connect("http://www.portaltransparencia.gov.br/licitacoes/" + this.codigo + "?ordenarPor=dataEmissao&direcao=asc").get();
            Elements scriptElements = doc2.getElementsByTag("script");
            for (Element element : scriptElements) {
                //...node.getWholeData();         
                for (DataNode data : element.dataNodes()) {
                    if (data.getWholeData().contains("data.skCompra")) {
                        String dados = data.getWholeData();
                        String valor = Util.useRegex("data\\.skCompra =.*\"(.*)\"", dados).group(1);
                        this.codigo_compra = valor;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Licitacoes.class.getName()).log(Level.SEVERE, null, ex);
            this.codigo_compra = "0";
        }

    }

    public String getCodigo() {
        return codigo;
    }

    public JSONObject getLicitacao() {
        return licitacao;
    }

    public List<JSONObject> getItens() {
        return itens;
    }

    public List<JSONObject> getParticipantes() {
        return participantes;
    }

    public List<JSONObject> getEmpenhos() {
        return empenhos;
    }
    
    

}
