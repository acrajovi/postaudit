/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sourse;

import java.awt.event.KeyEvent;

/**
 *
 * @author TOJOZ
 */
public class utilities {

    /**
     *
     * @param tecla KeyEvent
     * @return Retorna TRUE si se presionó la tecla ENTER
     */
    public boolean ifEnter(KeyEvent tecla) {
        return tecla.getKeyCode() == 10;
    }

    /**
     *
     * @param cadena
     * @return Retorna en STRING el tipo de dato introducido
     *
     * CHAR, NUMERIC, ALFANUMERIC
     */
    public String gettDataType(String cadena) {
        boolean algunDigito = false, algunaLEtra = false;
        String tipoVariable;
        if (cadena.matches("[0-9]*")) {
            algunDigito = true;
        } else if (cadena.matches("[^0-9]")) {
            algunaLEtra = true;
        }
        if (algunDigito && algunaLEtra) {
            tipoVariable = "ALFANUMERIC";
        } else if (!algunDigito) {
            tipoVariable = "CHAR";
        } else {
            tipoVariable = "NUMERIC";
        }
        return tipoVariable;
    }
/**
 * 
 * @param obj
 * @return Retorna TRUE si esta vacio o null, FALSE en caso contrario
 */
    public boolean isEmpty(Object obj) {
        return obj == null || "".equals(obj);
    }
}
