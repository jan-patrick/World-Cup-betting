import java.util.ArrayList;
import java.util.List;

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
public class Nationen
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    public List<Land> laender;
 
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
    
    public int getLaenderSize()
    {
        return laender.size();
    }
    
    public List<Land> getLaender() {
        return laender;
    }
    
    @XmlElement(name = "laender")
    public void setProducts(List<Land> laender) {

        this.laender = laender;

    }
    
    public String getLandDetails(int index)
    {
        Land land = laender.get(index);
        return land.getDetails();
    }
    
    public void add(Land land) {
        this.laender.add(land);
    }
}
