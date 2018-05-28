import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Paarungen.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Paarungen
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private ArrayList<Spiel> spiele;

    /**
     * Konstruktor für Objekte der Klasse Paarungen
     */
    public Paarungen()
    {
        // Instanzvariable initialisieren
        spiele = new ArrayList<>();
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public void addSpiel(int tora, int torb, String landa, String landb, String beschreibung)
    {
        spiele.add(new Spiel(tora, torb, landa, landb, beschreibung));
    }
    
    public int getSpieleSize()
    {
        return spiele.size();
    }
    
    public String getSpielDetails(int index)
    {
        Spiel spiel = spiele.get(index);
        return spiel.getDetails();
    }
}
