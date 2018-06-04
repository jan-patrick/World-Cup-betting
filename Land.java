/**
 * Beschreiben Sie hier die Klasse Land.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Land
{
    // Instanzvariablen   
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
     * @return The details.
     */
    public String getDetails()
    {
        return name + ": " + tore + " Tore,  " + punkte + " Punkte";
    }
    
    public String getName() {
        return name;
    }
    
    public int getTore() {
        return tore;
    }
    
    public int getPunkte() {
        return punkte;
    }
}