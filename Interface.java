import javax.swing.JOptionPane;

/**
 * Beschreiben Sie hier die Klasse test.
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
    public String bestaetigen(String kopfzeile, String nachricht)
    {
        int eingabe = JOptionPane.showConfirmDialog(null,
                nachricht,
                kopfzeile,
                JOptionPane.YES_NO_CANCEL_OPTION);
        if(eingabe == 0){
            return "Ja";
        }
        if(eingabe == 1){
            return "Nein";
        }
        else return "Abbrechen";
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
    public String eingabeAufforderung(String kopfzeile, String nachricht)
    {
        String eingabe = JOptionPane.showInputDialog(null,"Geben Sie Ihren Namen ein",
                "Deine Eingabeaufforderung",
                JOptionPane.PLAIN_MESSAGE);
        return eingabe;
    }
}

