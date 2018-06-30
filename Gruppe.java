import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Die Klasse Gruppe beinhaltet alle Länder der Gruppe und ebenso deren Gruppenpahsenspiele sowie deren Ergebnisse.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.06.30
 */
public class Gruppe
{
    private HashMap<String, Land> laender;
    private HashMap<String, String> gruppenphaseSpiele;
    private String gruppenName;
    private int gruppenGroesse;
    private Daten daten;
 
    /**
     * Konstruktor der Klasse Gruppe
     * 
     * @param gruppenName Der Name der Gruppe, zum Beispiel "A"
     */
    public Gruppe(String gruppenName)
    {
        laender = new HashMap<>();
        gruppenphaseSpiele = new HashMap<>();
        daten = new Daten();
        this.gruppenName = gruppenName;
        loadGruppeninfo(gruppenName);
    }
           
    /**
     * Fügt der Gruppe ein Land hinzu (in die laender HashMap)
     * 
     * @param name Name des hinzuzufügenden Landes
     */
    public void addLand(String name)
    {
        Land land = new Land(name, 0, 0);
        laender.put(name, land);
        gruppenGroesse += 1;
        try
        {
            daten.speichereDatei("laender", name, getLandDetails(name));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String aktuelledaten = getDaten("Gruppen", gruppenName);
        aktuelledaten += name + "/";
        String[] teile = aktuelledaten.split("/");
        teile[0] = String.valueOf(gruppenGroesse);
        try
        {
            daten.speichereDatei("gruppen", gruppenName, teile);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        loadGruppeninfo(gruppenName);
    }
    
    /**
     * Gibt die Anzahl der tatsächlich in der Gruppe gespeicherten Länder zurück.
     * 
     * @return laender.size()
     */
    public int getLaenderGroesse()
    {
        return laender.size();
    }
    
    /**
     * Gibt die Variable Gruppengröße zurück.
     * 
     * @return gruppenGroesse
     */
    public int getGruppenGroesse()
    {
        return gruppenGroesse;
    }
    
    /**
     * Gibt den Gruppennamen zurück.
     * 
     * @return gruppenName
     */
    public String getGruppenName()
    {
        return gruppenName;
    }
    
    /**
     * Lädt ein Land aus den lokal gespeicherten Textdateien.
     * 
     * @param name Name des zu ladenden Landes
     */
    public void ladeLand(String name)
    {
        String[] datenteile = getDatenTeile("Laender", name);
        String nameLand = datenteile[0].replaceAll("\\W","");
        int tore = Integer.valueOf(datenteile[1]);
        int punkte = Integer.valueOf(datenteile[2]);
        laender.put(nameLand, new Land(nameLand, tore, punkte));
    }
       
    /**
     * Überprüft, ob ein Land in der Gruppe existiert.
     * 
     * @param name Name des zu findenden Landes
     * @return true, wenn gefunden, false wenn nicht
     */
    public boolean existiertLand(String name)
    {
        if(laender.containsKey(name))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Überprüft, ob ein Spielergebnis bereits eingetragen ist, oder nicht.
     * 
     * @param land1 Name des ersten Lands
     * @param land2 Name des zweiten Lands
     * @return true, wenn Spielergebnis bereits eingetragen, false wenn nicht
     */
    public boolean existiertSpielergebnis (String land1, String land2)
    {
        String spiel = land1 + ":" + land2;
        String spielRueck = land2 + ":" + land1;
        String check = " : ";
        if(check.equals(gruppenphaseSpiele.get(spiel)) || 
           check.equals(gruppenphaseSpiele.get(spielRueck)))
        {
            return true;
        }
        else return false;
    }
    
    /**
     * Liest Daten aus einer existierenden Datei aus.
     * 
     * @param ordner Ordnername in dem die Datei liegt
     * @param datei Dateiname
     * @return aktuelledaten, ausgelesene Daten aus der Datei
     */
    private String getDaten(String ordner, String datei)
    {
        String aktuelledaten = "";
        try
        {
            aktuelledaten = daten.ladeDatei(ordner, datei);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return aktuelledaten; 
    }
    
    /**
     * Berechnet aus allen existierenden Ländern in der Gruppe alle möglichen Spielpaarungen und speichert diese ab.
     */
    public void berechnePaarungen()
    {
        ArrayList teile = new ArrayList<String>();
        String[] laender = getLaender();
        teile.add(String.valueOf(gruppenGroesse));
        for (int i = 0; i < laender.length; i++)
        {
            teile.add(laender[i]);
        }
        for (int i = 0; i < gruppenGroesse; i++)
        {
            for (int x = i+1; x < gruppenGroesse; x++)
            {
                teile.add(laender[i] + ":" + laender[x] + "- : ");
            }
        }
        String[] aktuelledaten = new String[teile.size()];
        teile.toArray( aktuelledaten );
        try
        {
            daten.speichereDatei("gruppen", gruppenName, aktuelledaten);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
          
    /**
     * Gibt die Details eines Landes zurück.
     * 
     * @param name Name des Landes
     * @return Landdetails (Name, Tore, Punkte)
     */
    public String[] getLandDetails(String name)
    {
        Land land = laender.get(name);
        return land.getDetails();
    }
    
    /**
     * Ändert die gespeicherten Tore und Punkte eines existierenden Landes und gibt die neuen Details zurück.
     * 
     * @param name Name des Landes
     * @param tore neue Tore des Landes
     * @param punkte neue Punkte des Landes
     * @return neue Details des Landes
     */
    public String[] getUpdatedInfoLand(String name, int tore, int punkte)
    {
        Land land = laender.get(name);
        return land.getUpdatedInfo(tore, punkte);
    }
    
    /**
     * Ändert die gespeicherten Tore und Punkte eines existierenden Landes.
     * 
     * @param name Name des Landes
     * @param tore neue Tore des Landes
     * @param punkte neue Punkte des Landes
     */
    public void landSetTorePunkte(String name, int tore, int punkte)
    {
        Land land = laender.get(name);
        land.setTorePunkte(tore, punkte);
    }
    
    /**
     * Lädt aus dem übergebenen String Array die Spiele in die HashMap
     * 
     * @param teile einzelnen Spiele
     */
    public void loadSpiele(String[] teile)
    {
        if(teile.length >= gruppenGroesse+2)
        {
            for (int i = gruppenGroesse+1; i < teile.length; i++)
            {
                String[] aktuelledaten = teile[i].split("-");
                gruppenphaseSpiele.put(aktuelledaten[0], aktuelledaten[1]);
            }
        }
    }
    
    /**
     * Gibt alle Länder als String Array zurück
     * 
     * @return alle existierenden Länder
     */
    public String[] getLaender()
    {
        StringBuffer aktuelleDaten = new StringBuffer();
        for (String key : laender.keySet())
        {
            if (aktuelleDaten.length() != 0)
            {
                aktuelleDaten.append("/");
            }
            aktuelleDaten.append(key);
        }
        String sDaten = aktuelleDaten.toString();
        String[] datenteile = sDaten.split("/");
        return datenteile;
    }
    
    /**
     * Lädt Gruppen.
     * 
     * @param name Name der zu ladenden Gruppe
     */
    public void loadGruppeninfo(String name)
    {
        String[] teile = getDatenTeile("Gruppen", name);
        gruppenGroesse = Integer.valueOf(teile[0]);
        for (int i = 1; i <= gruppenGroesse; i++)
        {
            ladeLand(teile[i]);
        }
        if(teile.length <= gruppenGroesse + 1)
        {
            berechnePaarungen();
            teile = getDatenTeile("Gruppen", name);
        }
        loadSpiele(teile);
    }
    
    /**
     * Gibt aus einer angeforderten Datei die Daten als String Array zurück.
     * 
     * @param ordner Ordner der gesuchten Datei
     * @param datei Dateiname
     * @return Daten im String Array
     */
    public String[] getDatenTeile(String ordner, String datei)
    {
        String aktuelleDaten = "";
        try{
            aktuelleDaten = daten.ladeDatei(ordner, datei);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String[] teile = aktuelleDaten.split("/");
        return teile;
    }
    
    /**
     * Gibt die gespeicherten Spiele zurück
     * 
     * @return Spiele, umsortiert
     */
    public String getSpielergebnisDaten()
    {
        loadGruppeninfo(gruppenName);
        String aktuelledaten = "";
        int laengstesLand = 0;
        ArrayList landEins = new ArrayList<String>();
        ArrayList landZwei = new ArrayList<String>();
        ArrayList torEins = new ArrayList<String>();
        ArrayList torZwei = new ArrayList<String>();
        for (String key : gruppenphaseSpiele.keySet())
        {
            String usedkey = key.replaceAll("\\s","");
            String usedvalue = gruppenphaseSpiele.get(key);
            String landeinsName = usedkey.split(":")[0];
            if(landEins.size()<=0)
            {
                laengstesLand = landeinsName.length();
            }
            else if(landeinsName.length()>laengstesLand)
            {
                laengstesLand = landeinsName.length();
            }    
            landEins.add(landeinsName);
            landZwei.add(usedkey.split(":")[1]);
            usedvalue = usedvalue.replaceAll("\\s","");
            if(usedvalue.length()<3)
            {
                usedvalue = "-:-";
            }  
            for(int k = 0; k < 2; k++)
            {
                if(usedvalue.split(":")[k].length() > 0 && usedvalue.split(":")[k] != null
                   && isInteger(usedvalue.split(":")[k]))
                {
                    if(k==0)
                    {
                        torEins.add(usedvalue.split(":")[k]);
                    }
                    else
                    {
                        torZwei.add(usedvalue.split(":")[k]);
                    }
                }
                else
                {
                    if(k==0)
                    {
                        torEins.add("-");
                    }
                    else
                    {
                        torZwei.add("-");
                    }
                }
            }
        }
        for(int m = 0; m < landEins.size(); m++)
        {
            String outputLandEins = landEins.get(m).toString();
            if(outputLandEins.length()<laengstesLand)
            {
                int h = laengstesLand-outputLandEins.length();
                addspace(h,outputLandEins);               
            } 
            aktuelledaten += (outputLandEins + " " + torEins.get(m) + ":" +torZwei.get(m) 
                             + " " + landZwei.get(m) +" <br>");
        }
        if(aktuelledaten.isEmpty())
        {
            aktuelledaten += "-------------";
        }
        return aktuelledaten;
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
     * Löscht ein spezifisches Spielergebnis
     * 
     * @param land1 Land 1 des Spiels
     * @param land2 Land 2 des Spiels
     */
    public void deleteTorePunkteSpielergebnis (String land1, String land2)
    {
        String spielVor = land1 + ":" + land2;
        String spielRück = land2 + ":" + land1;
        String spiel = "";
        String aktuelledaten = "";
        String landEINS = land1;        
        String landZWEI = land2;
        if(gruppenphaseSpiele.containsKey(spielVor))
        {
            spiel = spielVor;
        }
        if(gruppenphaseSpiele.containsKey(spielRück))
        {
            spiel = spielRück;
            landEINS = land2;
            landZWEI = land1;
        }
        Land landEins = laender.get(landEINS);
        Land landZwei = laender.get(landZWEI);
        aktuelledaten = gruppenphaseSpiele.get(spiel);
        String[] teile = aktuelledaten.split(":");
        int tore1 = Integer.valueOf(teile[0].replaceAll("\\W",""));
        int tore2 = Integer.valueOf(teile[1].replaceAll("\\W",""));
        if(tore1 == tore2)
        {
            landEins.zieheWerteAb(tore1, 1);
            landZwei.zieheWerteAb(tore2, 1);
        }
        if(tore1 < tore2)
        {
            landEins.zieheWerteAb(tore1, 0);
            landZwei.zieheWerteAb(tore2, 3);
        }
        if(tore1 > tore2)
        {
            landEins.zieheWerteAb(tore1, 3);
            landZwei.zieheWerteAb(tore2, 0);
        }
    }
    
    /**
     * Überprüft, ob ein Spielergebnis bereits existiert.
     * 
     * @param land1 Land 1 des Spiels
     * @param land2 Land 2 des Spiels
     * @return true, wenn ja, sonst false
     */
    public boolean pruefeExistenzSpielergebnis (String land1, String land2)
    {
        String spiel = land1 + ":" + land2;
        String spielRück = land2 + ":" + land1;
        String empty = " : ";
        if(empty.equals(gruppenphaseSpiele.get(spiel)) || 
           empty.equals(gruppenphaseSpiele.get(spielRück)))
        {
            return true;
        }
        else return false;

    }
    
    /**
     * Fügt dem übergebenen String Leerzeichen hinzu (voraus).
     * 
     * @param i Anzahl der hinzuzufügenden Leerzeichen
     * @param str zu verändernder String
     * @return String mit Leerzeichen
     */
    String addspace(int i, String str)
    {       
        StringBuilder ausgabe = new StringBuilder();
        for(int j=0;j<i;j++)
        {
            ausgabe.append(" ");
        }
        ausgabe.append(str);           
        return ausgabe.toString();         
    }
    
    /**
     * Löscht sämtlichen Inhalt der Gruppenphase-Spiele-HashMap
     */
    public void deleteSpiele()
    {
        gruppenphaseSpiele.clear();
    }

    /**
     * Löscht sämtlichen Inhalt der Länder-HashMap
     */
    public void deleteLaender()
    {
        laender.clear();
    }
}
