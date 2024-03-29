/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta;

import com.portal.consulta.model.ParticipacaoLicitacao;
import com.portal.consulta.model.PortalTransparencia;
import com.portal.consulta.util.Util;
import org.json.JSONArray;
import org.json.JSONException;
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
public class Testes {

    public static void main(String[] args) throws Exception {
        teste_script();
    }

    private static void pegar_dados() throws Exception {
        String cnpj = "07.060.438/0001-04";
        Document doc = Jsoup.connect("http://www.portaltransparencia.gov.br/busca/resultado?termo=" + cnpj).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").ignoreContentType(true).get();
        String dados = doc.body().text();

        JSONObject json = new JSONObject(dados);
        String link = (String) json.getJSONArray("registros").getJSONObject(0).get("link");
        doc = Jsoup.connect("http://www.portaltransparencia.gov.br/" + link).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").get();
        if (doc.select("input#skModulo").size() > 0) {
            String id = doc.selectFirst("input#skModulo").val();
            String json_resposta_licitacao = Jsoup.connect("http://www.portaltransparencia.gov.br/pessoa-juridica/" + id + "/participante-licitacao/resultado?paginacaoSimples=true&tamanhoPagina=15&offset=0&direcaoOrdenacao=desc&colunaOrdenacao=numeroLicitacao&colunasSelecionadas=linkDetalhamento%2Corgao%2CunidadeGestora%2CnumeroLicitacao%2CdataAbertura&id=" + id).ignoreContentType(true).get().body().text();
            System.out.println(json_resposta_licitacao);
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
                    System.out.println(p);
                }
            }
//            System.out.println(section.selectFirst("div > div:contains(Número de inscrição)").text());
        } else {
            System.out.println("Não tem");
        }

    }

    private static void teste_portal() {
        String cnpj = "07.060.438/0001-04";
        PortalTransparencia pt = new PortalTransparencia();
        try {
            if (pt.buscarDados(cnpj)) {
                System.out.println(pt);
            } else {
                System.out.println("deu erro");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }
    }

    private static void teste_json() throws JSONException {
        String json = "{\n"
                + "  'id': 239715809,\n"
                + "  'dataResultadoCompra': '04/11/2014',\n"
                + "  'dataAbertura': null,\n"
                + "  'dataReferencia': '04/11/2014',\n"
                + "  'dataPublicacao': '01/01/1900',\n"
                + "  'situacaoCompra': {\n"
                + "    'codigo': 106,\n"
                + "    'descricao': 'Encerrado'\n"
                + "  },\n"
                + "  'modalidadeLicitacao': {\n"
                + "    'descricao': 'Dispensa de Licitação'\n"
                + "  },\n"
                + "  'instrumentoLegal': null,\n"
                + "  'valor': 14986.28,\n"
                + "  'municipio': {\n"
                + "    'codigoIBGE': '3303302',\n"
                + "    'nomeIBGE': 'NITERÓI',\n"
                + "    'pais': 'BRASIL',\n"
                + "    'uf': {\n"
                + "      'sigla': 'RJ',\n"
                + "      'nome': 'RIO DE JANEIRO'\n"
                + "    }\n"
                + "  },\n"
                + "  'unidadeGestora': {\n"
                + "    'codigo': '150182',\n"
                + "    'nome': 'PRÓ-REITORIA DE ADMINISTRAÇÃO',\n"
                + "    'descricaoPoder': 'EXECUTIVO',\n"
                + "    'orgaoVinculado': {\n"
                + "      'codigoSIAFI': '26236',\n"
                + "      'cnpj': '',\n"
                + "      'nome': 'Universidade Federal Fluminense',\n"
                + "      'naturezaJuridica': {\n"
                + "        'codigo': '0000    ',\n"
                + "        'descricao': 'Sem informação',\n"
                + "        'codigoTipo': '0 ',\n"
                + "        'descricaoTipo': 'Sem informação'\n"
                + "      },\n"
                + "      'sigla': ''\n"
                + "    },\n"
                + "    'orgaoMaximo': {\n"
                + "      'codigo': '26000',\n"
                + "      'nome': 'Ministério da Educação',\n"
                + "      'sigla': ''\n"
                + "    },\n"
                + "    'existeFatoDespesa': false,\n"
                + "    'existeFatoReceita': false\n"
                + "  },\n"
                + "  'licitacao': {\n"
                + "    'numero': '011312014',\n"
                + "    'objeto': 'Objeto: Instalação de placa',\n"
                + "    'numeroProcesso': '23069051165201464',\n"
                + "    'contatoResponsavel': 'LEONARDO VARGAS DA SILVA'\n"
                + "  }\n"
                + "}";
        JSONObject jsonObj = new JSONObject(json);
        System.out.println(jsonObj.getJSONObject("unidadeGestora").getJSONObject("orgaoMaximo").getString("nome"));
    }

    private static void teste_script() throws Exception {
        Document doc = Jsoup.connect("http://www.portaltransparencia.gov.br/licitacoes/239715809?ordenarPor=dataEmissao&direcao=asc").get();
        Elements scriptElements = doc.getElementsByTag("script");
        for (Element element : scriptElements) {
            //...node.getWholeData();         
            for (DataNode data : element.dataNodes()) {
                if (data.getWholeData().contains("data.skCompra")) {
                    String dados = data.getWholeData();
                    String valor = Util.useRegex("data\\.skCompra =.*\"(.*)\"", dados).group(1);
                    System.out.println(valor);
                }
            }
        }
    }
}
