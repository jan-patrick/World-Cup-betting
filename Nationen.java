import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Nationen.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Nationen
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private ArrayList<Land> laender;

    /**
     * Konstruktor für Objekte der Klasse Nationen
     */
    public Nationen()
    {
        // Instanzvariable initialisieren
        laender = new ArrayList<>();
    }
    
    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public void addLand(String name, int tore, int punkte)
    {
        laender.add(new Land(name, tore, punkte));
    }
}
