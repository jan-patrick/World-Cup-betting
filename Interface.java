import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * Beschreiben Sie hier die Klasse Interface.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Interface
{
    /**
     * Constructor for objects of class Interface
     */
    public Interface()
    {
        // nichts
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public boolean bestaetigen(String kopfzeile, String nachricht)
    {
        int eingabe = JOptionPane.showConfirmDialog(null,
                nachricht,
                kopfzeile,
                JOptionPane.YES_NO_CANCEL_OPTION);
        if(eingabe == 0){
            return true;
        }
        else return false;
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void nachricht(String kopfzeile, String nachricht)
    {
        JOptionPane.showMessageDialog(null,
            nachricht,
            kopfzeile,                        
            JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String[] eingabeAufforderungSpielergebnis()
    {
        JTextField mannschaft1 = new JTextField();
        JTextField tore1 = new JTextField();
        JTextField mannschaft2 = new JTextField();
        JTextField tore2 = new JTextField();
        Object[] message = {"Mannschaft", mannschaft1, 
                "Tore", tore1, "Mannschaft", mannschaft2, 
                "Tore", tore2};

        JOptionPane pane = new JOptionPane( message, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, "Spielergebnis").setVisible(true);

        String[] daten = {mannschaft1.getText(), tore1.getText(), mannschaft2.getText(), tore2.getText()};
        return daten;
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void createSpielplan(String daten)
    {
        String [] teile = daten.split("!");
   
        JPanel panel = new JPanel( new GridLayout(1, teile.length) );
        for (int i = 0; i < teile.length; i++) {
            panel.add( new JLabel("<html>" + teile[i] + "</html>") );
        }     

        JOptionPane pane = new JOptionPane( panel);
        pane.createDialog(null, "Spielergebnisse").setVisible(true);
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String[] eingabeAufforderungNeuesLand()
    {
        JTextField gruppe = new JTextField();
        JTextField name = new JTextField();

        Object[] message = {"Wenn sie ein neues Land zu einer Gruppe hinzu fügen werden alle Daten zurückgesetzt.", " ",
            "Gruppe des Landes", gruppe, 
                "Name des Landes", name};

        JOptionPane pane = new JOptionPane( message, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, "Neues Land").setVisible(true);

        String[] daten = {gruppe.getText(), name.getText()};
        return daten;
    }
}

