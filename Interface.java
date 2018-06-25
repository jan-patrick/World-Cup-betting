import java.util.ArrayList;
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
        
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String eingabeAufforderungTurniername(String bisherigerTurniername)
    {
        JTextField turniername = new JTextField();
        Object[] message = {"neuer Turniername (bestehend aus Buchstaben und Zahlen)",
                            turniername, 
                            "Ansonsten heißt das Turnier weiterhin:", 
                            bisherigerTurniername,};
        JOptionPane pane = new JOptionPane( message, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, "Turniername ändern").setVisible(true);
        String daten = turniername.getText();
        return daten;
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
                JOptionPane.YES_NO_OPTION);
        if(eingabe == 0)
        {
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
        String[] daten = {mannschaft1.getText().replaceAll("\\W",""), tore1.getText().replaceAll("\\W",""),
                          mannschaft2.getText().replaceAll("\\W",""), tore2.getText().replaceAll("\\W","")};
        return daten;
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void createSpielplan(String turniername, String daten)
    { 
        String [] teile = daten.split(",");     
        JPanel panel = new JPanel( new GridLayout(1, teile.length) );
        for (int i = 0; i < teile.length; i++)
        {
            panel.add( new JLabel("<html><div style='text-align: center;'>" + teile[i] + "</div></html>") );
        }     
        JOptionPane pane = new JOptionPane( panel);
        pane.createDialog(null, "Spielergebnisse " + turniername).setVisible(true);
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
        Object[] message = {"Bei Hinzufügen eines neuen Landes werden alle bisher eingespeicherten Spieldaten resetted.",
                            " ", "Gruppe des neuen Landes", gruppe, "Name des Landes", name};
        JOptionPane pane = new JOptionPane( message, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, "Neues Land").setVisible(true);
        if(pane.getValue()!= null)
        {
            int value = ((Integer)pane.getValue()).intValue();
            if(value == JOptionPane.CANCEL_OPTION){return null;}
            if(!gruppe.getText().isEmpty() && !name.getText().isEmpty())
            {
                String[] daten = {gruppe.getText(), name.getText()};
                return daten;
            }
            else
            {
                nachricht("Eingabefehler", "Sie müssen alle Felder ausfüllen!");  
                return eingabeAufforderungNeuesLand();
            }
        }
        else return null;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String[] eingabeAufforderungNeueGruppe()
    {
        JTextField gruppe = new JTextField();
        JTextField name1 = new JTextField();
        JTextField name2 = new JTextField();
        JTextField name3 = new JTextField();
        JTextField name4 = new JTextField();

        Object[] message = {"Fügen sie bitte mindestens zwei Länder hinzu.", " ", 
                "Namen der Länder", name1, name2, name3, name4};

        JOptionPane pane = new JOptionPane( message, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, "Neues Land").setVisible(true);

        if(pane.getValue()!= null){
            int value = ((Integer)pane.getValue()).intValue();
            String[] datenAlt = {name1.getText(), name2.getText(), name3.getText(), name4.getText()};
            ArrayList teile = new ArrayList<String>();
            for (int i = 0; i < datenAlt.length; i++) {
                if(datenAlt[i].isEmpty() == false){ teile.add(datenAlt[i]);}
            }
            String[] daten = new String[teile.size()];
            teile.toArray( daten );

            if( daten.length >= 2) {return daten;}
            if(value == JOptionPane.CANCEL_OPTION){return null;}
            else {nachricht("Eingabefehler", "Sie müssen mindestens 2 Länder eingeben!");  return eingabeAufforderungNeueGruppe();}
        }
        else return null;
    }   
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String eingabeAufforderungEinFeld(String kopfzeile, String nachricht, String textfeld)
    {
        JTextField gruppe = new JTextField();
        JTextField name = new JTextField();
        Object[] message = {nachricht, " ", textfeld, name};
        JOptionPane pane = new JOptionPane( message, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, kopfzeile).setVisible(true);
        if(pane.getValue()!= null)
        {
            int value = ((Integer)pane.getValue()).intValue();
            String daten = name.getText();
            if(!daten.isEmpty())
            {
                return daten;
            }
            if(value == JOptionPane.CANCEL_OPTION)
            {
                return null;
            }
            else 
            {
                nachricht("Eingabefehler", "Sie müssen alle Felder ausfüllen!"); 
                return eingabeAufforderungEinFeld(kopfzeile, nachricht, textfeld);
            }
        }
        else return null;
    } 
}

