import java.util.HashMap;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Beschreiben Sie hier die Klasse Nationen.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
@XmlRootElement(name = "nationen")
@XmlAccessorType(XmlAccessType.FIELD)
public class Gruppe
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private HashMap<String, Land> laender;
 
    /**
     * Konstruktor für Objekte der Klasse Nationen
     */
    public Gruppe()
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
    public String getLandDetails(int index)
    {
        Land land = laender.get(index);
        return land.getDetails();
    }
}
