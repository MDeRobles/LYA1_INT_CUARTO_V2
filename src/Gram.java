/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lalo_
 */
public class Gram extends Editor{
    
//------------------------------Inicio de exp-----------------------------------------------------------
    public static int expE(Token[] t, int i){
        try{
            if(i<0) return -1;
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            i = expT(t,i);
            if(i<0) return -1;
            if(t[i].getTk().equals("+") || t[i].getTk().equals("-")){
                i++;
                i = expE(t,i++);
                if(i<0) return -1;
            }
            return i;
        }catch(NullPointerException e){
            return -1;
        }
    }

    public static int expT(Token[] t, int i){
        try{
            if(i<0) return -1;
            i = expF(t,i);
            if(i<0) return -1;
            if(t[i].getTk().equals("*") || t[i].getTk().equals("/")){
                i++;
                i = expT(t,i);
                if(i<0) return -1;
            }
            return i;
        }catch(NullPointerException e){
            return -1;
        }
    }

    public static int expF(Token[] t, int i){
        try{
            if(i<0) return -1; 
            if(t[i].getTipo().equals("Entero") || t[i].getTipo().equals("Decimal") || t[i].getTipo().equals("Identificador")){
                i++;
                return i;
            }else if(t[i].getTk().equals("(")){
                i++;
                i = expE(t,i);
                if(i<0) return -1;
                if(t[i].getTk().equals(")")){
                    i++;
                    if(t[i+1].getTipo().equals("Operador") && !t[i+1].getTk().equals("=")) i++;
                    return i;
                }else {
                    return -1;
                }
                
            }else return -1;
        }catch(NullPointerException e){
            return i-1;
        }
    }
//-------------------------------------Fin de exp--------------------------------------------------

//-------------------------------------Inicio de decla---------------------------------------------
    public static int decla(Token[] t, int i, String tipo){
        try{
            if(i<0) return -1;
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            if(t[i].getTk().equals(":")){
                i++;
                i = declaA(t,i,tipo);
                if(i<=0) return -1;
            }else return -1;
        }catch(NullPointerException e){
            return -1;
        }
        return i;
    }

    public static int declaA(Token[] t, int i, String tipo){
        try{
            if(i<0) return -1;
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            if(t[i].getTipo().equals("Identificador")){
/*if(Editor.exiId(t[i].getTk()) >= 0){
Editor.I[j].setVal("5");
}else{*/
                //System.out.println(j);
                Editor.I[j] = new Identificador(t[i].getTk(),"",tipo,t[i]);
                i++;
                if(t[i].getTk().equals(",") || t[i].getTk().equals("=")){
                    i = declaB(t,i,tipo);
                    if(i<0) return -1;
                }else if (t[i].getTk().equals(";")){
                    j++;
                }else {
                    return 0;
                }
            }else return 0;
        }catch(NullPointerException e){
            return -1;
        }
        return i;
    }

    public static int declaB(Token[] t, int i, String tipo){
        try{
            if(i<0) return -1;
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            if(t[i].getTk().equals(",")){
                i++; j++;
                i = declaA(t,i,tipo);
                if(i<=0) return 0;
            }else if(t[i].getTk().equals("=")){
                i++;
                Editor.I[j].setVal(t[i].getTk());
                if(expE(t,i) >= 0){
                    i=expE(t,i);
                    if(t[i].getTk().equals(",")){
                        i++; j++;
                        i = declaA(t,i,tipo);
                        if(i<0) return -1;
                        return i;
                    }else if(t[i].getTk().equals(";")){
                        return i;
                    }else return -1;
                }else if (t[i].getTipo().equals("Cadena") || t[i].getTk().equals("true") || t[i].getTk().equals("false")){
                    i++;
                    Editor.I[j].setVal(t[i].getTk());
                    if(t[i].getTk().equals(",")){
                        i++; j++;
                        i = declaA(t,i,tipo);
                        if(i<0) return -1;
                        return i;
                    }else if(t[i].getTk().equals(";")){
                        j++;
                        return i;
                    }else return -1;
                }else return -1;
            }else return -1;
        }catch(NullPointerException e){
            return -1;
        }
        return i;
    }
//----------------------------------------Fin de decla----------------------------------------------------


//----------------------------------------Inicio de Alexa------------------------------------------------
    public static boolean alexa(Token[] t, int i){
        try{
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            if(t[i++].getTk().equals("(")){
                if(t[i++].getTipo().equals("Palabra reservada")){
                    if(t[i++].getTk().equals(",")){
                        if(t[i++].getTipo().equals("Identificador")){
                            if(t[i++].getTk().equals(")")){
                                if(!t[i].getTk().equals(";")){
                                    Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Falta punto y coma");
                                    return false;                                
                                }
                                return true;
                            }else {Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Falta paréntesis que cierra."); return false;}
                        }
                    }
                }
            }else {Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Falta paréntesis que abre."); return false;}
        }catch(NullPointerException e){
            return false;
        }
        Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Parámetros incorrectos."); 
        return false;
    }
//-----------------------------Fin de alexa----------------------------------------------------------

//-------------------------------IF--------------------------------------------
     public static boolean EsIF(Token[] t, int i){
        try{
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
                if(t[i++].getTk().equals("(")){
                    if(t[i++].getTipo().equals("Identificador")){
                        if(t[i++].getTk().equals(">") || t[i].getTk().equals("<")|| t[i].getTk().equals("=")|| t[i].getTk().equals("<")){
                            if(t[i++].getTipo().equals("Identificador")){
                                if(t[i++].getTk().equals(")")){
                                    return true;
                                }
                            }
                        }
                    }
                }
        }catch(NullPointerException e){
            return false;
        }
        return false;
    }   
    
 //-----------------------------FIN IF-----------------------------------------

}
