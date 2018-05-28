import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Nationen.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Nationen
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private ArrayList<Land> laender;

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
    
    public String getLandDetails(int index)
    {
        Land land = laender.get(index);
        return land.getDetails();
    }
}
