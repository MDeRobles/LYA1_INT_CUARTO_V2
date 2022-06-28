/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * 
 */
public class Gram2 extends Editor{
    private static String text;

    //------------------------------Inicio de ide-----------------------------------------------------------
    public static void ide(Token[] t, int i){
        String cad = t[i].getTk();
        int c = cad.charAt(0);
        text = Sugerencia.txtPProd.getText();

        if((c >= 65 && c <= 90) || (c >= 97 && c <= 122)){
           Sugerencia.txtPProd.setText(text + "S ::= " + cad.charAt(0) + " A\n");
            for(int j=1; j<cad.length(); j++){
                c=cad.charAt(j);
                text = Sugerencia.txtPProd.getText();
                if(!((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) && c != '_' && !(c>=48 && c<=57)){
                    Sugerencia.txtPProd.setText(text + "A ::= " + cad.charAt(j) +" <- ERROR");
                    return;
                }
                Sugerencia.txtPProd.setText(text + "A ::= " + cad.charAt(j) + " A\n");
            }
        }else{
            Sugerencia.txtPProd.setText(text + "S ::= " + cad.charAt(0) + " <-- ERROR");
        }
    }
    //--------------------------------Fin de ide-----------------------------------------------------------

    //------------------------------Inicio de exp-----------------------------------------------------------
    public static int expE(Token[] t, int i){
        try{
            text = Sugerencia.txtPProd.getText();
            if(i<0) return -1;
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            Sugerencia.txtPProd.setText(text + "E ::= T\n");
            i = expT(t,i);
            if(i<0) return -1;
            if(t[i].getTk().equals("+") || t[i].getTk().equals("-")){
                i++;
                text = Sugerencia.txtPProd.getText();
                Sugerencia.txtPProd.setText(text + "E ::= T " + t[i-1].getTk() + " E\n");
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
            text = Sugerencia.txtPProd.getText();
            if(i<0) return -1;
            Sugerencia.txtPProd.setText(text + "T ::= F\n");
            i = expF(t,i);
            if(i<0) return -1;
            if(t[i].getTk().equals("*") || t[i].getTk().equals("/")){
                i++;
                text = Sugerencia.txtPProd.getText();
                Sugerencia.txtPProd.setText(text + "T ::= F " + t[i-1].getTk() + " T\n");
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
            text = Sugerencia.txtPProd.getText();
            if(i<0) return -1; 
            if(t[i].getTipo().equals("Entero") || t[i].getTipo().equals("Decimal") || t[i].getTipo().equals("Identificador")){
                Sugerencia.txtPProd.setText(text + "F ::= " + t[i].getTk()+ "\n");
                i++;
                return i;
            }else if(t[i].getTk().equals("(")){
                Sugerencia.txtPProd.setText(text + "F ::= ( E )\n");
                i++;
                i = expE(t,i);
                if(i<0) return -1;
                if(t[i].getTk().equals(")")){
                    i++;
                    if(t[i+1].getTipo().equals("Operador") && !t[i+1].getTk().equals("=")) i++;
                    return i;
                }else {
                    text = Sugerencia.txtPProd.getText();
                    Sugerencia.txtPProd.setText(text + "F ::= ( E <- ERROR\n");
                    return -1;
                }
                
            }else {
                Sugerencia.txtPProd.setText(text + "F ::= <- ERROR\n");
                return -1;
            }
        }catch(NullPointerException e){
            return i-1;
        }
    }
//-------------------------------------Fin de exp--------------------------------------------------

//-------------------------------------Inicio de decla---------------------------------------------
    public static int decla(Token[] t, int i){
        try{
            text = Sugerencia.txtPProd.getText();
            if(i<0) return -1;
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            //System.out.println(t[i]);
            if(t[i].getTk().equals(":")){
                i++;
                Sugerencia.txtPProd.setText(text + "S ::= "+ t[i-2].getTk() +" : A\n");
                i = declaA(t,i);
                if(i<=0) return -1;
            }else {Sugerencia.txtPProd.setText(text + "S ::= <- ERROR"); return -1;}
        }catch(NullPointerException e){
            return -1;
        }
        return i;
    }

    public static int declaA(Token[] t, int i){
        j--;
        try{
            text = Sugerencia.txtPProd.getText();
            if(i<0) return -1;
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            if(t[i].getTipo().equals("Identificador")){
                Sugerencia.txtPProd.setText(text + "A ::= " + t[i].getTk() + " B\n");
                i++;
                if(t[i].getTk().equals(",") || t[i].getTk().equals("=")){
                    i = declaB(t,i);
                    if(i<0) return -1;
                }else if (t[i].getTk().equals(";")){
                    text = Sugerencia.txtPProd.getText();
                    Sugerencia.txtPProd.setText(text + "B ::= <- ERROR");
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

    public static int declaB(Token[] t, int i){
        try{
            text = Sugerencia.txtPProd.getText();
            if(i<0) return -1;
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            if(t[i].getTk().equals(",")){
                Sugerencia.txtPProd.setText(text + "B ::= , A\n");
                i++; j++;
                i = declaA(t,i);
                if(i<=0) return 0;
            }else if(t[i].getTk().equals("=")){
                //Sugerencia.txtPProd.setText(text + "B ::= = <val> C\n");
                text = Sugerencia.txtPProd.getText();
                i++;
                if(Identificador.comp(I[j].getVal(), I[j].getTipo()) == false){
                    Sugerencia.txtPProd.setText(text + "B ::= = <val> <- ERROR");
                    return -1;
                }
                if (t[i].getTipo().equals("Cadena") || t[i].getTk().equals("true") || t[i].getTk().equals("false")){
                    i++;
                    text = Sugerencia.txtPProd.getText();
                    if(Identificador.comp(I[j].getVal(), I[j].getTipo()) == false){
                        Sugerencia.txtPProd.setText(text + "B ::= = " + t[i].getTk() +" <- ERROR\n");
                    }
                    if(t[i].getTk().equals(",")){
                        Sugerencia.txtPProd.setText(text + "C ::= , A\n");
                        i++; j++;
                        i = declaA(t,i);
                        if(i<0) return -1;
                        return i;
                    }else if(t[i].getTk().equals(";")){
                        Sugerencia.txtPProd.setText(text + "C ::= ;\n");
                        j++;
                        return i;
                    }else {Sugerencia.txtPProd.setText(text + "C ::= <- ERROR\n"); return -1;}
                }else if(expE(t,i) >= 0){
                    i=expE(t,i);
                    text = Sugerencia.txtPProd.getText();
                    if(t[i].getTk().equals(",")){
                        Sugerencia.txtPProd.setText(text + "C ::= , A\n");
                        i++; j++;
                        i = declaA(t,i);
                        if(i<0) return -1;
                        return i;
                    }else if(t[i].getTk().equals(";")){
                        Sugerencia.txtPProd.setText(text + "C ::= ;\n");
                        return i;
                    }else return -1;
                }else {Sugerencia.txtPProd.setText(text + "B ::= = <val> <- ERROR");return -1;}
            }else {Sugerencia.txtPProd.setText(text + "B ::= = <- ERROR"); return -1;}
        }catch(NullPointerException e){
            Sugerencia.txtPProd.setText(text + "B ::= = <val> <- ERROR");
            return -1;
        }
        return i;
    }
//----------------------------------------Fin de decla----------------------------------------------------


//----------------------------------------Inicio de Alexa------------------------------------------------
    public static boolean alexa(Token[] t, int i){
        String pr = "";
        try{
            text = Sugerencia.txtPProd.getText();
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            Sugerencia.txtPProd.setText(text + "q1 = Alexa\n");
            text = Sugerencia.txtPProd.getText();
            if(t[i++].getTk().equals("(")){
                Sugerencia.txtPProd.setText(text + "q2 = Alexa(\n");
                text = Sugerencia.txtPProd.getText();
                if(t[i].getTipo().equals("Palabra reservada")){
                    pr = t[i].getTk();
                    Sugerencia.txtPProd.setText(text + "q3 = Alexa("+pr+"\n");
                    text = Sugerencia.txtPProd.getText();
                    i++;
                    if(t[i++].getTk().equals(",")){
                        Sugerencia.txtPProd.setText(text + "q4 = Alexa("+pr+", \n");
                        text = Sugerencia.txtPProd.getText();
                        if(t[i].getTipo().equals("Identificador")){
                            if(Identificador.buscarId(t[i].getTk()) == -1){
                                Sugerencia.txtPProd.setText(text + "q5 = Alexa("+pr+", "+ t[i].getTk() +" <-- ERROR\n");
                                text = Sugerencia.txtPProd.getText();
                                //Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Identificador no encontrado.",t,k);
                                return false;
                            }else {
                                if(pr.equals("encender") || pr.equals("prende") || pr.equals("prender") || pr.equals("enciende") || pr.equals("apaga") || pr.equals("apagar")){
                                    if(!(I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Luz") || I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Bocina") || I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Camara") || I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Aspersor") )){
                                        Sugerencia.txtPProd.setText(text + "q5 = Alexa("+pr+", "+ t[i].getTk() +" <-- ERROR\n");
                                        text = Sugerencia.txtPProd.getText();
                                        return false;
                                    }
                                }else if(pr.equals("lanza") || pr.equals("lanzar")){
                                    if(!(I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Premio") || I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Juguete"))){
                                        Sugerencia.txtPProd.setText(text + "q5 = Alexa("+pr+", "+ t[i].getTk() +" <-- ERROR\n");
                                        text = Sugerencia.txtPProd.getText();
                                        return false;
                                    }
                                }else if(pr.equals("abrir") || pr.equals("abre") || pr.equals("cierra") || pr.equals("cerrar")){
                                    if(!(I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Puerta"))){
                                        Sugerencia.txtPProd.setText(text + "q5 = Alexa("+pr+", "+ t[i].getTk() +" <-- ERROR\n");
                                        text = Sugerencia.txtPProd.getText();
                                        return false;
                                    }
                                }else if(pr.equals("aumentar") || pr.equals("disminuir")){
                                    if(!(I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Temperatura"))){
                                        Sugerencia.txtPProd.setText(text + "q5 = Alexa("+pr+", "+ t[i].getTk() +" <-- ERROR\n");
                                        text = Sugerencia.txtPProd.getText();
                                        return false;
                                    }
                                }else{
                                    Sugerencia.txtPProd.setText(text + "q3 = Alexa( <-- ERROR\n");
                                    text = Sugerencia.txtPProd.getText();
                                    return false;
                                }
                            }
                            i++;
                            if(t[i++].getTk().equals(")")){
                                Sugerencia.txtPProd.setText(text + "q6 = Alexa("+pr+", "+ t[i].getTk() +")\n");
                                text = Sugerencia.txtPProd.getText();
                                if(!t[i].getTk().equals(";")){
                                    Sugerencia.txtPProd.setText(text + "q7 = Alexa("+pr+", "+ t[i].getTk() +") <-- ERROR\n");
                                    text = Sugerencia.txtPProd.getText();
                                    //Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Falta punto y coma",t,k);
                                    return false;                                
                                }
                                return true;
                            }else {
                                //Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Falta paréntesis que cierra.",t,k); 
                                Sugerencia.txtPProd.setText(text + "q6 = Alexa("+pr+", "+ t[i].getTk() +" <-- ERROR\n");
                                text = Sugerencia.txtPProd.getText();
                                return false;
                            }
                        }else{Sugerencia.txtPProd.setText(text + "q5 = Alexa(" + pr + ", <- ERROR\n");}
                    }else{Sugerencia.txtPProd.setText(text + "q4 = Alexa(" + pr + " <- ERROR\n");}
                }else{Sugerencia.txtPProd.setText(text + "q3 = Alexa( <- ERROR\n");}
            }else {
                Sugerencia.txtPProd.setText(text + "q2 = Alexa <- ERROR\n");
                text = Sugerencia.txtPProd.getText();
                //Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Falta paréntesis que abre.",t,k3); 
                return false;
            }
        }catch(NullPointerException e){
            return false;
        }
        return false;
    }
//-----------------------------Fin de alexa----------------------------------------------------------

//-------------------------------IF--------------------------------------------
     public static boolean EsIF(Token[] t, int i){
        try{
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
                if(t[i++].getTk().equals("(")){
                    if(t[i++].getTipo().equals("Identificador")){
                        if(t[i].getTk().equals(">") || t[i].getTk().equals("<")|| t[i].getTk().equals("=")){
                            i++;
                            if(t[i].getTipo().equals("Identificador") || t[i].getTipo().equals("Entero") || t[i].getTipo().equals("Decimal")){
                                i++;
                                if(t[i].getTk().equals(")")){
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
