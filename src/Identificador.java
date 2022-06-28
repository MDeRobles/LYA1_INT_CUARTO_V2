/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * 
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

    public static int buscarId(String id){
        int j = 0;
        try{
            while(!Editor.I[j].equals("null")){
                if(Editor.I[j].getId().equals(id)){
                    return j;
                }
                j++;
            }
        }catch(NullPointerException e){
            return -1;
        }
        return -1;
    }

    public static Identificador buscarId(String id, int l, int c, String msg, Token[] t, int k){
        int j = 0;
        try{
            while(!Editor.I[j].equals("null")){
                if(Editor.I[j].getId().equals(id)){
                    return Editor.I[j];
                }
                j++;
            }
        }catch(NullPointerException e){
            return null;
        }
        return null;
    }

    public static boolean comp(String val, String tipo){
        if(val.equals("")) return true;
        if(tipo.equals("Entero") && Grafos.esInt(val)) return true;
        else if(tipo.equals("Decimal") && Grafos.esDecimal(val)) return true;
        else if((tipo.equals("Boolean") || tipo.equals("Luz") || tipo.equals("Bocina") || tipo.equals("Camara")||tipo.equals("Aspersor")||tipo.equals("Juguete")||tipo.equals("Premio")||tipo.equals("Puerta")) && (val.equals("true") || val.equals("false"))) return true;
        return false;
    }

}
