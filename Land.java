/**
 * Die Klasse Land beinhaltet alle Informationen, die ein Land identifizieren und zugehörig sind.
 * Objekte der Klasse Land werden in der HashMap Laender in der Klasse Gruppe gespeichert.
 * Der Name des Landes wird neben dessen Tore und Punkte gespeichert.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Land
{
    private String name;
    private int tore;
    private int punkte;   

    /**
     * Wird ein Objekt der Klasse Land erzeugt, muss der Name, Tore und Punkte übergeben werden.
     * Wichtig ist, dass auch die Tore und Punkte übergeben werden können, um bei Neustart oder
     * Zurücksetzen des Programms direkt aus den gespeicherten oder neuen Daten der Klasse Daten
     * alle Länder mit aktuellen Werten erzeugt bzw wieder eingeleden werden können, auch wenn 
     * diese dann beispielsweise schon Tore und Punkte besitzen.
     * 
     * @param name, tore, punkte
     */
    public Land(String name, int tore, int punkte)
    {
        setDetails(name, tore, punkte);
    }
    
    /**
     * Setzt die Werte der Klasse den übergebenen Werten der Methode gleich. 
     * 
     * @param name, tore, punkte
     */
    private void setDetails(String name, int tore, int punkte)
    {
        this.name = name;
        this.tore = tore;
        this.punkte = punkte;
    }

    /**
     * Gibt alle Werte (Name, Tore, Punkte) dieser Klasse als String Array zurück.
     * 
     * @return aktuelledaten
     */
    public String[] getDetails()
    {
        String[] aktuelledaten = {name, String.valueOf(tore), String.valueOf(punkte)};
        return aktuelledaten;
    }
    
    /**
     * Gibt den Namen des Landes zurück.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Aktualisiert erst die Tore und Punkte und gibt anschließend alle Werte (Name, Tore, 
     * Punkte)als Array zurück.
     * 
     * @param tore, punkte
     * @return aktuelledaten
     */
    public String[] getUpdatedInfo (int tore, int punkte)
    {
        this.tore += tore;
        this.punkte += punkte;
        String[] aktuelledaten = getDetails();
        return aktuelledaten;
    }
}