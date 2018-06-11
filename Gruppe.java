import java.util.HashMap;

/**
 * Beschreiben Sie hier die Klasse Nationen.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Gruppe
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private HashMap<String, Land> laender;
    private int gruppenGroesse;
    private Daten daten;
 
    /**
     * Konstruktor für Objekte der Klasse Nationen
     */
    public Gruppe(String name)
    {
        // Instanzvariable initialisieren
        laender = new HashMap<>();
    }
           
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void addLand(String name, int tore, int punkte)
    {
        Land land = new Land(name, 0, 0);
        laender.put(name, land);
        gruppenGroesse += 1;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public int getLaenderSize()
    {
        return laender.size();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public boolean existiertLand(String name)
    {
        if(laender.containsKey(name)){
            return true;
        }
        else{return false;}
    }
       
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String getLandDetails(int index)
    {
        Land land = laender.get(index);
        return land.getDetails();
    }
    
    public String getUpdatedInfoLand(String name, int tore, int punkte)
    {
        Land land = laender.get(name);
        return land.getUpdatedLandInfo(tore, punkte);
    }
}
