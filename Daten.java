import java.io.*;
import java.lang.*;

/**
 * Beschreiben Sie hier die Klasse Nationen.
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
            daten += ",";
        }

        br.close();

        return daten;
    }

    /**
     * Noch zu Beschreiben
     * 
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
     * @ToDo
     */
    public void gruppeSpeichern(String gruppendateiName, String gruppendaten) throws IOException
    {              
        String datei = "Gruppen/" + gruppendateiName + ".txt";
        FileWriter fw = new FileWriter(datei);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.newLine();
        bw.write(gruppendaten);
        
        bw.close();
    }
}