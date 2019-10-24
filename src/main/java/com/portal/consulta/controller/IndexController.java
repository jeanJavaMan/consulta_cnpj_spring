/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta.controller;

import com.portal.consulta.model.DocumentoLicitacao;
import com.portal.consulta.model.Licitacoes;
import com.portal.consulta.model.PortalTransparencia;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jeanderson
 */
//@RestController
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/pesquisa", method = RequestMethod.GET)
    public ModelAndView pesquisa(@RequestParam("pesquisa") String pesquisa) {
        PortalTransparencia p = new PortalTransparencia();
        try {
            if (p.buscarDados(pesquisa)) {
                ModelAndView mv = new ModelAndView("pesquisa");
                mv.addObject("dados", p);
                return mv;
            } else {
                return new ModelAndView("index");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ModelAndView("index");
        }
    }

    @RequestMapping(value = "/licitacao", method = RequestMethod.GET)
    public ModelAndView licitacao(@RequestParam("licitacao") String licitacao) {
        Licitacoes l = new Licitacoes(licitacao);
        try {
            l.carregar_dados();
            ModelAndView mv = new ModelAndView("licitacao");
            mv.addObject("licitacao", l);
            return mv;
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return new ModelAndView("index");
        }
    }

    @RequestMapping(value = "/licitacoes", method = RequestMethod.GET)
    @ResponseBody
    public String licitacoes_ajax(@RequestParam Map<String, String> allParams) {
        try {
            String coluna_ordem = allParams.get("order[0][column]");
            String id = allParams.get("id");
            if (coluna_ordem.equals("0")) {
                coluna_ordem = "1";
            }
            String coluna = allParams.get("columns[" + coluna_ordem + "][data]");
            String url = "http://www.portaltransparencia.gov.br/pessoa-juridica/" + id + "/participante-licitacao/resultado?paginacaoSimples=false&tamanhoPagina=" + allParams.get("length") + "&offset=" + allParams.get("start") + "&direcaoOrdenacao=" + allParams.get("order[0][dir]") + "&colunaOrdenacao=" + coluna + "&id=" + id;
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
            String json_resposta = doc.body().text();
            return json_resposta;
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return "{}";
        }
    }

    @RequestMapping(value = "/licitacao/itens", method = RequestMethod.GET)
    @ResponseBody
    public String licitacoes_itens(@RequestParam Map<String, String> allParams) {
        try {
            String coluna_ordem = allParams.get("order[0][column]");
            String id = allParams.get("id_compra");
            String coluna = allParams.get("columns[" + coluna_ordem + "][data]");
            String url = "http://www.portaltransparencia.gov.br/licitacoes/item-licitacao/resultado?paginacaoSimples=false&tamanhoPagina=" + allParams.get("length") + "&offset=" + allParams.get("start") + "&direcaoOrdenacao=" + allParams.get("order[0][dir]") + "&colunaOrdenacao=" + coluna + "&skCompra=" + id;
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
            String json_resposta = doc.body().text();
            return json_resposta;
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return "{}";
        }
    }

    @RequestMapping(value = "/licitacao/participantes", method = RequestMethod.GET)
    @ResponseBody
    public String licitacoes_participantes(@RequestParam Map<String, String> allParams) {
        try {
            String coluna_ordem = allParams.get("order[0][column]");
            String id = allParams.get("id_compra");
            String coluna = allParams.get("columns[" + coluna_ordem + "][data]");
            String url = "http://www.portaltransparencia.gov.br/licitacoes/participantes/resultado?paginacaoSimples=false&tamanhoPagina=" + allParams.get("length") + "&offset=" + allParams.get("start") + "&direcaoOrdenacao=" + allParams.get("order[0][dir]") + "&colunaOrdenacao=" + coluna + "&skCompra=" + id;
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
            String json_resposta = doc.body().text();
            return json_resposta;
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return "{}";
        }
    }

    @RequestMapping(value = "/licitacao/empenho", method = RequestMethod.GET)
    @ResponseBody
    public String licitacoes_empenho(@RequestParam Map<String, String> allParams) {
        try {
            String coluna_ordem = allParams.get("order[0][column]");
            String id = allParams.get("id_compra");
            String coluna = allParams.get("columns[" + coluna_ordem + "][data]");
            String url = "http://www.portaltransparencia.gov.br/licitacoes/empenhos/resultado?paginacaoSimples=true&tamanhoPagina="+allParams.get("length")+"&offset="+allParams.get("start")+"&direcaoOrdenacao="+allParams.get("order[0][dir]")+"&colunaOrdenacao="+coluna+"&skCompra="+id;
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
            String json_resposta = doc.body().text();
            return json_resposta;
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return "{}";
        }
    }
    
    @RequestMapping(value = "/licitacao/documento", method = RequestMethod.GET)
    public ModelAndView documento_licitacao(@RequestParam("doc") String documento){
        ModelAndView model = new ModelAndView("documento");
        DocumentoLicitacao doc = new DocumentoLicitacao(documento);
        try {
            doc.carregar_dados();
            model.addObject("doc", doc);
            return model;
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return new ModelAndView("index");
        }
    }
    
    @RequestMapping(value = "/licitacao/documento/detalhamento", method = RequestMethod.GET)
    @ResponseBody
    public String documento_licitacao_detalhamento(@RequestParam Map<String, String> allParams) {
        try {
            String coluna_ordem = allParams.get("order[0][column]");
            String id = allParams.get("id");
            String coluna = allParams.get("columns[" + coluna_ordem + "][data]");
            String url = "http://www.portaltransparencia.gov.br/despesas/documento/empenho/detalhamento/resultado?paginacaoSimples=true&tamanhoPagina="+allParams.get("length")+"&offset="+allParams.get("start")+"&direcaoOrdenacao="+allParams.get("order[0][dir]")+"&colunaOrdenacao="+coluna+"&codigo="+id;

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
            String json_resposta = doc.body().text();
            return json_resposta;
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return "{}";
        }
    }
    
    @RequestMapping(value = "/licitacao/documento/relacionados", method = RequestMethod.GET)
    @ResponseBody
    public String documento_licitacao_relacionados(@RequestParam Map<String, String> allParams) {
        try {
            String coluna_ordem = allParams.get("order[0][column]");
            String id = allParams.get("id");
            String coluna = allParams.get("columns[" + coluna_ordem + "][data]");
            String url = "http://www.portaltransparencia.gov.br/despesas/documento/documentos-relacionados/resultado?paginacaoSimples=true&tamanhoPagina="+allParams.get("length")+"&offset="+allParams.get("start")+"&direcaoOrdenacao="+allParams.get("order[0][dir]")+"&colunaOrdenacao="+coluna+"&fase=Empenho&codigo="+id;

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36").timeout(60 * 1000).ignoreContentType(true).get();
            String json_resposta = doc.body().text();
            return json_resposta;
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return "{}";
        }
    }
}
