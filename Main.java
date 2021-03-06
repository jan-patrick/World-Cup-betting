import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Die Klasse Main dient als Hauptklasse und somit zur Ausführung aller Methoden und Funktionen des Programms.
 *  
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.06.30
 * 
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
     * Lädt bei alle Daten aus den lokalen Textdateien. 
     * Sollte dabei ein Fehler auftreten wird der Nutzer darüber informiert und die Funktion nochRetten() gestartet.
     */
    private void loadInitalData()
    {
        try
        {
            ladeGruppen();
            loadTurnierName();
            ladeSpieleErgebnisse();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            nochRetten();
        }
    }
    
    /**
     * Öffnet das GitHub Repository dieses Programms im Standardbrowser nachdem der Nutzer dies bestätigt hat.
     */
    public void oeffneGithubLink()
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
        if (neuerName!=null && !neuerName.equals(""))
        {
            neuerName = createValideEingabe(neuerName);
            if( neuerName.length()<=4)
            {
                mainInterface.nachricht("Fehler", 
                "Der neue Name ist zu kurz. Er darf nur aus Buchstaben und Zahlen bestehen.");
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
                    nochRetten();
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
        deleteAktuelleTurnierDaten();
        deleteAlleDaten();
        char[] firstLetter = new char[MAX_GRUPPEN_ANZAHL];
        // lade GRUPPEN
        try
        {
            String gruppenDaten = daten.ladeVorlage("gruppen","Gruppen");
            String[] gruppenData = gruppenDaten.split("/");
            gruppenAnzahl = gruppenDaten.replaceAll("\\W","").length();
            daten.speichereDatei("gruppen", "Gruppen", gruppenData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for(int i = 0; i < gruppenAnzahl; i++)
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
        ladeSpieleErgebnisse();
    }
    
    /**
     * Wird dann aufgerufen, wenn eine Methode lokale Daten nicht korrekt auslesen kann.
     * Dies ist praktisch eine Art Reset, wenn etwas nicht mehr funktioniert.
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
     * Lädt zuerst aus der Gruppenübersicht (Gruppen/Gruppen.txt) alle Gruppennamen und anschließend darauf basierend alle Gruppen samt Daten in das Programm.
     */
    private void ladeGruppen()
    {
        String aktuelledaten = "";
        String[] teile;
        try
        {
            aktuelledaten = daten.ladeDatei("Gruppen", "Gruppen").replaceAll("//","/");
            gruppenAnzahl = aktuelledaten.replaceAll("\\W","").length();
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
     * Lädt alle Spielergebnisse aus den Textdateien und speichert sie in die Programmvariablen ein, inklusive der Landdaten.
     */
    private void ladeSpieleErgebnisse()
    {
        char[] firstLetter = new char[gruppenAnzahl];
        String[] aktuelledaten = {};
        String[] interessanteDaten = {};
        String[] beideLaender = {};
        String[] beideTore ={};
        for(int i = 0; i < gruppenAnzahl; i++)
        {
            firstLetter[i] = (char)(65 + i);
            try
            {    
                String aktuelledatengruppe = daten.ladeDatei("gruppen",String.valueOf(firstLetter[i]));
                aktuelledaten = aktuelledatengruppe.split("/");
                int gespeicherteGruppengroesse = Integer.valueOf(aktuelledaten[0]);
                int anzahlSpiele = binominalkoeffizient(gespeicherteGruppengroesse, 2);
                int gesamt = gespeicherteGruppengroesse+anzahlSpiele;
                for(int z = gespeicherteGruppengroesse+1; z <= gesamt; z++)
                {
                    interessanteDaten = aktuelledaten[z].split("-");
                    if(interessanteDaten[0].length()<3)
                    {
                        interessanteDaten[0] = " : ";
                    }
                    beideLaender = interessanteDaten[0].split(":");
                    if(interessanteDaten[1].length()<3)
                    {
                        interessanteDaten[1] = " : ";
                    }
                    beideTore = interessanteDaten[1].split(":");
                    if(beideTore==null || beideTore[0].isEmpty() || beideTore[1].isEmpty()
                       || !isInteger(beideTore[0].replaceAll("\\W","")) 
                       || !isInteger(beideTore[1].replaceAll("\\W","")))
                    {
                        beideTore[0] = String.valueOf(0);
                        beideTore[1] = String.valueOf(0);
                    }
                    int[] punkte = berechnePunkte(Integer.valueOf(beideTore[0].replaceAll("\\W","")), 
                                                  Integer.valueOf(beideTore[1].replaceAll("\\W","")));
                    Gruppe gruppe1 = getGruppeWennLand(beideLaender[0].replaceAll("\\W",""));            
                    gruppe1.landSetTorePunkte(beideLaender[0].replaceAll("\\W",""),
                                              Integer.valueOf(beideTore[0].replaceAll("\\W","")), 
                                              punkte[0]);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                nochRetten();
            }
        }
    }    
    
    /**
     * Öffnet ein Fenster das eine Eingabe erfordert. Um ein Land zu entfernen müssen alle Daten gelöscht werden.
     * Ist dies Erwünscht wird das Land entfernt, alle Paarungen neu berechnet und Daten aktualisiert und gespeichert.
     * @ToDo
     */
    public void entferneLand()
    {
        String check = mainInterface.eingabeAufforderungEinFeld("Land Entfernen", "Wenn sie ein Land aus einer Gruppe entfernen werden alle Daten resetted.", "Name des Landes");
        if(check != null){
            String nameLand = createValideEingabe(check);
            Gruppe gruppe = getGruppeWennLand(nameLand);
            if(gruppe != null){
                if(mainInterface.bestaetigen("Bestätigung", "Wollen sie wirklich alle Einträge löschen?") == true){
                    deleteAktuelleTurnierDaten();
                    String name = gruppe.getGruppenName();
                    String[] teile = gruppe.getDatenTeile("Gruppen", name);
                    int gruppenGroesse = Integer.valueOf(teile[0]);
                    teile[0] = String.valueOf(gruppenGroesse - 1);

                    ArrayList aktuelledaten = new ArrayList<String>();
                    for (int i = 0; i < teile.length; i++) {
                        aktuelledaten.add(teile[i]);
                    }
                    aktuelledaten.remove(nameLand);
                    String[] datenGruppe = new String[aktuelledaten.size()];
                    aktuelledaten.toArray( datenGruppe );
                    saveGruppe(name, datenGruppe);
                    gruppe.deleteLaender();
                    gruppe.loadGruppeninfo(name);
                    try{
                        daten.deleteDatei("Laender", name + ".txt");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else mainInterface.nachricht("Fehler", "Das Land " + nameLand + " existiert nicht.");
        }
    }

    /**
     * Öffnet ein Fenster in dem der Name der zu entfernenden Gruppe eingegeben werden kann.
     * 2 Gruppen sind als Standard nicht zu entfernen. Wenn eine Gruppe entfernt wird werden auch deren Länder gelöscht.
     */
    private void entferneGruppe()
    {
        String aktuelledaten = mainInterface.eingabeAufforderungEinFeld("Gruppe entfernen", 
            "Wenn Sie eine Gruppe entfernen werden deren Länder ebenfallsgelöscht.", "Name der Gruppe");                 
        if(aktuelledaten != null){
            char[] firstLetter = new char[MIN_GRUPPEN_ANZAHL];
            ArrayList<String> gruppenFest = new ArrayList<String>();
            for(int i = 0; i < MIN_GRUPPEN_ANZAHL; i++)
            {
                firstLetter[i] = (char)(65 + i);
                gruppenFest.add(String.valueOf(firstLetter[i]));
            }
            String nameGruppe = createValideEingabe(aktuelledaten);
            if(!gruppenFest.contains(nameGruppe))
            {
                if(gruppen.containsKey(nameGruppe))
                {
                    Gruppe gruppe = gruppen.get(nameGruppe);
                    String[] laender = gruppe.getLaender();
                    for (int i = 0; i < laender.length; i++)
                    {
                        try
                        {
                            daten.deleteDatei("Laender", laender[i]);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }                
                    gruppen.remove(nameGruppe);
                    String aktuellDaten = getGruppenAsString();
                    String[] datenGruppen =aktuellDaten.split("/");
                    try
                    {
                        daten.speichereDatei("gruppen", "Gruppen", datenGruppen);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        daten.deleteDatei("Gruppen", nameGruppe);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else mainInterface.nachricht("Fehler", "Die Gruppe " + nameGruppe + " existiert nicht.");
            }
            else mainInterface.nachricht("Fehler", "Die Gruppe " + nameGruppe + " darf nicht entfernt werden.");
        }
    }
    
    /**
     * Gibt alle Gruppen als String zurück (z.B. A/B/C/D)
     * 
     * @return Gruppen als String
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
     * Ändert an einem Land die Tore und Punkte.
     * 
     * @param land Name des Landes
     * @param tore Tore des Landes
     * @param punkte Punkte des Landes
     */
    private void addLand(String land, int tore, int punkte)
    {
        if(getDatenSpielergebnis(land, tore, punkte) == null)
        {
            mainInterface.nachricht("Fehler", "Das Land " + land + " existiert nicht.");
        }
        else
        {
            Gruppe gruppe = getGruppeWennLand(land);
            try
            {
                daten.speichereDatei("laender", land, getDatenSpielergebnis(land, tore, punkte));
                gruppe.getUpdatedInfoLand(land, tore, punkte);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                nochRetten();
            }
        }
    }
    
    /**
     * Speichert die eingegebene Gruppe samt Daten in ihrer Textdatei.
     * 
     * @param name Name der Gruppe
     * @param teile Gruppendaten
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
            nochRetten();
        }
    }
    
    /**
     * Updated die Daten eines Landes und gibt die neuen Werte zurück.
     * 
     * @param land
     * @param tore
     * @param punkte
     * @return  neue Landdetails
     */
    private String[] getDatenSpielergebnis(String land, int tore, int punkte)
    {
        Gruppe gruppe = getGruppeWennLand(land);
        return gruppe.getUpdatedInfoLand(land, tore, punkte);
    }
    
    /**
     * Öffnet ein Fenster, über das sich ein Spielergebnis eintragen lässt.
     * Dieses wird geprüft und gespeichert.
     */
    public void spielergebnisEingeben()
    {
        aktualisiereGruppeninfo();
        String[] datenEingabe = mainInterface.eingabeAufforderungSpielergebnis();
        if(datenEingabe != null && datenEingabe[1].replaceAll("\\W","") != null && 
           !datenEingabe[0].replaceAll("\\W","").equals("") &&
           !datenEingabe[1].replaceAll("\\W","").equals("") &&
           !datenEingabe[2].replaceAll("\\W","").equals("") &&
           !datenEingabe[3].replaceAll("\\W","").equals(""))
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
                    else 
                    {
                        gruppe.deleteTorePunkteSpielergebnis(land1, land2);
                    }
                }
                if(gruppe.existiertSpielergebnis(land1, land2)&& check)
                {              
                    String aktuelledaten = land1 + ":" + land2 + "-" + tore1 + ":" + tore2;
                    saveGruppe(nameGruppe, updateGruppeSpielinfo(nameGruppe, land1, land2, aktuelledaten));
                }
                else if(gruppe.existiertSpielergebnis(land2, land1)&& check)
                {
                    String aktuelledaten = land2 + ":" + land1 + "-" + tore2 + ":" + tore1;
                    saveGruppe(nameGruppe, updateGruppeSpielinfo(nameGruppe, land2, land1, aktuelledaten));;
                }
            }
            else
            {
                mainInterface.nachricht("Fehler", "Die Länder existieren nicht oder sind nicht in einer Gruppe.");
            }
            ladeGruppen();
            ladeSpieleErgebnisse();
        }
    } 
    
    /**
     * Lädt die Gruppendaten neu, um diese aktuell zu halten.
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
     * Öffnet ein Fenster, dass es ermöglicht ein neues Land einer Gruppe hinzuzufügen.
     * Danach wird die Eingabe geprüft und gegebenenfalls geprüft.
     */
    public void neuesLand()
    {
        String[] aktuelledaten = mainInterface.eingabeAufforderungNeuesLand();
        if(aktuelledaten != null)
        {
            if(mainInterface.bestaetigen("Achtung", "Wollen sie wirklich alle Einträge löschen?"))
            {
                String gruppe = createValideEingabe(aktuelledaten[0]);
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
     * Öffnet ein Fenster, dass die Eingabe von vier Ländern in die Gruppe ermöglicht (mindestens so viel wie mindestens pro Gruppe definiert).
     * Anschließend wird die Eingabe geprüft und gegebenenfalls gespeichert.
     */
    public void neueGruppe()
    {
        String[] datenAlt = mainInterface.eingabeAufforderungNeueGruppe();
        if(datenAlt != null)
        { 
            ArrayList aktuelledaten = new ArrayList<String>();
            String nameGruppe = String.valueOf((char)(65 + gruppen.size()));
            try
            {
                daten.gruppeAnhaengen(nameGruppe);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            String [] teile = new String[] {"0"};
            saveGruppe(nameGruppe, teile);
            gruppen.put(nameGruppe, new Gruppe(nameGruppe));
            for (int i = 0; i < datenAlt.length; i++)
            {
                String aktuellesLand = createValideEingabe(datenAlt[i]);
                addneuesLand(nameGruppe, aktuellesLand);
            }
        }
    }
    
    /**
     * Nachdem alle bisherigen Ergebnisse gelöscht wurden wird in die eingegebene Gruppe das eingegebene Land hinzugefügt und gespeichert.
     * 
     * @param nameGruppe Name der Gruppe, in die das Land soll
     * @param land Name des neuen Landes
     */
    private void addneuesLand(String nameGruppe, String land)
    {
        deleteAktuelleTurnierDaten();
        Gruppe gruppe = gruppen.get(createValideEingabe(nameGruppe));
        gruppe.addLand(createValideEingabe(land));
        String[] landd = {land, "0", "0"};
        try
        {
        daten.speichereDatei("laender", land, landd);
        }
        catch (Exception e)
        {
                e.printStackTrace();
                nochRetten();
        }
    }
    
    /**
     * Fügt ein aktualisiertes Spielergebnis in die gespeicherten Gruppendaten hinzu und speichert diese wieder ab.
     * 
     * @param nameGruppe Name der Gruppe
     * @param land1 Land 1
     * @param land2 Land 2
     * @param aktuelledaten aktualisierte Spielergebnisse
     * @return aktualisierte Gruppendaten
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
            nochRetten();
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
     * Überprüft, ob zwei eingegebene Länder in derselben Gruppe sind.
     * 
     * @param land1 Land 1
     * @param land2 Land 2
     * @return Gruppe, wenn beide Länder in derselben, ansonsten null
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
     * Öffnet ein Fenster, dass die Eingabe eines Landes ermöglicht. Sofern dieses Land in einer Gruppen enthalten ist wird deren Name ausgegeben.
     */
    public void findeGruppevonLand()
    {
        String aktuellesLand = mainInterface.eingabeAufforderungEinFeld("Gruppe finden", "", "Name des Landes");
        if(aktuellesLand != null)
        {
            String nameLand = createValideEingabe(aktuellesLand);
            Gruppe gruppe = getGruppeWennLand(nameLand);
            if(gruppe == null)
            {
                mainInterface.nachricht("Fehler", "Das Land " + nameLand + " existiert nicht.");
            }
            else
            {
                mainInterface.nachricht("Gruppenanzeige", "Die Gruppe von " + nameLand + 
                                        " ist " + gruppe.getGruppenName() + ".");
            }
        }
    }

    /**
     * Gibt die Gruppe des eingegebenen Landes zurück.
     * 
     * @param land Name des Landes
     * @return Gruppe, falls Land gefunden, ansonsten null
     */
    private Gruppe getGruppeWennLand(String land)
    {
        String aktuelledaten = "";
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
     * Ruft die Funktion in Daten auf, um alle lokalen Textdateien (den gesamten Speicher des Programms) zu löschen.
     * Löscht alle existierenden Textdateien in den Ordnern Allgemein, Gruppen, Laender.
    */
    private void deleteAlleDaten()
    {
        try
        {
        daten.deleteAlleDaten();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Öffnet eine Übersicht, die alle Gruppenphasespiele samt Ergebnissen anzeigt.
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
        loadInitalData();
    }
    
    /**
     * Bearbeitet den eingegebenen String so, dass es bei seiner Rückgabe vom Programm ohne Probleme verwendet werden kann.
     * Überarbeitet werden: <br><b>
     * - Umlaute, z.B. ö zu oe <br>
     * - alles außer Buchstaben und Zahlen gelöscht <br>
     * - Anfangsbuchstabe Versal, alles Nachfolgende als Minuskeln
     * 
     * @param eingabe String, unbearbeitet
     * @return ausgabe String, überarbeitet
     */
    private String createValideEingabe(String eingabe)
    {
        String ausgabe = eingabe.replace("ü", "ue")
                                .replace("ö", "oe")
                                .replace("ä", "ae")
                                .replace("ß", "ss");
        ausgabe = ausgabe.replace("Ü", "Ue")
                         .replace("Ö", "Oe")
                         .replace("Ä", "Ae");
        ausgabe = ausgabe.replaceAll("\\W","");                
        ausgabe = ausgabe.substring(0, 1).toUpperCase() + ausgabe.substring(1).toLowerCase();
        return ausgabe;
    }
    
    /**
     * Ruft nach Bestätigung die Methode auf, um alle Turnierdaten zurückzusetzen (Tore, Punkte, Ergebnisse).
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
     * Gibt die Namen aller existierenden Gruppen als String Array zurück.
     * 
     * @return teile Array mit den Keys aller Gruppen
     */
    private String[] getGruppen()
    {
        StringBuffer aktuelledaten = new StringBuffer();
        for (String key : gruppen.keySet())
        {
            if (aktuelledaten.length() != 0)
            {
                aktuelledaten.append("/");
            }
            aktuelledaten.append(key);         
        }
        String sDaten = daten.toString();
        String[] teile = sDaten.split("/");
        return teile;
    }
    
    /**
     * Löscht alle Turnierdaten (Ergebnisse, Punkte, Tore).
     */
    private void deleteAktuelleTurnierDaten()
    {
        gruppenAnzahl = 0;
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
                    daten.speichereDatei("laender", datenLand[0], datenLand);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    nochRetten();
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
                nochRetten();
            }
            gruppe.deleteSpiele();
        }
    }
    
    /**
     * Rechnet auf Basis der eingegebenen Tore die Punkte beider Länder aus.
     * 
     * @param tore1 Tore von Land 1
     * @param tore2 Tore von Land 2
     * @return punkte als Array (Punkte von Land 1, Punte von Land 2)
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
     * Öffnet ein Fenster, dass die Eingabe eines Lands ermöglicht. Ist es eine valide Eingabe werden die Landdetails angezeigt (Name, Tore, Punkte).
     */
    public void showLandDetails()
    {
        String aktuelledaten = mainInterface.eingabeAufforderungEinFeld("Landdetails", "", "Name des Landes");
        if(aktuelledaten != null)
        {
            String nameLand = createValideEingabe(aktuelledaten);
            Gruppe gruppe = getGruppeWennLand(nameLand);
            if(gruppe != null)
            {
                String[] torePunkte = gruppe.getLandDetails(nameLand);
                if(Integer.parseInt(torePunkte[1]) == 0)
                {
                    mainInterface.nachricht("Landdetails", "Das Land " + nameLand + " hat bisher " 
                                        + torePunkte[1] + " Tore geschossen und damit 0 Punkte erspielt.");
                }
                else
                {
                mainInterface.nachricht("Landdetails", "Das Land " + nameLand + " hat bisher " 
                                        + torePunkte[1] + " Tore geschossen und damit " + torePunkte[2] + " Punkte erspielt.");
                }
            }
            else
            {
                mainInterface.nachricht("Fehler", "Das Land " + nameLand + " existiert nicht.");
            }
        }      
    }
    
    /**
     * Überprüft, ob der übergebene String ein valider Integer ist.
     * 
     * @param s zu überprüfender String
     * @return true, wenn String einen validen Integer ergibt, ansosnten false
     */
    private static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try
        {
            Integer.parseInt(s);
            // s is a valid integer
            isValidInteger = true;
        }
        catch (NumberFormatException ex)
        {
            // s is not an integer
        }
        return isValidInteger;
    }
    
    /**
     * Berechnet mathematisch korrekt die Anzahl der Spiele, die aus der Länderanzahl erfolgen muss
     * 
     * @param n Basis
     * @param k Quadratzahl
     * @return binominalkoeffizient
     */
    private int binominalkoeffizient(int n, int k)
    {        
        if (k > n) return 0;
        else 
        {
            int a = 1;
            for (int i = n-k+1; i <= n; i++) a *= i;
            int b = 1;
            for (int i = 2; i <= k; i++) b *= i;
            return a / b;
        }
    }
}