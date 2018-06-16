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
        setDetails(name, tore, punkte);
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void setDetails(String name, int tore, int punkte)
    {
        this.name = name;
        this.tore = tore;
        this.punkte = punkte;
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String getDetails()
    {
        return name + ": " + tore + " Tore,  " + punkte + " Punkte";
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String getName() {
        return name;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public int getTore() {
        return tore;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public int getPunkte() {
        return punkte;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String toString() {
         return name + "/" + String.valueOf(tore) + "/" + String.valueOf(punkte);
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String getUpdatedLandInfo(int tore, int punkte)
    {
        this.tore = tore;
        this.punkte = punkte;
        return toString();
    }
}