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
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public void addLand(String name, int tore, int punkte)
    {
        Land land = new Land(name, 0, 0);
        laender.put(name, land);
    }
    
    public int getLaenderSize()
    {
        return laender.size();
    }
        
    public String getLandDetails(int index)
    {
        Land land = laender.get(index);
        return land.getDetails();
    }
}
