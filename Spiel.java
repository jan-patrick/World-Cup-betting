/**
 * Beschreiben Sie hier die Klasse Spiel.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Spiel
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int tora;
    private int torb;
    private String landa;
    private String landb;
    private String beschreibung;

    /**
     * Konstruktor für Objekte der Klasse Spiel
     */
    public Spiel(int tora, int torb, String landa, String landb, String beschreibung)
    {
        // Instanzvariable initialisieren
        setDetails(tora, torb, landa, landb, beschreibung);
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void setDetails(int tora, int torb, String landa, String landb, String beschreibung)
    {
        this.tora = tora;
        this.torb = torb;
        this.landa = landa;
        this.landb = landb;
        this.beschreibung = beschreibung;
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String getDetails()
    {
        return landa + " - " + tora + " : " + torb + " - " + landb;
    }
}
