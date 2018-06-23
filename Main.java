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
        loadInitalData();
    }
    
    /**
     * Lädt bei Programmstart alle Daten aus den lokalen Textdateien. Sollte dabei ein Fehler auftreten 
     * wird der Nutzer darüber informiert und die Funktion nochRetten() gestartet.
     */
    private void loadInitalData(){
        try{
            ladeGruppen();
            loadTurnierName();
        }
        catch (Exception e) {
            e.printStackTrace();
            nochRetten();
        }
    }
    
    /**
     * Öffnet das GitHub Repository dieses Programms im Standardbrowser nachdem der Nutzer dies bestätigte.
     */
    public void openGitHubLink(){
        if(mainInterface.bestaetigen("Projekt von Jan Schneider","Quellcode auf Github anzeigen?"))
        {
            String[] aktuelledaten = {"https://github.com/jan-patrick/World-Cup-betting"};
            daten.openLink(aktuelledaten);
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
                    String[] aktuelledaten = {neuerName};
                    daten.speichereDatei("turniername", "turnierName", aktuelledaten);
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
     * Lädt den Turniernamen aus der lokalen Textdatei und übergibt den Wert dem zugehörigen Java String.
     * Sollte dabei etwas schief laufen gehen wird die Funktion nochRetten() aufgerufen.
     */
    private void loadTurnierName()
    {
        try
        {    
            turnierName = daten.ladeDatei("allgemein", "turnierName").replaceAll("\\W","");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            nochRetten();
        }
    }
    
    /**
     * Lädt nach Bestätigung manuell die Vorlagendaten aus dem Projekt Repository und überschreibt Lokales.
     */
    public void ladeDatenDerAktuellenWM()
    {
        if(mainInterface.bestaetigen("Vorlage laden?",
           "Wenn Sie die Vorlage laden werden alle lokal gespeicherten Daten überschrieben! Eine Internetverbindung wird benötigt."))
        {
            ladeVorlage();
        }    
    }    
    
    /**
     * Lädt Gruppen, Turniernamen und Länder aus dem Girhub Repository und überschreibt alle lokalen Daten.
     */
    private void ladeVorlage()
    {
        int anzahlGruppen = 0;
        char[] firstLetter = new char[MAX_GRUPPEN_ANZAHL];
        // lade GRUPPEN
        try
        {
            anzahlGruppen = daten.ladeVorlage("gruppen","Gruppen").replaceAll("\\W","").length();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for(int i = 0; i < anzahlGruppen; i++)
        {
            firstLetter[i] = (char)(65 + i);
            try
            {    
                String aktuelledatengr = daten.ladeVorlage("gruppen",String.valueOf(firstLetter[i]));
                String[] aktuelledateng = aktuelledatengr.split("/");
                daten.speichereDatei("gruppen", String.valueOf(firstLetter[i]), aktuelledateng);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        // lade TURNIERNAME
        try
        {   
            String[] aktuelledatenn = {daten.ladeVorlage("turniername","turnierName").replaceAll("\\W","").toString()};
            daten.speichereDatei("turniername", "turnierName", aktuelledatenn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // lade LÄNDER
        for (String key : gruppen.keySet())
        {
            String name = key;
            Gruppe gruppe = gruppen.get(key);
            String[] vorlagelaender = gruppe.getLaender();
            for(int z = 0; z < vorlagelaender.length; z++)
            {
                try
                {   
                    String aktuelledatenla = daten.ladeVorlage("laender",vorlagelaender[z]).replaceAll("\\s","").toString();
                    String[] aktuelledatenl = aktuelledatenla.split("/");
                    daten.speichereDatei("laender", vorlagelaender[z], aktuelledatenl);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        // lade Programmvariablen neu
        ladeGruppen();
        loadTurnierName();  
    }
    
    /**
     * Wird dann aufgerufen, wenn eine Methode lokale Daten nicht korrekt auslesen kann.
     * Dies ist praktisch ein eine Art Reset, wenn etwas nicht mehr funktioniert.
     */
    private void nochRetten()
    {
        if(mainInterface.bestaetigen("beschädigte Daten überschreiben",
           "Die lokalen Daten scheinen beschädigt zu sein. Sollen Standarddaten der "+STANDARD_TURNIERNAME+" geladen werden? Eine Internetverbindung wird benötigt."))
        {
            ladeVorlage();
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
            nochRetten();
        }
        teile = aktuelledaten.split("/");
        for(int k = 0; k < teile.length; k++)
        {
            teile[k] = createValideEingabe(teile[k]);
        }    
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