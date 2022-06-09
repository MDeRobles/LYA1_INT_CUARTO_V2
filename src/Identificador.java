/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lalo_
 */
public class Identificador {
    private String id, val, tipo;
    private Token t;

    public Identificador() {
    }
    
    public Identificador(String id, String val, String tipo, Token t) {
        this.id = id;
        this.val = val;
        this.tipo = tipo;
        this.t = t;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Token getT() {
        return t;
    }

    public void setT(Token t) {
        this.t = t;
    }

    public String toString() {
        return id + ", " + val + ", " + tipo;
    }

}
