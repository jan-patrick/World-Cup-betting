import java.io.*;
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
        // benötigt keine Initialisierung
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
    public void landSpeichern(String landdaten) throws IOException
    {
        String[] landdatenteile = landdaten.split(",");
        
        String datei = "Land," + landdatenteile[0] + ".txt";
        FileWriter fw = new FileWriter(datei);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int x = 0; x < landdatenteile.length; x++) {
            bw.write(landdatenteile[x]);
            if(x<landdatenteile.length){bw.newLine();};
        }
        
        bw.close();
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public void gruppeSpeichern(String gruppendaten) throws IOException
    {       
        String[] teile = gruppendaten.split(",");
        
        String datei = "Gruppe" + teile[0] + ".txt";
        FileWriter fw = new FileWriter(datei);
        BufferedWriter bw = new BufferedWriter(fw);

        

        for (int x = 0; x < teile.length; x++) {
            bw.write(teile[x]);
            if(x<teile.length){bw.newLine();};
        }
        
        bw.close();
    }
}