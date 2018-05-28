import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Gruppen.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Gruppen
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private ArrayList<Land> gruppe;
    
    /**
     * Konstruktor für Objekte der Klasse Gruppen
     */
    public Gruppen()
    {
        // Instanzvariable initialisieren
        gruppe = new ArrayList<>();
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public void addMitglied(Land name)
    {
        gruppe.add(name);
    }
}
