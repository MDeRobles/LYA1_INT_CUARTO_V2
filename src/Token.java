/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eduardo
 */
public class Token {
    private String tk, tipo;
    private int linea, caracter;
    public Token(){
    }
    public Token(String tk, String tipo, int linea, int caracter) {
        this.tk = tk;
        this.tipo = tipo;
        this.linea = linea;
        this.caracter = caracter;
    }
    public String getTk() {
        return tk;
    }
    public void setTk(String tk) {
        this.tk = tk;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getCaracter() {
        return caracter;
    }

    public void setCaracter(int caracter) {
        this.caracter = caracter;
    }
    public String toString() {
        return tk + ", " + tipo;
    }
}
