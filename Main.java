import java.util.ArrayList;
import java.lang.*;
import java.io.*;
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
    private HashMap<String, Spiel> spiele;
    private Daten daten;
    private Interface mainInterface;
    private Startscreen startscreenReadMe;
    private int gruppenAnzahl = 0;
    private int laenderAnzahl = 0;
    private final int MAX_GRUPPEN_ANZAHL = 8;
    private final int MAX_LAENDER_ANZAHL = 32;
    

    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main()
    {
        // Instanzvariable initialisieren
        gruppen = new HashMap<>();
        spiele = new HashMap<>();
        daten = new Daten();
        mainInterface = new Interface();
        startscreenReadMe = new Startscreen();
        startscreenReadMe.main();
        //mainInterface.setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png"));
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void erstelleGruppen()
    {
        char[] alpha = new char[26];
        // unicode upper-cased alphabet
        for(int i = 0; i < 26; i++){
            alpha[i] = (char)(96 + i);
        }
        
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
        spiele.put( "0", new Spiel( tora, torb, landa, landb, beschreibung));
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void setProducts(ArrayList<Land> laender) {
        spiele.put( "a", new Spiel( 1, 1, "Griechenland", "Südamerika", "Do, 13.13.13"));
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public boolean speichereLand(String land, int tore, int punkte)
    {
        if(getDatenSpielergebnis(land, tore, punkte) == null){
            System.out.println("Das Land " + land + " existiert nicht");
            return false;
        }
        else{
            try{
                daten.landSpeichern(getDatenSpielergebnis(land, tore, punkte));
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
    private String[] getDatenSpielergebnis(String land, int tore, int punkte)
    {
        Gruppe gruppe = getGruppeWennLand(land);
        return gruppe.getUpdatedInfoLand(land, tore, punkte);
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public Gruppe getGruppeWennLand(String land)
    {
        String daten = "";

        for (String key : gruppen.keySet()) {
            Gruppe gruppe = gruppen.get(key);

            if(gruppe.existiertLand(land) == true){
                return gruppe;
            }
        }

        return null;
    }
}