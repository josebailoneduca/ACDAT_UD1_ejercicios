/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package ejercicio15.dto;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public class ResultadoOperacion {
    private boolean ok;
    private String msg;

    public ResultadoOperacion(boolean ok, String msg) {
        this.ok = ok;
        this.msg = msg;
    }

    public boolean isOk() {
        return ok;
    }

    public String getMsg() {
        return msg;
    }
    
    
}//end ResultadoOperaciones
