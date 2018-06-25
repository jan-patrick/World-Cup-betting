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
            ladeSpieleErgebnisse();
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
        char[] firstLetter = new char[MAX_GRUPPEN_ANZAHL];
        // lade GRUPPEN
        try
        {
            gruppenAnzahl = daten.ladeVorlage("gruppen","Gruppen").replaceAll("\\W","").length();
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
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void ladeSpieleErgebnisse()
    {
        char[] firstLetter = new char[MAX_GRUPPEN_ANZAHL];
        String[] aktuelledaten = {};
        String[] interessanteDaten = {};
        String[] beideLaender = {};
        String[] beideTore ={};
        for(int i = 0; i < gruppenAnzahl; i++)
        {
            firstLetter[i] = (char)(65 + i);
            try
            {    
                String aktuelledatengruppe = daten.ladeVorlage("gruppen",String.valueOf(firstLetter[i]));
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
                        daten.deleteDatei("Laender", name);
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
     * @param land
     * @param tore
     * @param punkte
     */
    private void addLand(String land, int tore, int punkte)
    {
        if(getDatenSpielergebnis(land, tore, punkte) == null)
        {
            System.out.println("Das Land " + land + " existiert nicht");
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
            nochRetten();
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
    public void spielergebnisEingeben()
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
    public void findeGruppevonLand()
    {
        String aktuellesLand = mainInterface.eingabeAufforderungEinFeld("Gruppe finden", "", "Name des Landes");
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

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private Gruppe getGruppeWennLand(String land)
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
        String aktuelledaten = mainInterface.eingabeAufforderungEinFeld("Landdetails", "", "Name des Landes");
        if(daten != null)
        {
            String nameLand = createValideEingabe(aktuelledaten);
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
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
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
     * Noch zu Beschreiben
     * 
     * @ToDo
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