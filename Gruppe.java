import java.util.HashMap;
import java.math.*;

/**
 * Beschreiben Sie hier die Klasse Gruppe.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Gruppe
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private HashMap<String, Land> laender;
    private String gruppenName;
    private int gruppenGroesse;
    private Daten daten;
 
    /**
     * Konstruktor der Klasse Gruppe
     */
    public Gruppe(String gruppenName)
    {
        laender = new HashMap<>();
        daten = new Daten();
        this.gruppenName = gruppenName;
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
    public int getLaenderGroesse()
    {
        return laender.size();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public int getGruppenGroesse()
    {
        return gruppenGroesse;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void ladeLand(String name)
    {
        String[] datenteile = getDatenTeile("Laender", name);

        String nameLand = datenteile[0];
        int tore = Integer.valueOf(datenteile[1]);
        int punkte = Integer.valueOf(datenteile[2]);

        laender.put(nameLand, new Land(nameLand, tore, punkte));
        gruppenGroesse += 1;
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
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String[] getUpdatedInfoLand(String name, int tore, int punkte)
    {
        Land land = laender.get(name);
        return land.getUpdatedInfo(tore, punkte);
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String[] getLaender()
    {
        StringBuffer aktuelleDaten = new StringBuffer();
        for (String key : laender.keySet()) {
            if (aktuelleDaten.length() != 0) {
                aktuelleDaten.append("/");
            }
            aktuelleDaten.append(key);
        }
        String sDaten = aktuelleDaten.toString();
        String[] datenteile = sDaten.split("/");
        return datenteile;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private String[] getDatenTeile(String ordner, String datei)
    {
        String aktuelleDaten = "";
        try{
            aktuelleDaten = daten.ladeDatei(ordner, datei);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String[] teile = aktuelleDaten.split("/");
        return teile;
    }
}
