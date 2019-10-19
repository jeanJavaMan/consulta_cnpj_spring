/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.consulta.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jeand
 */
public class Util {
     public static Matcher useRegex(String regex, String compile) {
        try {
            Matcher m = Pattern.compile(regex).matcher(compile);
            m.find();
            return m;
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }
}
