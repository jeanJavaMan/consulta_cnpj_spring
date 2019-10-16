/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta.controller;

import com.portal.consulta.model.PortalTransparencia;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String index(){
        System.out.println("rodou");
        return "index";
    }
    
    @RequestMapping(value = "/pesquisa", method = RequestMethod.GET)
    public ModelAndView pesquisa(@RequestParam("pesquisa") String pesquisa){        
        PortalTransparencia p = new PortalTransparencia();
        try {
            if(p.buscarDados(pesquisa)){
                ModelAndView mv = new ModelAndView("pesquisa");
                mv.addObject("dados", p);
                return mv;
            }else{
               return new ModelAndView("index");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ModelAndView("index"); 
        }
    }
}
