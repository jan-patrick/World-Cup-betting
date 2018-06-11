import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Beschreiben Sie hier die Klasse Main.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Main
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private HashMap<String, Gruppe> gruppen;
    private Paarungen begegnungen;
    private Daten daten;

    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main()
    {
        // Instanzvariable initialisieren
        gruppen = new HashMap<>();
        begegnungen = new Paarungen();
        daten = new Daten();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void ladeGruppenTestData()
    {
        gruppen.put("A", new Gruppe("A"));
        gruppen.put("B", new Gruppe("B"));
        gruppen.put("C", new Gruppe("C"));
        gruppen.put("D", new Gruppe("D"));
        gruppen.put("E", new Gruppe("E"));
        gruppen.put("F", new Gruppe("F"));
        gruppen.put("G", new Gruppe("G"));
        gruppen.put("H", new Gruppe("H"));
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */  
    public void addSpiel(int tora, int torb, String landa, String landb, String beschreibung)
    {
        begegnungen.addSpiel(tora, torb, landa, landb, beschreibung);
    }
        
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void getEverySpielDetails()
    {
        System.out.println("Spiel-Liste: ");
        for(int i = 0; i < begegnungen.getSpieleSize(); i++) {
            System.out.println(begegnungen.getSpielDetails(i));
        }
        System.out.println();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public boolean speichereLand(String land, int tore, int punkte)
    {
        if(gibDatenSpielergebnis(land, tore, punkte) == null){
            System.out.println("Das Land " + land + " existiert nicht");
            return false;
        }
        else{
            try{
                daten.landSpeichern(gibDatenSpielergebnis(land, tore, punkte));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private String gibDatenSpielergebnis(String land, int tore, int punkte)
    {
        boolean check = false;
        String daten = "";

        for (String key : gruppen.keySet()) {
            Gruppe gruppe = gruppen.get(key);

            if(gruppe.existiertLand(land) == true){
                check = true;      
                daten = gruppe.gibUpdatedInfoLand(land, tore, punkte);
            }
        }

        if(check == false){
            return null;
        }

        return daten;
    }
}