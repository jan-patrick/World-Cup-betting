import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;
import java.io.*;
import java.util.HashMap;
import java.util.Set;

/**
 * Beschreiben Sie hier die Klasse Main.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 * 
 * - gutes Softwaredesign
 * - keine Codeduplizierung (denselben Code mehrfach, dann auslagern), 
 * - Kopplung (je loser, desto bessere Wartbarkeit)
 * - Datenhaltung (geschlossen, nur über Zugriffsmethoden)
 * - Kohäsion (eine Klasse oder Methode hat möglichst spezifischen Aufgabenbereich hat, 
 *   Chance auf Wiederverwertung höher)
 * - Test (einfaches Testen des Projekts)
 */
public class Main
{
    private HashMap<String, Gruppe> gruppen;
    private Daten daten;
    private Interface mainInterface;
    private Startscreen startscreenReadMe;
    private int gruppenAnzahl = 0;
    private int laenderAnzahl = 0;
    private final int MIN_GRUPPEN_ANZAHL = 2;
    private final int MAX_GRUPPEN_ANZAHL = 8;
    private final int MIN_LAENDER_ANZAHL = 8;
    private final int MAX_LAENDER_ANZAHL = 32;
    private String turnierName;
    
    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main()
    {
        if (turnierName==null){
            this.turnierName = "WM 2018";
        }else{
            this.turnierName = turnierName;
        }
        gruppen = new HashMap<>();
        daten = new Daten();
        mainInterface = new Interface();
        startscreenReadMe = new Startscreen();
        startscreenReadMe.main();
        ladeGruppen();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void ladeGruppen()
    {
        String aktuelledaten = "";
        String[] teile;
        try{
            aktuelledaten = daten.ladeDatei("Gruppen", "Gruppen");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        teile = aktuelledaten.split("/");
        for (int i = 0; i < teile.length; i++) {
            gruppen.put(teile[i], new Gruppe(teile[i]));
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void createGruppen()
    {
        if(gruppenAnzahl>=MAX_GRUPPEN_ANZAHL)
        {            
            mainInterface.nachricht("Fehler", "Es sind zu viele Gruppen im System.");
        }else if(gruppenAnzahl != 0)
        {
            mainInterface.nachricht("Fehler", "Es sind bereits Gruppen im System.");
        }
        else
        {     
            char[] firstLetter = new char[MAX_GRUPPEN_ANZAHL];
            // unicode upper-cased alphabet
            for(int i = 0; i < MAX_GRUPPEN_ANZAHL; i++){
                firstLetter[i] = (char)(65 + i);
                gruppen.put(String.valueOf(firstLetter[i]), new Gruppe(String.valueOf(firstLetter[i])));
                gruppenAnzahl += 1;
            }
        }    
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */ 
    public String getGruppenAsString()
    {
        String ergebnis = "Gruppen: ";
        Set<String> keywords = gruppen.keySet();
        for(String gruppe : keywords){
            ergebnis += " " + gruppe;
        }
        return ergebnis;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     * 
     * @param land, tore, punkte
     * @return boolean
     */
    public boolean saveLand(String land, int tore, int punkte)
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
     * 
     * @param String land, int tore, int punkte
     * @return String updated Land Info (name, tore, punkte)
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
    
    /**
     * Noch zu Beschreiben
     * 
     * @see     alle Gruppenphasespiele, sortiert nach Gruppe
     * @ToDo
     */
    public void getAlleSpielergebnise ()
    {
        String aktuelledaten = "";

        for (String key : gruppen.keySet()) {
            Gruppe gruppe = gruppen.get(key);
            gruppe.loadGruppeninfo(key);
            aktuelledaten += "Gruppe " + key + "<br>";
            aktuelledaten += gruppe.getSpielergebnisDaten();
            aktuelledaten += "!";
        }

        mainInterface.createSpielplan(aktuelledaten);
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private String schreibeGroß(String eingabe)
    {
        String ausgabe = eingabe.substring(0, 1).toUpperCase() + eingabe.substring(1);
        return ausgabe;
    }
}