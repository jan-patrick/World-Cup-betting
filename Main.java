import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;
import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.net.URI;
import java.awt.Desktop;

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
    private int gruppenAnzahl = 0;
    private int laenderAnzahl = 0;
    private final int MIN_GRUPPEN_ANZAHL = 2;
    private final int MAX_GRUPPEN_ANZAHL = 8;
    private final int MIN_LAENDER_ANZAHL = 8;
    private final int MAX_LAENDER_ANZAHL = 32;
    private final String STANDARD_TURNIERNAME = "WM2018";
    private String turnierName = STANDARD_TURNIERNAME;
    
    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main()
    {
        gruppen = new HashMap<>();
        daten = new Daten();
        mainInterface = new Interface();
        ladeGruppen();
        loadTurnierName();
    }
    
    /**
     * Öffnet das GitHub Repository im Standardbrowser
     */
    public void openGitHubLink(){
        if(mainInterface.bestaetigen("Projekt von Jan Schneider","Quellcode auf Github anzeigen?"))
        {
            String[] daten = {"https://github.com/jan-patrick/World-Cup-betting"};
            openLink(daten);
        }    
    }
    
    /**
     * Ändert die Turnierbezeichnung. Lässt nur Eingaben zu, die in den Kontext passen 
     * (angedacht EM oder WM + Jahreszahl), was durch die Länge der Eingabe grob geprüft wird.
     * Geprüft wird aber nur auf Länge, sodass ein anderer Name auch möglich ist, nur auch um
     * Ausgabefehler zu vermeiden ist die Länge eben eingeschränkt. Außerdem wird die Eingabe auf
     * Buchstaben und Zahlen reduziert, um "   " als Turnier zu vermeiden und andere eventuell
     * anfällige Eingaben direkt auszuschließen
     */
    public void setTurnierName()
    {
        String neuerName = mainInterface.eingabeAufforderungTurniername(turnierName);
        neuerName = neuerName.replaceAll("\\W","");
        if (neuerName!=null || neuerName!= "" || neuerName!= " ")
        {
            if( neuerName.length()<=4)
            {
                mainInterface.nachricht("Fehler", "Der neue Name ist zu kurz.");
            }
            else if(neuerName.length() >=7)
            { 
                mainInterface.nachricht("Fehler", "Der neue Name ist zu lang.");
            }
            else
            {
                try
                {    
                    daten.turniernameSpeichern(neuerName);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                turnierName = neuerName;
            }
        }
        else
        {
            turnierName = STANDARD_TURNIERNAME;
        }    
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void loadTurnierName()
    {
        try
        {    
            turnierName = daten.ladeDatei("allgemein", "turnierName").replaceAll("\\W","");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void ladeVorlage()
    {
        String aktuelledaten = "";
        String[] teile;
        char[] firstLetter = new char[MAX_GRUPPEN_ANZAHL];
        if(mainInterface.bestaetigen("Vorlage laden?",
           "Wenn Sie die Vorlage laden werden alle lokal gespeicherten Daten überschrieben!"))
        {
            for(int i = 0; i < 8; i++)
            {
                firstLetter[i] = (char)(65 + i);
                try
                {    
                    aktuelledaten = daten.ladeVorlage(1,String.valueOf(firstLetter[i]));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                teile = aktuelledaten.split("/");
                //gruppen speichern in datei
                //ladeGruppen();
                System.out.println(aktuelledaten);
            }
            try
            {    
                daten.turniernameSpeichern(daten.ladeVorlage(2,"turnierName").replaceAll("\\W",""));
                System.out.println(daten.ladeVorlage(2,"turnierName").replaceAll("\\W",""));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }    
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
     */
    private void saveLand(String land, int tore, int punkte)
    {
        if(getDatenSpielergebnis(land, tore, punkte) == null){
            System.out.println("Das Land " + land + " existiert nicht");
        }
        else{
            try{
                daten.landSpeichern(getDatenSpielergebnis(land, tore, punkte));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void speichereGruppe(String name, String[] teile)
    {
        try{
            daten.gruppeSpeichern(name, teile);
        }
        catch (Exception e) {
            e.printStackTrace();
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
            aktuelledaten += ",";
        }
        mainInterface.createSpielplan(turnierName, aktuelledaten);
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private String createValideEingabe(String eingabe)
    {
        String ausgabe = eingabe.replaceAll("\\W","");
        ausgabe = ausgabe.substring(0, 1).toUpperCase() + ausgabe.substring(1);
        return ausgabe;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void alleDatenloeschen()
    {
        if(mainInterface.bestaetigen("Achtung!", "Wollen Sie wirklich alle Daten löschen?"))
        {
            deleteAlleDaten();
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void deleteAlleDaten()
    {
        for (String key : gruppen.keySet()) {
            String name = key;
            Gruppe gruppe = gruppen.get(key);
            String[] teile = gruppe.getLaender();
            
            ArrayList where = new ArrayList<String>();
            where.add(key);
            where.add(String.valueOf(gruppe.getGruppenGroesse()));

            for (int i = 0; i < teile.length; i++) {
                String[] datenLand = {teile[i], "0", "0"};
                where.add(teile[i]);
                try{
                    daten.landSpeichern(datenLand);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String[] datenGruppe = new String[where.size()];
            where.toArray( datenGruppe );

            try{
                daten.gruppeReseten(datenGruppe);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            gruppe.deleteSpiele();
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private String sindLaenderInGruppe(String land1, String land2)
    {
        String gruppe1 = "";
        String gruppe2 = "";

        for (String key : gruppen.keySet()) {
            Gruppe gruppe = gruppen.get(key);

            if(gruppe.existiertLand(land1) == true){
                gruppe1 = key;
            }
            if(gruppe.existiertLand(land2) == true){
                gruppe2 = key;
            }
        }

        if(gruppe1 == gruppe2){
            return gruppe1;
        }
        else{return null;}

    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private int[] berechnePunkte(int tore1, int tore2)
    {
        int[] punkte = {0, 0};

        if(tore1 > tore2){punkte[0] = 3;}
        if(tore1 < tore2){punkte[1] = 3;}
        if(tore1 == tore2){punkte[0] = 1; punkte[1] = 1;}
        return punkte;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void openLink(String[] args)
    {
        if(!java.awt.Desktop.isDesktopSupported())
        {
            System.err.println("Desktop is not supported (fatal)");
            System.exit(1);
        }
        if(args.length==0)
        {
            System.out.println( "Usage: OpenURI [URI [URI ... ]]" );
            System.exit( 0 );
        }
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
        if(!desktop.isSupported(java.awt.Desktop.Action.BROWSE))
        {
            System.err.println( "Desktop doesn't support the browse action (fatal)" );
            System.exit( 1 );
        }
        for(String arg:args)
        {
            try{
                java.net.URI uri = new java.net.URI(arg);
                desktop.browse( uri );
            }
            catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void showLandDetails()
    {
        String daten = mainInterface.eingabeAufforderungEinFeld("Zeige Land", "", "Name des Landes");
        if(daten != null){
            String nameLand = createValideEingabe(daten);
            Gruppe gruppe = getGruppeWennLand(nameLand);
            if(gruppe != null){
                String[] torePunkte = gruppe.getLandDetails(nameLand);
                mainInterface.nachricht("Information", "Das Land " + nameLand + " hat " + torePunkte[1] + " Tore geschossen und derzeit " + torePunkte[2] + " Punkte.");
            }
            else mainInterface.nachricht("Fehler", "Das Land " + nameLand + " existiert nicht.");
        }      
    }
}