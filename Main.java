
/**
 * Beschreiben Sie hier die Klasse Main.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Main
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Nationen teilnehmerlaender;

    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main()
    {
        // Instanzvariable initialisieren
        teilnehmerlaender = new Nationen();
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public void addLand(String name, int tore, int punkte)
    {
        teilnehmerlaender.addLand(name, tore, punkte);
    }
}
