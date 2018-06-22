import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Beschreiben Sie hier die Klasse Gruppe.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Gruppe
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private HashMap<String, Land> laender;
    private HashMap<String, String> gruppenphaseSpiele;
    private String gruppenName;
    private int gruppenGroesse;
    private Daten daten;
 
    /**
     * Noch zu Beschreiben
     * 
     * @param gruppenName
     * @ToDo
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
     * Noch zu Beschreiben
     * 
     * @param name
     * @ToDo
     */
    public void addLand(String name)
    {
        Land land = new Land(name, 0, 0);
        laender.put(name, land);
        gruppenGroesse += 1;
        try{
            daten.landSpeichern(getLandDetails(name));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String aktuelledaten = getDaten("Gruppen", gruppenName);
        aktuelledaten += name + "/";
        String[] teile = aktuelledaten.split("/");
        teile[0] = String.valueOf(gruppenGroesse);
        try{
            daten.gruppeSpeichern(gruppenName, teile);
        }
        catch (Exception e) {
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
     * Gibt die Gruppengröße zurück.
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
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void ladeLand(String name)
    {
        String[] datenteile = getDatenTeile("Laender", name);

        String nameLand = datenteile[0];
        int tore = Integer.valueOf(datenteile[1]);
        int punkte = Integer.valueOf(datenteile[2]);

        laender.put(nameLand, new Land(nameLand, tore, punkte));
    }
       
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public boolean existiertLand(String name)
    {
        if(laender.containsKey(name)){
            return true;
        }
        else{return false;}
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @param ordner, datei
     * @return aktuelledaten
     * @ToDo
     */
    private String getDaten(String ordner, String datei)
    {
        String aktuelledaten = "";
        try{
            aktuelledaten = daten.ladeDatei(ordner, datei);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return aktuelledaten; 
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void berechnePaarungen()
    {
        ArrayList teile = new ArrayList<String>();
        String[] laender = getLaender();
        teile.add(String.valueOf(gruppenGroesse));

        for (int i = 0; i < laender.length; i++) {
            teile.add(laender[i]);
        }

        for (int i = 0; i < gruppenGroesse; i++) {
            for (int x = i+1; x < gruppenGroesse; x++) {
                teile.add(laender[i] + ":" + laender[x] + "- : ");
            }
        }

        String[] aktuelledaten = new String[teile.size()];
        teile.toArray( aktuelledaten );
        
         try{
            daten.gruppeSpeichern(gruppenName, aktuelledaten);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
          
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String[] getLandDetails(String name)
    {
        Land land = laender.get(name);
        return land.getDetails();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @param name, tore, punkte
     * @ToDo
     */
    public String[] getUpdatedInfoLand(String name, int tore, int punkte)
    {
        Land land = laender.get(name);
        return land.getUpdatedInfo(tore, punkte);
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @param teile
     * @ToDo
     */
    public void loadSpiele(String[] teile)
    {
        if(teile.length >= gruppenGroesse+2){
            for (int i = gruppenGroesse+1; i < teile.length; i++) {
                String[] aktuelledaten = teile[i].split("-");
                gruppenphaseSpiele.put(aktuelledaten[0], aktuelledaten[1]);
            }
        }
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String[] getLaender()
    {
        StringBuffer aktuelleDaten = new StringBuffer();
        for (String key : laender.keySet()) {
            if (aktuelleDaten.length() != 0) {
                aktuelleDaten.append("/");
            }
            aktuelleDaten.append(key);
        }
        String sDaten = aktuelleDaten.toString();
        String[] datenteile = sDaten.split("/");
        return datenteile;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void loadGruppeninfo(String name)
    {
        String[] teile = getDatenTeile("Gruppen", name);

        gruppenGroesse = Integer.valueOf(teile[0]);
        for (int i = 1; i <= gruppenGroesse; i++) {
            ladeLand(teile[i]);
        }

        if(teile.length <= gruppenGroesse + 1){
            berechnePaarungen();
            teile = getDatenTeile("Gruppen", name);
        }

        loadSpiele(teile);
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private String[] getDatenTeile(String ordner, String datei)
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
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public String getSpielergebnisDaten()
    {
        loadGruppeninfo(gruppenName);
        String aktuelledaten = "";
        for (String key : gruppenphaseSpiele.keySet()) {
            String usedkey = key.replaceAll("\\s","");
            String landEins = usedkey.split(":")[0];
            String landZwei = usedkey.split(":")[1];
            aktuelledaten += (landEins + " " + gruppenphaseSpiele.get(key) + " " + landZwei +"<br>");
        }
        if(aktuelledaten.isEmpty() == true){
            aktuelledaten += "-------------";
        }
        return aktuelledaten;
    }
}
