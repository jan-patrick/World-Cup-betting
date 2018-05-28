
/**
 * Beschreiben Sie hier die Klasse Land.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Land
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private String name;
    private int tore;
    private int punkte;

    /**
     * Konstruktor für Objekte der Klasse Land
     */
    public Land(String name, int tore, int punkte)
    {
        // Instanzvariable initialisieren
        setDetails(name, tore, punkte);
    }
    
    private void setDetails(String name, int tore, int punkte)
    {
        this.name = name;
        this.tore = tore;
        this.punkte = punkte;
    }

    /**
     * Return details
     * @return The track's details.
     */
    public String getDetails()
    {
        return name + ": " + tore + " Tore,  " + punkte + " Punkte";
    }
}