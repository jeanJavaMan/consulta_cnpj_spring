/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
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
    private final RecursosRecebidos recursosRecebidos;
    private final ProdutosServicos produtosServicos;
    private final List<ParticipacaoLicitacao> lista_licitacao;
    private String id_resultado;

    public PortalTransparencia() {
        this.link_pagina_inicial = "http://www.portaltransparencia.gov.br/";
        this.link_busca_termo = "http://www.portaltransparencia.gov.br/busca/resultado?termo=";
        this.pessoaJuridica = new PessoaJuridica();
        this.lista_quadrosocietario = new ArrayList<>();
        this.recursosRecebidos = new RecursosRecebidos();
        this.produtosServicos = new ProdutosServicos();
        this.lista_licitacao = new ArrayList<>();
    }

    public boolean buscarDados(String cnpj) throws Exception {
        this.cnpj = cnpj;
        this.doc = Jsoup.connect(link_busca_termo + cnpj).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
        String json_resposta = doc.body().text();
        JSONObject json = new JSONObject(json_resposta);
        String link = (String) json.getJSONArray("registros").getJSONObject(0).get("link");
        this.doc = Jsoup.connect(this.link_pagina_inicial + link).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).get();
        if (this.doc.select("section.dados-tabelados").size() > 0) {
            this.pegar_dados_pessoa_juridica();
            this.pegar_quadro_societario();
            this.pegar_recursosRecebidos();
            this.pegar_produtos_e_servicos();
            this.pegar_licitacao();
            return true;
        } else {
            return false;
        }
    }

    private void pegar_licitacao() throws IOException, JSONException {
        if (!this.id_resultado.isEmpty()) {
            String json_resposta_licitacao = Jsoup.connect("http://www.portaltransparencia.gov.br/pessoa-juridica/"+this.id_resultado+"/participante-licitacao/resultado?paginacaoSimples=false&tamanhoPagina=200&direcaoOrdenacao=desc&colunasSelecionadas=linkDetalhamento%2Corgao%2CunidadeGestora%2CnumeroLicitacao%2CdataAbertura&id=" + this.id_resultado).ignoreContentType(true).timeout(60 * 1000).get().body().text();
            JSONObject json_licitacao = new JSONObject(json_resposta_licitacao);
            if (json_licitacao.getJSONArray("data").length() > 0) {
                JSONArray jsonArray = json_licitacao.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json_dados = jsonArray.getJSONObject(i);
                    ParticipacaoLicitacao p = new ParticipacaoLicitacao();
                    p.setSkLicitacao(Integer.toString(json_dados.getInt("skLicitacao")));
                    p.setOrgao(json_dados.getString("orgao"));
                    p.setUnidadeGestora(json_dados.getString("unidadeGestora"));
                    p.setNumeroLicitacao(json_dados.getString("numeroLicitacao"));
                    p.setDataAbertura(json_dados.getString("dataAbertura"));
                    this.lista_licitacao.add(p);
                }
            }
        }
    }

    private void pegar_produtos_e_servicos() throws JSONException, IOException {
        if (this.doc.select("input#skModulo").size() > 0) {
            String id = doc.selectFirst("input#skModulo").val();
            this.id_resultado = id;
            String json_resposta_produtos = Jsoup.connect("http://www.portaltransparencia.gov.br/pessoa-juridica/" + id + "/produtos-e-servicos").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").ignoreContentType(true).timeout(60 * 1000).get().body().text();
            JSONObject json_produtos = new JSONObject(json_resposta_produtos);
            this.produtosServicos.setBens_patrimoniais(json_produtos.getString("valorBensPatrimoniais"));
            this.produtosServicos.setServicos(json_produtos.getString("valorServicos"));
            this.produtosServicos.setObras(json_produtos.getString("valorObras"));
            this.produtosServicos.setMateriais(json_produtos.getString("valorMateriais"));
            this.produtosServicos.setOutros(json_produtos.getString("valorOutros"));
        }
    }

    private void pegar_recursosRecebidos() {
        if (doc.select("div#collapse-1 > div").size() > 0) {
            Element div = doc.selectFirst("div#collapse-1 > div");
            this.recursosRecebidos.setTexto(div.getElementsByTag("strong").first().text());
            this.recursosRecebidos.setValores(div.getElementsByTag("span").get(0).text());
            this.recursosRecebidos.setValores_sigilosos(div.getElementsByTag("span").get(1).text());
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

    public RecursosRecebidos getRecursosRecebidos() {
        return recursosRecebidos;
    }

    public ProdutosServicos getProdutosServicos() {
        return produtosServicos;
    }

    public List<ParticipacaoLicitacao> getLista_licitacao() {
        return lista_licitacao;
    }

    @Override
    public String toString() {
        return "PortalTransparencia{" + "link_pagina_inicial=" + link_pagina_inicial + ", link_busca_termo=" + link_busca_termo + ", cnpj=" + cnpj + ", pessoaJuridica=" + pessoaJuridica + ", lista_quadrosocietario=" + lista_quadrosocietario + ", recursosRecebidos=" + recursosRecebidos + ", produtosServicos=" + produtosServicos + ", lista_licitacao=" + lista_licitacao + ", id_resultado=" + id_resultado + '}';
    }
    
    

}
