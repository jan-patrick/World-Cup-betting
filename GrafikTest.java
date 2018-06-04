/**
 * Beschreiben Sie hier die Klasse test.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
import java.awt.*;      // Abstract Windowing Toolkit
import javax.swing.*;   // Swing Komponenten fuer Oberflaeche
import java.awt.event.*;// Ereignisse wie Klick auf Button

public class GrafikTest extends JPanel {       // ein JPanel als Traeger der Komponenten
  JLabel literLabel = new JLabel("Liter");
  JLabel kmLabel = new JLabel("Kilometer");
  JLabel verbrauchLabel = new JLabel("Verbrauch:");
  JTextField literEingabe = new JTextField(10);
  JTextField kmEingabe = new JTextField(10);
  JButton berechneButton = new JButton("berechne");
  
  public static void main(String[] args){           // Startfunktion 
    GrafikTest derGrafikTest = new GrafikTest(); // die Oberflaeche erzeugen
    JFrame derRahmen = new JFrame("Grafik Test"); // einen Fensterrahmen erzeugen
    derRahmen.getContentPane().add(derGrafikTest); // das Panel in den Rahmen einbauen
    derRahmen.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // Programmende wenn Rahmen geschlossen 
    derRahmen.setSize(400,400);                     // Groesse des JFrame setzen
    derRahmen.setVisible(true);                     // das Fenster soll sichtbar sein
    derRahmen.setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png"));
  } 
  public GrafikTest(){         // Konstruktor der Oberflaeche
    setBackground(Color.yellow);// der Hintergrund ist gelb
    setLayout(null);            // selber machen
    int durchschuss=5,hoehe=30,zeile1=20,spalte1=20,spalte2=90,spalte3=200; // Layoutpositionen
    literLabel.setBounds(spalte1,zeile1,spalte2-spalte1-2,hoehe-durchschuss);
    kmLabel.setBounds(spalte1,zeile1+hoehe,spalte2-spalte1-2,hoehe-durchschuss);
    verbrauchLabel.setBounds(spalte1,zeile1+2*hoehe,250,hoehe-durchschuss);
    literEingabe.setBounds(spalte2,zeile1,spalte3-spalte2-2,hoehe-durchschuss);
    kmEingabe.setBounds(spalte2,zeile1+hoehe,spalte3-spalte2-2,hoehe-durchschuss);
    berechneButton.setBounds(spalte3,zeile1+hoehe,100,hoehe-durchschuss);
    add(literLabel);
    add(kmLabel);
    add(verbrauchLabel);
    add(literEingabe);
    add(kmEingabe);
    add(berechneButton);
    berechneButton.addMouseListener(new java.awt.event.MouseAdapter() { // bei Mausklick ButtonClick
      public void mousePressed(MouseEvent e) {
        berechneButtonClick();      // bei Click diese Methode aufrufen
      }
    });
  }
  void berechneButtonClick(){
    double l,km,v;
    l = Double.parseDouble(literEingabe.getText());
    km= Double.parseDouble(kmEingabe.getText());
    v=l/km*100;
    //verbrauchLabel.setText("Verbrauch: "+v+" l/100km");
    verbrauchLabel.setText(String.format("Verbrauch: %5.2f l/100km",v));
  }
}