import java.io.*;
import java.lang.*;
import java.nio.file.*;
import java.net.*;

/**
 * Beschreiben Sie hier die Klasse Daten.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.06.11
 */
public class Daten
{
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public Daten()
    {
        // keine Instanzvariablen
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @param dateiname
     * @return daten
     * @ToDo
     */
    public String ladeVorlage(int u, String dateiname) throws IOException
    {
        String aktuelledaten = "";
        String zeile = "";
        switch(u){
        case 1:
            URL group = new URL
            ("https://raw.githubusercontent.com/jan-patrick/World-Cup-betting/master/Gruppen/"+ dateiname +".txt");
            BufferedReader no = new BufferedReader(
            new InputStreamReader(group.openStream()));
            while( (zeile = no.readLine()) != null )
            {
                aktuelledaten += zeile;
                aktuelledaten += "/";
            }    
            no.close();
            break;
        case 2:
            URL turnier = new URL
            ("https://raw.githubusercontent.com/jan-patrick/World-Cup-betting/master/Allgemein/"+ dateiname +".txt");
            BufferedReader nu = new BufferedReader(
            new InputStreamReader(turnier.openStream()));
            while( (zeile = nu.readLine()) != null )
            {
                aktuelledaten += zeile;
                aktuelledaten += "/";
            }    
            nu.close();
            break;
        default:
            aktuelledaten = "Fehler";
            break;
        }
        return aktuelledaten;
    }

    /**
     * Noch zu Beschreiben
     * 
     * @param ordnername, dateiname
     * @return daten
     * @ToDo
     */
    public String ladeDatei(String ordnername, String dateiname) throws IOException
    {
        String datei = ordnername + "/" + dateiname + ".txt";
        FileReader fr = new FileReader(datei);
        BufferedReader br = new BufferedReader(fr);
        String daten = "";
        String zeile = "";
        while( (zeile = br.readLine()) != null )
        {
            daten += zeile;
            daten += "/";
        }
        br.close();
        return daten;
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @param landteile
     * @ToDo
     */
    public void turniernameSpeichern(String turniername) throws IOException
    {        
        String datei = "Allgemein/turnierName.txt";
        FileWriter fw = new FileWriter(datei);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(turniername);
        bw.close();
    }

    /**
     * Noch zu Beschreiben
     * 
     * @param landteile
     * @ToDo
     */
    public void landSpeichern(String[] landteile) throws IOException
    {        
        String datei = "Laender/" + landteile[0] + ".txt";
        FileWriter fw = new FileWriter(datei);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int x = 0; x < landteile.length; x++) {
            bw.write(landteile[x]);
            if(x<landteile.length){bw.newLine();};
        }
        
        bw.close();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @param gruppenName, gruppenteile
     * @ToDo
     */
    public void gruppeSpeichern(String gruppenName, String[] gruppenteile) throws IOException
    {
        String datei = "Gruppen/" + gruppenName + ".txt";
        FileWriter fw = new FileWriter(datei);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int x = 0; x < gruppenteile.length; x++) {
            bw.write(gruppenteile[x]);
            if(x<gruppenteile.length-1){bw.newLine();};
        }
        bw.close();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @param teile
     * @ToDo
     */
    public void gruppeReseten(String[] teile) throws IOException
    {
        String datei = "Gruppen/" + teile[0] + ".txt";
        FileWriter fw = new FileWriter(datei);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int x = 1; x < teile.length; x++) {
            bw.write(teile[x]);
            if(x<teile.length-1){bw.newLine();};
        }

        bw.close();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @param daten
     * @ToDo
     */
    public  void gruppeAnhaengen(String daten) throws IOException
    {
        FileWriter fw = new FileWriter("Gruppen/Gruppen.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(daten);        
        bw.close();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @param ordner, name
     * @ToDo
     */
    public  void deleteDatei(String ordner, String name) throws IOException
    {
        String datei = ordner + "/" + name + ".txt";
        Files.deleteIfExists(Paths.get(datei));
    }
}