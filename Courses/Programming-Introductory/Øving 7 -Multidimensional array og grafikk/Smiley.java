import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.applet.Applet;


class Vindu extends JFrame {
  public Vindu(String tittel){
    setTitle(tittel);
    setSize(430,450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Tegning tegningen = new Tegning();
    add(tegningen);
  }
}

class Tegning extends JPanel{
  public void paintComponent(Graphics tegneflate){
    super.paintComponent(tegneflate);
    setBackground(Color.BLUE);

    /* Hode */
    tegneflate.setColor(Color.BLACK);
    tegneflate.drawOval(14,14,401,401); // hode
    tegneflate.setColor(Color.YELLOW);
    tegneflate.fillOval(15,15,400,400); // hode
    /* Øyer */
    tegneflate.setColor(Color.BLACK);
    tegneflate.fillOval(120,150,40,40); //venstre øye
    tegneflate.fillOval(280,150,40,40); // høyre øye
    /* Munn */
    tegneflate.fillArc(120,190,200,150,180,180); // munn
    /* Tenner */
    tegneflate.setColor(Color.WHITE);
    tegneflate.fillRect(160, 265, 50, 50); //tann
    tegneflate.fillRect(220, 265, 50, 50); //tann
    /* Briller */
    tegneflate.setColor(Color.BLACK);
    tegneflate.fillRect(70, 120, 10, 120);
    tegneflate.fillRect(190, 120, 10, 120);
    tegneflate.fillRect(230, 120, 10, 120);
    tegneflate.fillRect(350, 120, 10, 120);
    tegneflate.fillRect(230, 120, 120, 10);
    tegneflate.fillRect(80, 120, 120, 10);
    tegneflate.fillRect(230, 230, 120, 10);
    tegneflate.fillRect(80, 230, 120, 10);
    tegneflate.fillRect(15, 180, 60, 10);
    tegneflate.fillRect(190, 160, 40, 10);
    tegneflate.fillRect(355, 180, 60, 10);
    
  }
}


public class Smiley extends Applet {
  public static void main(String[]args){
    Vindu etVindu = new Vindu("Smiley");
    etVindu.setVisible(true);
  }
}
