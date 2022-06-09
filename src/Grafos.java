/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eduardo
 */
public class Grafos {
    public static boolean esIde(String cad){
        if(cad.length()==0) return false;
        int edo=0,ent,c;
                      //  letra,dig,_,otro
        int M[][]={ {1,-1,1,-1},{1,1,1,-1}};
        for (int i = 0; i < cad.length(); i++) {
            c=cad.charAt(i);
            if((c>=65 && c<=90)||(c>=97 && c<=122)){
                ent=0;
            }else  if(c>=48 && c<=57){
                ent=1;
            }else  if(c=='_'){
                ent=2;
            }else {
                ent=3;
            }
            edo=M[edo][ent];  
            if(edo==-1) {
                return false;
            }
        }//for  
        return true;
    }//esIde
    
    public static boolean esInt(String cad){      
        if(cad.length()==0) return false;
        int edo = 0, ent, c;
        
        int M[][]={{1,1,1,-1},{1,-1,-1,-1}};
        for(int i=0; i<cad.length(); i++){
           c=cad.charAt(i);
           if(c>=48 && c<=57) ent=0;
           else if(c=='+') {
               if(cad.length()==1) return false; 
               ent=1; 
           }
           else if(c=='-') {
               if(cad.length()==1) return false;
               ent=2;
           } 
           else ent=3;
           edo=M[edo][ent]; 
           if(edo==-1) return false;
       }//for
       return true;
    //int
    }
    
    public static boolean esDecimal(String cad){
        if(cad.length()==0) return false;
        if(cad.charAt(cad.length()-1)=='.') return false;
        int edo = 0, ent, c;
                  //+ - 1  .  *
        int M[][]={{1,1,2,-1,-1},
                   {-1,-1,2,-1,-1},
                   {-1,-1,2,3,-1},
                   {-1,-1,3,-1,-1}};
        for(int i=0; i<cad.length();i++){
           c=cad.charAt(i);
           if(c == '+') ent = 0;
           else if(c == '-') ent = 1;
           else if(c>=48 && c<=57) ent=2;
           else if(c == '.') ent = 3; 
           else ent=4;
           edo=M[edo][ent]; 
           if(edo==-1) return false;
       }//for
       return true;
    }

    public static boolean esCom(String cad){
        if(cad.length()==0) return false;
        if(cad.matches("#\\w*#")) return true;
        return false;
    }//esIde

}
