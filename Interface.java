import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * Die Klasse Interface erstellt ein User Interface, welches über die Hauptklasse Main aufgerufen wird.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.06.26
 */
public class Interface
{
    /**
     * Konstruktor für Objekte der Klasse Interface.
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
     * Öffnet ein Fenster, welches eine Nachricht und Textzeile oben lins enthält und die Option die Knöpfe "OK" und "Abbrechen" zu wählen.
     * Bei Auswahl von OK durch den Benutzer wird true zurückgeliefert und bei Abbrechen false.
     * Diese Methode ist ein Bestätigungsfenster durch das meist nochmal überprüft wird, ob eine Methode wirklick ausgeführt werden soll.
     * 
     * @param head Text der oben im Fenster steht
     * @param nachricht Nachricht des Programms
     * 
     * @return true wenn "OK" geklickt wurde, false wenn das Fenster geschlossen oder "Abbrechen" geklickt wurde.
     */
    public boolean bestaetigen(String head, String nachricht)
    {
        int eingabe = JOptionPane.showConfirmDialog(null,
                nachricht,
                head,
                JOptionPane.YES_NO_OPTION);
        if(eingabe == 0) return true;
        else return false;
    }

    /**
     * Öffnet ein Fenster, dass eine Nachricht anzeigt. 
     * Dieses Fenster kann mit OK oder dem [X] geschlossen werden.
     * Meist wird dem User über diese Methode eine Information angezeigt.
     * 
     * @param head Text der oben im Fenster steht
     * @param nachricht Nachricht des Programms bzw Information
     */
    public void nachricht(String head, String nachricht)
    {
        JOptionPane.showMessageDialog(null,
            nachricht,
            head,                        
            JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Öffnet ein Fenster, in das ein Spielergebnis eingetragen werden kann. 
     * Dies wird über vier Fleder möglich - je zwei für die Länder und zwei für deren Tore.
     * Bei Auswahl von OK durch den Benutzer wird ein Array aller Felder zurückgeliefert und bei Abbrechen null.
     * Eingegebene Werte werden überprüft (negativ, Leerzeichen, andere Zeichen),
     * sodass nur bei korrekt ausgefüllen Feldern auch wirklich ein Array zurückgeliefert wird.
     * 
     * @return daten Spieldaten, ansonsten null
     */
    public String[] eingabeAufforderungSpielergebnis()
    {
        JTextField land1 = new JTextField();
        JTextField tore1 = new JTextField();
        JTextField land2 = new JTextField();
        JTextField tore2 = new JTextField();
        Object[] message = {"Mannschaft", land1, 
                "Tore", tore1, "Mannschaft", land2, 
                "Tore", tore2};
        JOptionPane pane = new JOptionPane( message, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, "Spielergebnis eintragen").setVisible(true);
        if(pane.getValue()!= null){
            int value = ((Integer)pane.getValue()).intValue();
            if(value == JOptionPane.CANCEL_OPTION)
            {
                return null;
            }
            if(!land1.getText().replaceAll("\\W","").isEmpty() && !tore1.getText().replaceAll("\\W","").isEmpty() &&
               !land2.getText().replaceAll("\\W","").isEmpty() && !tore2.getText().replaceAll("\\W","").isEmpty())
            {
                String[] daten = {land1.getText().replaceAll("\\W",""), tore1.getText().replaceAll("\\W",""),
                                  land2.getText().replaceAll("\\W",""), tore2.getText().replaceAll("\\W","")};
                if(Integer.valueOf(tore1.getText()) >= 0 && Integer.valueOf(tore2.getText()) >= 0)
                {
                    return daten;
                }           
                else 
                {
                    nachricht("Fehler", "Sie können nur positive Werte eintragen");  
                    return eingabeAufforderungSpielergebnis();
                }
            }
            else 
            {
                nachricht("Fehler", "Sie müssen alle Felder ausfüllen!");  
                return eingabeAufforderungSpielergebnis();
            }
        }
        else return null;
    }

    /**
     * Öffnet ein Fenster und zeigt den Übersichtsplan an.
     * Die einzelnen Spiele werden ausgegeben, zusammen mit dem Ergebnis
     * 
     * @param turniername Name des Turniers
     * @param daten Daten aller Spielergebnisse
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
     * Öffnet ein Fenster, in das der Name eines neuen Landes und dessen Gruppe eingegeben werden kann. 
     * 
     * @return daten Landname, ansonsten null
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
     * Öffnet ein Fenster mit einer Nachricht und vier Feldern zur Eingabe von möglichen vier Ländern.
     * Mindestens zwei Länder müssen eingegeben werden.
     * 
     * @return daten neue Gruppe, ansonsten null
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
     * Öffnet ein Fenster mit einer Nachricht und einem Feld zur Eingabe entweder möglicher Länder oder Gruppen je nach verwendung.
     * Deshalb müssen die Nachricht, Kopfzeile und Beschreibung auch übergeben werden um die Variabilität zu gewährleisten.
     * 
     * @param head Text der oben im Fenster steht
     * @param nachricht Nachricht des Programms bzw Information
     * @param textfeld
     * 
     * @return daten wenn alle Bedingungen erfüllt sind, ansonsten null
     */
    public String eingabeAufforderungEinFeld(String head, String nachricht, String textfeld)
    {
        JTextField gruppe = new JTextField();
        JTextField name = new JTextField();
        Object[] message = {nachricht, " ", textfeld, name};
        JOptionPane pane = new JOptionPane( message, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, head).setVisible(true);
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
                return eingabeAufforderungEinFeld(head, nachricht, textfeld);
            }
        }
        else return null;
    }
}