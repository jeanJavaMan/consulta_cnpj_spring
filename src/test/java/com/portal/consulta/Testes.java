/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta;

import com.portal.consulta.model.PortalTransparencia;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author jeand
 */
public class Testes {

    public static void main(String[] args) throws Exception {
        pegar_dados();
    }

    private static void pegar_dados() throws Exception {
        String cnpj = "07.060.438/0001-04";
        Document doc = Jsoup.connect("http://www.portaltransparencia.gov.br/busca/resultado?termo="+cnpj).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").ignoreContentType(true).get();
        String dados = doc.body().text();

        JSONObject json = new JSONObject(dados);
        String link = (String)json.getJSONArray("registros").getJSONObject(0).get("link");
        doc = Jsoup.connect("http://www.portaltransparencia.gov.br/"+link).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").get();
        if(doc.select("div.box-tabela-completa").size() > 0){
            Element tr = doc.selectFirst("div.box-tabela-completa > table > tbody > tr");
            System.out.println(tr.getElementsByTag("td").get(0).text());
            System.out.println(tr.getElementsByTag("td").get(1).text());
            
//            System.out.println(section.selectFirst("div > div:contains(Número de inscrição)").text());
        }else{
            System.out.println("Não tem");
        }
        
    }
    
    private static void teste_portal(){
        String cnpj = "07.060.438/0001-04";
        PortalTransparencia pt = new PortalTransparencia();
        try {
            if(pt.buscarDados(cnpj)){
                System.out.println(pt.getPessoaJuridica().getDados());
            }else{
                System.out.println("deu erro");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex);
        }
    }
}
