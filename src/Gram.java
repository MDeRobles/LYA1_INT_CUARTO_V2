/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 *
 */
public class Gram extends Editor{
    private static int k1=0,k2=0,k3=0;

//------------------------------Inicio de exp-----------------------------------------------------------
    public static int expE(Token[] t, int i){
        try{
            k1=i;
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
                if(t[i].getTipo().equals("Identificador") && Identificador.buscarId(t[i].getTk()) == -1){
                    //Editor.escError(t[i].getLinea(),t[i].getCaracter(),"Identificador no encontrado.",t,0);
                    return i;
                }
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
            k2=i;
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
                /*if(Editor.exiId(t[i].getTk()) != -1){
                    Editor.escError(t[i].getLinea(),t[i].getCaracter(),"El identificador ya existe.");
                    return -1;
                }*/
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
                if(Identificador.comp(I[j].getVal(), I[j].getTipo()) == false){
                    escError(I[j].getT().getLinea(),I[j].getT().getCaracter(),"Valor incompatible.",t,k2);
                }
                if (t[i].getTipo().equals("Cadena") || t[i].getTk().equals("true") || t[i].getTk().equals("false")){
                    i++;
                    Editor.I[j].setVal(t[i-1].getTk());
                    if(Identificador.comp(I[j].getVal(), I[j].getTipo()) == false){
                        escError(I[j].getT().getLinea(),I[j].getT().getCaracter(),"Valor incompatible.",t,k2);
                    }
                    if(t[i].getTk().equals(",")){
                        i++; j++;
                        i = declaA(t,i,tipo);
                        if(i<0) return -1;
                        return i;
                    }else if(t[i].getTk().equals(";")){
                        j++;
                        return i;
                    }else return -1;
                }else if(expE(t,i) >= 0){
                    i=expE(t,i);
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
        String pr = "";
        try{
            k3=i;
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            if(t[i++].getTk().equals("(")){
                if(t[i].getTipo().equals("Palabra reservada")){
                    pr = t[i].getTk();
                    i++;
                    if(t[i++].getTk().equals(",")){
                        if(t[i].getTipo().equals("Identificador")){
                            if(Identificador.buscarId(t[i].getTk()) == -1){
                                Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Identificador no encontrado.",t,k3,"Alexa");
                                return false;
                            }else {
                                if(pr.equals("encender") || pr.equals("prende") || pr.equals("prender") || pr.equals("enciende") || pr.equals("apaga") || pr.equals("apagar")){
                                    if(!(I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Luz") || I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Bocina") || I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Camara") || I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Aspersor") )){
                                        Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Identificador incompatible.",t,k3,"Alexa");
                                        return false;
                                    }
                                }else if(pr.equals("lanza") || pr.equals("lanzar")){
                                    if(!(I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Premio") || I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Juguete"))){
                                        Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Identificador incompatible.",t,k3,"Alexa");
                                        return false;
                                    }
                                }else if(pr.equals("abrir") || pr.equals("abre") || pr.equals("cierra") || pr.equals("cerrar")){
                                    if(!(I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Puerta"))){
                                        Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Identificador incompatible.",t,k3,"Alexa");
                                        return false;
                                    }
                                }else if(pr.equals("aumentar") || pr.equals("disminuir")){
                                    if(!(I[Identificador.buscarId(t[i].getTk())].getTipo().equals("Temperatura"))){
                                        Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Identificador incompatible.",t,k3,"Alexa");
                                        return false;
                                    }
                                }else{
                                    Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Parámetros incorrectos.",t,k3);
                                    return false;
                                }
                            }
                            i++;
                            if(t[i++].getTk().equals(")")){
                                if(!t[i].getTk().equals(";")){
                                    Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Falta punto y coma",t,k3,"Alexa");
                                    return false;                                
                                }
                                
                                //Hablar
                                String T1, T2, T3, T4, T5, T6, T7, Alexa;
                                String I1;

                                T1 = "";
                                T1 = T1 + t[i - 6].getTk();

                                T2 = "";
                                T2 = T2 + t[i - 5].getTk();

                                T3 = "";
                                T3 = T3 + t[i - 4].getTk();

                                T4 = "";
                                T4 = T4 + t[i - 3].getTk();

                                T5 = "";
                                T5 = T5 + t[i - 2].getTk();

                                T6 = "";
                                T6 = T6 + t[i - 1].getTk();

                                T7 = "";
                                T7 = T7 + t[i].getTk();

                                Alexa = T1 + T2 + T3 + T4 + T5 + T6 + T7;

                                String E[] = {"encender","prende","prender","enciende"};
                                String A[] = {"apaga","apagar"};
                                String C[] = {"cierra","cerrar"};
                                String Ab[] = {"abrir","abre"};
                                String L[] = {"lanza","lanzar"};
                                
                                System.out.println(Alexa);

                                //Encender <-----
                                //Luz
                                for (int j = 0; j < E.length; j++) {
                                    if (Alexa.equals("Alexa(" + E[j] + ",luz1);")) {
                                        hablar("Encendiendo Luz 1");
                                    } else {
                                        if (Alexa.equals("Alexa(" + E[j] + ",luz2);")) {
                                            hablar("Encendiendo Luz 2");
                                        } else {
                                            if (Alexa.equals("Alexa(" + E[j] + ",luz3);")) {
                                                hablar("Encendiendo Luz 3");
                                            }
                                        }

                                    }
                                }

                                //Bocina
                                for (int j = 0; j < E.length; j++) {
                                    if (Alexa.equals("Alexa(" + E[j] + ",bocina1);")) {
                                        hablar("Encendiendo Bocina 1");
                                    } else {
                                        if (Alexa.equals("Alexa(" + E[j] + ",bocina2);")) {
                                            hablar("Encendiendo Bocina 2");
                                        } else {
                                            if (Alexa.equals("Alexa(" + E[j] + ",bocina3);")) {
                                                hablar("Encendiendo Bocina 3");
                                            }
                                        }

                                    }
                                }
                                //Camara
                                for (int j = 0; j < E.length; j++) {
                                    if (Alexa.equals("Alexa(" + E[j] + ",camara1);")) {
                                        hablar("Encendiendo camara 1");
                                    } else {
                                        if (Alexa.equals("Alexa(" + E[j] + ",camara2);")) {
                                            hablar("Encendiendo camara 2");
                                        } else {
                                            if (Alexa.equals("Alexa(" + E[j] + ",camara3);")) {
                                                hablar("Encendiendo camara 3");
                                            }
                                        }

                                    }
                                }
                                //Aspersor
                                for (int j = 0; j < E.length; j++) {
                                    if (Alexa.equals("Alexa("+E[j]+",aspersor1);")) {
                                        hablar("Encendiendo aspersor 1");
                                    } else {
                                        if (Alexa.equals("Alexa(" + E[j] + ",aspersor2);")) {
                                            hablar("Encendiendo aspersor 2");
                                        } else {
                                            if (Alexa.equals("Alexa(" + E[j] + ",aspersor3);")) {
                                                hablar("Encendiendo aspersor 3");
                                            }
                                        }

                                    }
                                }
                                //Apagar <-----
                                //Luz
                                
                                for (int j = 0; j < A.length; j++) {
                                    if (Alexa.equals("Alexa(" + A[j] + ",luz1);")) {
                                        hablar("Apagando Luz 1");
                                    } else {
                                        if (Alexa.equals("Alexa(" + A[j] + ",luz2);")) {
                                            hablar("Apagando Luz 2");
                                        } else {
                                            if (Alexa.equals("Alexa(" + A[j] + ",luz3);")) {
                                                hablar("Apagando Luz 3");
                                            }
                                        }

                                    }
                                }

                                //Bocina
                                for (int j = 0; j < A.length; j++) {
                                    if (Alexa.equals("Alexa(" + A[j] + ",bocina1);")) {
                                        hablar("Apagando Bocina 1");
                                    } else {
                                        if (Alexa.equals("Alexa(" + A[j] + ",bocina2);")) {
                                            hablar("Apagando Bocina 2");
                                        } else {
                                            if (Alexa.equals("Alexa(" + A[j] + ",bocina3);")) {
                                                hablar("Apagando Bocina 3");
                                            }
                                        }

                                    }
                                }
                                //Camara
                                for (int j = 0; j < A.length; j++) {
                                    if (Alexa.equals("Alexa(" + A[j] + ",camara1);")) {
                                        hablar("Apagando camara 1");
                                    } else {
                                        if (Alexa.equals("Alexa(" + A[j] + ",camara2);")) {
                                            hablar("Apagando camara 2");
                                        } else {
                                            if (Alexa.equals("Alexa(" + A[j] + ",camara3);")) {
                                                hablar("Apagando camara 3");
                                            }
                                        }

                                    }
                                }
                                //Aspersor
                                for (int j = 0; j < A.length; j++) {
                                    if (Alexa.equals("Alexa(" + A[j] + ",aspersor1);")) {
                                        hablar("Apagando aspersor 1");
                                    } else {
                                        if (Alexa.equals("Alexa(" + A[j] + ",aspersor2);")) {
                                            hablar("Apagando aspersor 2");
                                        } else {
                                            if (Alexa.equals("Alexa(" + A[j] + ",aspersor3);")) {
                                                hablar("Apagando aspersor 3");
                                            }
                                        }

                                    }
                                }
                                //Cerrar <----
                                //Puerta
                                for (int j = 0; j < C.length; j++) {
                                    if (Alexa.equals("Alexa(" + C[j] + ",puerta1);")) {
                                        hablar("Cerrando puerta");
                                    } else {
                                        if (Alexa.equals("Alexa(" + C[j] + ",puerta2);")) {
                                            hablar("Cerrando puerta");
                                        } else {
                                            if (Alexa.equals("Alexa(" + C[j] + ",puerta3);")) {
                                                hablar("Cerrando puerta");
                                            }
                                        }

                                    }
                                }
                                //Abrir
                                for (int j = 0; j < Ab.length; j++) {
                                    if (Alexa.equals("Alexa(" + Ab[j] + ",puerta1);")) {
                                        hablar("Abriendo puerta");
                                    } else {
                                        if (Alexa.equals("Alexa(" + Ab[j] + ",puerta2);")) {
                                            hablar("Abriendo puerta");
                                        } else {
                                            if (Alexa.equals("Alexa(" + Ab[j] + ",puerta3);")) {
                                                hablar("Abriendo puerta");
                                            }
                                        }
                                    }
                                }
                                //Lanzar 
                                //Juguete <----
                                for (int j = 0; j < L.length; j++) {
                                    if (Alexa.equals("Alexa(" + L[j] + ",juguete1);")) {
                                        hablar("Lanzando juguete");
                                    } else {
                                        if (Alexa.equals("Alexa(" + L[j] + ",juguete2);")) {
                                            hablar("Lanzando puerta");
                                        } else {
                                            if (Alexa.equals("Alexa(" + L[j] + ",juguete3);")) {
                                                hablar("Lanzando puerta");
                                            }
                                        }
                                    }
                                }
                                //Premio <----
                                  for (int j = 0; j < L.length; j++) {
                                    if (Alexa.equals("Alexa(" + L[j] + ",premio1);")) {
                                        hablar("Lanzando premio");
                                    } else {
                                        if (Alexa.equals("Alexa(" + L[j] + ",premio2);")) {
                                            hablar("Lanzando premio");
                                        } else {
                                            if (Alexa.equals("Alexa(" + L[j] + ",premio3);")) {
                                                hablar("Lanzando premio");
                                            }
                                        }
                                    }
                                }
                                return true;
                            }else {Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Falta paréntesis que cierra.",t,k3); return false;}
                        }
                    }
                }
            }else {Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Falta paréntesis que abre.",t,k3); return false;}
        }catch(NullPointerException e){
            return false;
        }
        Editor.escError(t[i-1].getLinea(), t[i-1].getCaracter(), "Parámetros incorrectos.",t,k3); 
        return false;
    }
//-----------------------------Fin de alexa----------------------------------------------------------

//-------------------------------IF--------------------------------------------
     public static boolean EsIF(Token[] t, int i){
        try{
            if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            if(t[i++].getTk().equals("(")){
                if(cond(t,i) > 0){
                    i = cond(t,i);
                    if(i == -1) return false;
                    while(t[i].getTipo().equals("Operador lógico")){
                        i = cond(t,i);
                    }
                    if(t[i].equals("null")) i++;
                    System.out.println(t[i]);
                    if(t[i].getTk().equals(")")){
                        return true;
                    }
                }else return false;
            }
        }catch(NullPointerException e){
            return false;
        }
        return false;
    }   
    
 //-----------------------------FIN IF-----------------------------------------

    public static int cond(Token[] t, int i){
        if(expE(t,i) >= 0){
            System.out.println("1. "+t[i]);
            i = expE(t,i);
            if(i < 0) return -1;
            System.out.println("2. "+t[i]);
            if(t[i].getTk().equals("==") || t[i].getTk().equals("<") || t[i].getTk().equals(">") || t[i].getTk().equals("<=") || t[i].getTk().equals(">=") || t[i].getTk().equals("!=")){
                System.out.println("3. "+t[i]);
                i++;
                if(t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
                System.out.println("4. "+t[i]);
                if(expE(t,i) >= 0){
                    i = expE(t,i);
                    if(i < 0) return -1;
                }
                System.out.println("5. "+t[i]);
            }
        }else return -1;
        return i;
    }

//-------------------------------FOR--------------------------------------------
    public static boolean EsFor(Token[] t, int i) {
        try {
            if (t[i].getTipo().equals("Error") && t[i].getTk().equals("")) i++;
            if (t[i++].getTk().equals("(")) {
                if(t[i].getTk().equals("int")) {
                    i++;
                    if(t[i].getTk().equals(":")) i++;
                    else return false;
                }
                if (t[i++].getTipo().equals("Identificador")) {
                    if (t[i++].getTk().equals("=")) {
                        if (t[i++].getTipo().equals("Entero")) {
                            if (t[i++].getTk().equals(";")) {
                                if(t[i++].getTipo().equals("Identificador")){
                                    if(t[i].getTipo().equals("Operador relacional") || t[i].getTk().equals("<") || t[i].getTk().equals(">")){
                                        i++;
                                        if(t[i++].getTipo().equals("Entero")){
                                            if(t[i++].getTk().equals(";")){
                                                if(t[i++].getTipo().equals("Identificador")){
                                                    if(t[i++].getTk().equals("++")){
                                                        if(t[i++].getTk().equals(")")){
                                                            if(t[i].getTk().equals("{")){
                                                                return true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    //-----------------------------FIN FOR-----------------------------------------



}
