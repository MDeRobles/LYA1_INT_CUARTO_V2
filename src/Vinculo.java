/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 *
 *
 */
public class Vinculo extends JLabel implements MouseListener{
    private String tipo;
    private int i;
    private Token[] t;

    public Vinculo(int x, int y, String tipo, Token[] t, int i){        
        this.setText("Sugerencia");
        this.setSize(100, 30);
        this.setForeground(Color.blue);
        this.setFont(new Font("tahoma",Font.PLAIN,18));
        this.setVisible(true);
        this.setLocation(x, y); 
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.tipo = tipo;
        this.i = i;
        this.t = t;

        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle r;            
        r = g.getClipBounds();            
        g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent(), getFontMetrics(getFont())
                .stringWidth(getText()), r.height - getFontMetrics(getFont()).getDescent());        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Sugerencia sug = new Sugerencia();
        sug.setLocationRelativeTo(null);
        sug.setVisible(true);

        sug.txtPDesc.setText("");
        sug.txtPGram.setText("");
        sug.txtPProd.setText("");

        ImageIcon imagen = new ImageIcon(getClass().getResource("IMG/alexa.jpg"));
        Icon img=new ImageIcon(imagen.getImage().getScaledInstance(400, 50, Image.SCALE_SMOOTH));
        
        if(tipo.equals("Identificador")){
            sug.txtPDesc.setText("La sintaxis propia de un identificador es\nempezando siempre por una letra, seguida\no por otra letra o un d??gito o un gui??n\nbajo cero o m??s veces.\n\nEjemplos:\nx\ny1\nID_5");
            sug.txtPGram.setText("S ::= <letra> A\nA ::= <letra> A | _A | <dig> A | ??");
            Gram2.ide(t, i);
        }else if(tipo.equals("Alexa")){
            sug.txtPDesc.setText("La sint??xis de \"Alexa\" debe empezar por\nla palabra \"Alexa\", seguida por un\npar??ntesis que abre, una pal??bra reservada\nespec??fica de la funci??n \"Alexa\", una coma,\nun identificador previamente declarado y con\nun valor compatible con la funci??n de la \npalabra reservada, continuando por un \npar??ntesis que cierra y por ??ltimo, un punto\ny coma.\n\nEjemplos:\nAlexa ( encender, luz1 );\nAlexa ( lanzar, pelota3 );\nAlexa ( abrir, puerta2 );");
            JLabel aut = new JLabel("");
            aut.setIcon(img);
            aut.setLocation(10, 10);
            aut.setSize(sug.txtPGram.getWidth(), 150);
            sug.txtPGram.add(aut);
            Gram2.alexa(t, i);
        }else if(tipo.equals("Declaraci??n")){
            sug.txtPDesc.setText("Una correcta declaraci??n debe empezar por\ndescribir el tipo con su respectiva\npalabra reservada (\"int\", \"float\", \"boolean\",\netc), seguida por dos puntos (\":\") y el\nidentificador, y en el caso de que se\ndesee asignarle el valor al mismo tiempo,\ndebe continuar por el signo igual (\"=\") y\ndespu??s el valor compatible al tipo de la\ndeclaraci??n, terminando la expresi??n \ncon un punto y coma.\n\nEjemplos:\nint: x = 10;\nfloat: y;\nboolean: val = true;\nluz: foco1;");
            sug.txtPGram.setText("S ::= <tipo> : A\nA ::= <id> B\nB ::= , A | = <val> C | ;\nC ::= ; | , A");
            Gram2.decla(t, i);
        }else if(tipo.equals("Asignaci??n")){
            sug.txtPDesc.setText("Para asignarle un valor a un identificador\nanteriormente declarado, es necesario\nespecificar dicho identificador, siguiendo\npor dos puntos (\":\") y la expresi??n o el\nvalor directo que se le desea asignar.\n\nEjemplos:\nentero: 10;\ndec: 5.8;\nid: true;\nx: x + 15;");
            sug.txtPGram.setText("S ::= <id> : E\nE ::= T + E | T - E | T\nT ::= F * T | F / T | F\nF ::= <val> | (E)");
            sug.txtPProd.setText("S ::= " + t[i].getTk() + " : E\n");
            Gram2.expE(t, i+2);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}    

}
