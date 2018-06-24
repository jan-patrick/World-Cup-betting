import java.util.ArrayList;
import java.util.Arrays;
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
    private void loadInitalData()
    {
        try
        {
            ladeGruppen();
            loadTurnierName();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            nochRetten();
        }
    }
    
    /**
     * Öffnet das GitHub Repository dieses Programms im Standardbrowser nachdem der Nutzer dies bestätigte.
     */
    public void openGithubLink()
    {
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
     * Lädt Gruppen, Turniernamen und Länder aus dem Github Repository und überschreibt alle lokalen Daten.
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
        String[] vorlagelaender = {};
        try
        {
            vorlagelaender = daten.ladeVorlage("laender","Laender").split("/");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for(int z = 0; z < vorlagelaender.length; z++)
        {
            try
            {   
                String aktuelledatenla = daten.ladeVorlage("laender",vorlagelaender[z].replaceAll("\\W",""));
                String[] aktuelledatenl = aktuelledatenla.split("/");
                daten.speichereDatei("laender", vorlagelaender[z], aktuelledatenl);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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
           "Die lokalen Daten scheinen beschädigt zu sein. Sollen Standarddaten der " + STANDARD_TURNIERNAME + " geladen werden? Eine Internetverbindung wird benötigt."))
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
        try
        {
            aktuelledaten = daten.ladeDatei("Gruppen", "Gruppen");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            nochRetten();
        }
        teile = aktuelledaten.split("/");
        for(int k = 0; k < teile.length; k++)
        {
            teile[k] = createValideEingabe(teile[k]);
        }    
        for (int i = 0; i < teile.length; i++)
        {
            gruppen.put(teile[i], new Gruppe(teile[i]));
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void createGruppen()
    {
        if(gruppenAnzahl>=MAX_GRUPPEN_ANZAHL)
        {            
            mainInterface.nachricht("Fehler", "Es sind zu viele Gruppen im System.");
        }
        else if(gruppenAnzahl != 0)
        {
            mainInterface.nachricht("Fehler", "Es sind bereits Gruppen im System.");
        }
        else
        {     
            char[] firstLetter = new char[MAX_GRUPPEN_ANZAHL];
            // unicode upper-cased alphabet
            for(int i = 0; i < MAX_GRUPPEN_ANZAHL; i++)
            {
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
    private String getGruppenAsString()
    {
        String ergebnis = "";
        Set<String> keywords = gruppen.keySet();
        for(String gruppe : keywords)
        {
            ergebnis += "/" + gruppe;
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
    public void addLand(String land, int tore, int punkte)
    {
        if(getDatenSpielergebnis(land, tore, punkte) == null)
        {
            System.out.println("Das Land " + land + " existiert nicht");
        }
        else
        {
            try
            {
                daten.landSpeichern(getDatenSpielergebnis(land, tore, punkte));
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
    private void saveGruppe(String name, String[] teile)
    {
        try
        {
            daten.speichereDatei("gruppen", name, teile);
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
     * 
     * @param String land, int tore, int punkte
     * @return String updated Land Info (name, tore, punkte)
     */
    public void updateSpielergebnis()
    {
        aktualisiereGruppeninfo();
        String[] datenEingabe = mainInterface.eingabeAufforderungSpielergebnis();
        if(datenEingabe != null && datenEingabe[1].replaceAll("\\W","") != null && 
           datenEingabe[1].replaceAll("\\W","") != "")
        { 
            int tore1 = Integer.valueOf(datenEingabe[1].replaceAll("\\W",""));
            int tore2 = Integer.valueOf(datenEingabe[3].replaceAll("\\W",""));
            String land1 = createValideEingabe(datenEingabe[0]);
            String land2 = createValideEingabe(datenEingabe[2]);
            boolean check = true;
            int[] punkte = berechnePunkte(tore1, tore2);
            String nameGruppe = LaenderInGruppe(land1, land2);
            if(nameGruppe != null)
            {
                Gruppe gruppe = gruppen.get(nameGruppe);
                if (!gruppe.existiertSpielergebnis(land1, land2))
                {
                    if(!mainInterface.bestaetigen("Fehler", "Das Ergebnis wurde bereits eingegeben. Möchten Sie die alte Eingabe überschreiben?"))
                    {
                        check = false;
                    }
                    else gruppe.deleteTorePunkteSpielergebnis(land1, land2);
                }
                if(gruppe.existiertSpielergebnis(land1, land2)&& check)
                {              
                    addLand(land1, tore1, punkte[0]); 
                    addLand(land2, tore2, punkte[1]);
                    String daten = land1 + ":" + land2 + "-" + tore1 + ":" + tore2;
                    saveGruppe(nameGruppe, updateGruppeSpielinfo(nameGruppe, land1, land2, daten));
                    gruppe.loadGruppeninfo(nameGruppe);
                }
                else if(gruppe.existiertSpielergebnis(land2, land1)&& check)
                {
                    addLand(land1, tore1, punkte[0]); 
                    addLand(land2, tore2, punkte[1]);
                    String daten = land2 + ":" + land1 + "-" + tore2 + ":" + tore1;
                    saveGruppe(nameGruppe, updateGruppeSpielinfo(nameGruppe, land2, land1, daten));
                    gruppe.loadGruppeninfo(nameGruppe);
                }
            }
            else
            {
                mainInterface.nachricht("Fehler", "Die Länder existieren nicht oder sind nicht in einer Gruppe.");
            }
        }
    } 
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void aktualisiereGruppeninfo()
    {
        for (String key : gruppen.keySet())
        {
            Gruppe gruppe = gruppen.get(key);
            gruppe.loadGruppeninfo(key);
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void neuesLand()
    {
        String[] aktuelledaten = mainInterface.eingabeAufforderungNeuesLand();
        if(aktuelledaten != null)
        {
            if(mainInterface.bestaetigen("Achtung", "Wollen sie wirklich alle Einträge löschen?"))
            {
                String gruppe = aktuelledaten[0];
                if(gruppen.containsKey(gruppe))
                {
                    String name = createValideEingabe(aktuelledaten[1]);
                    addneuesLand(gruppe, name);
                }
                else 
                {
                    mainInterface.nachricht("Fehler", "Die Gruppe " + gruppe + " existiert nicht.");
                }    
            }
        }
    } 
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void addneuesLand(String nameGruppe, String land)
    {
        deleteAktuelleTurnierDaten();
        Gruppe gruppe = gruppen.get(createValideEingabe(nameGruppe));
        gruppe.addLand(createValideEingabe(land));
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private String[] updateGruppeSpielinfo(String nameGruppe, String land1, String land2, String aktuelledaten)
    {        
        Gruppe gruppe = gruppen.get(nameGruppe);
        String gruppenDatenAlt = "";
        try
        {
            gruppenDatenAlt = daten.ladeDatei("Gruppen", nameGruppe);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String[] teileGruppenDatenAlt = gruppenDatenAlt.split("/");
        for (int i = 0; i < teileGruppenDatenAlt.length; i++)
        {
            String check = teileGruppenDatenAlt[i];
            if(check.contains(land1 + ":" + land2))
            {
                teileGruppenDatenAlt[i] = aktuelledaten;
            }
        }
        return teileGruppenDatenAlt;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private String LaenderInGruppe(String land1, String land2)
    {
        String gruppe1 = "";
        String gruppe2 = "";
        for (String key : gruppen.keySet())
        {
            Gruppe gruppe = gruppen.get(key);
            if(gruppe.existiertLand(land1))
            {
                gruppe1 = key;
            }
            if(gruppe.existiertLand(land2))
            {
                gruppe2 = key;
            }
        }
        if(gruppe1 == gruppe2)
        {
            return gruppe1;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public Gruppe getGruppeWennLand(String land)
    {
        String daten = "";
        for (String key : gruppen.keySet())
        {
            Gruppe gruppe = gruppen.get(key);
            if(gruppe.existiertLand(land))
            {
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
        for (String key : gruppen.keySet())
        {
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
    public void alleTurnierDatenloeschen()
    {
        if(mainInterface.bestaetigen("Achtung!", 
           "Wollen Sie wirklich die Ergebnisse, Tore und Punkte des Turniers löschen?"))
        {
            deleteAktuelleTurnierDaten();
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void deleteAlleDaten()
    {
        if(mainInterface.bestaetigen("Achtung!", 
           "Wollen Sie wirklich alle lokal gespeicherten Daten löschen?"))
        {
            int anzahlGruppen = 0;
            char[] firstLetter = new char[MAX_GRUPPEN_ANZAHL];
            try
            {
                anzahlGruppen = daten.ladeVorlage("gruppen","Gruppen").replaceAll("\\W","").length();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try
            {
                daten.deleteAlleDaten("turniername", "turnierName");
                daten.deleteAlleDaten("gruppen", "Gruppen");
                for(int i = 0; i < anzahlGruppen; i++)
                {
                    firstLetter[i] = (char)(65 + i);
                    daten.deleteAlleDaten("gruppen", String.valueOf(firstLetter[i]));
                }
            }
            catch(Exception e)
            {
                System.err.println(e.getMessage());
            }            
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private void deleteAktuelleTurnierDaten()
    {
        for (String key : gruppen.keySet())
        {
            String name = key;
            Gruppe gruppe = gruppen.get(key);
            String[] teile = gruppe.getLaender();  
            ArrayList where = new ArrayList<String>();
            where.add(key);
            where.add(String.valueOf(gruppe.getGruppenGroesse()));
            for (int i = 0; i < teile.length; i++)
            {
                String[] datenLand = {teile[i], "0", "0"};
                where.add(teile[i]);
                try
                {
                    daten.landSpeichern(datenLand);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            String[] datenGruppe = new String[where.size()];
            where.toArray( datenGruppe );
            try
            {
                daten.gruppeReseten(datenGruppe);
            }
            catch (Exception e)
            {
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
        for (String key : gruppen.keySet())
        {
            Gruppe gruppe = gruppen.get(key);
            if(gruppe.existiertLand(land1))
            {
                gruppe1 = key;
            }
            if(gruppe.existiertLand(land2))
            {
                gruppe2 = key;
            }
        }
        if(gruppe1 == gruppe2)
        {
            return gruppe1;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private int[] berechnePunkte(int tore1, int tore2)
    {
        int[] punkte = {0, 0};
        if(tore1 > tore2)
        {
            punkte[0] = 3;
        }
        if(tore1 < tore2)
        {
            punkte[1] = 3;
        }
        if(tore1 == tore2)
        {
            punkte[0] = 1; 
            punkte[1] = 1;
        }
        return punkte;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void showLandDetails()
    {
        String daten = mainInterface.eingabeAufforderungEinFeld("Landdetails", "", "Name des Landes");
        if(daten != null)
        {
            String nameLand = createValideEingabe(daten);
            Gruppe gruppe = getGruppeWennLand(nameLand);
            if(gruppe != null)
            {
                String[] torePunkte = gruppe.getLandDetails(nameLand);
                mainInterface.nachricht("Landdetails", "Das Land " + nameLand + " hat bisher " 
                                        + torePunkte[1] + " Tore geschossen und damit " + torePunkte[2] + " Punkte erspielt.");
            }
            else {
                mainInterface.nachricht("Fehler", "Das Land " + nameLand + " existiert nicht.");
            }
        }      
    }
}