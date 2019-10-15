/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jeand
 */
public class PessoaJuridica {   
   private final List<Map<String,String>> dados;

    public PessoaJuridica() {
        this.dados = new ArrayList<>();
    }   
   
    public void addDados(String texto, String descritivo){
        Map<String,String> map_dados = new HashMap<>();
        map_dados.put("texto", texto);
        map_dados.put("descritivo", descritivo);
        this.dados.add(map_dados);
    }

    public List<Map<String, String>> getDados() {
        return dados;
    }
   
    public boolean existe_dados(){
        return this.dados.size() > 0;
    }
   
}
