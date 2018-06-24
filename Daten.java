import java.io.*;
import java.lang.*;
import java.nio.file.*;
import java.net.*;
import java.awt.Desktop;

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
     * @return aktuelledaten
     * @ToDo
     */
    public String ladeVorlage(String art, String dateiname) throws IOException
    {
        String aktuelledaten = "";
        String zeile = "";
        URL link = new URL ("https://jan-patrick.de");
        switch(art)
        {
        case "gruppen":
            link = new URL ("https://raw.githubusercontent.com/jan-patrick/World-Cup-betting/master/Gruppen/"
            + dateiname +".txt");
            break;
        case "turniername":
            link = new URL ("https://raw.githubusercontent.com/jan-patrick/World-Cup-betting/master/Allgemein/"
            + dateiname +".txt");
            break;
        case "laender":
            link = new URL ("https://raw.githubusercontent.com/jan-patrick/World-Cup-betting/master/Laender/"
            + dateiname +".txt");
            break;
        default:
            aktuelledaten = "Fehler";
            return aktuelledaten;
        }
        BufferedReader bu = new BufferedReader(
        new InputStreamReader(link.openStream()));
        while( (zeile = bu.readLine()) != null )
        {
            aktuelledaten += zeile + "/";
        }  
        bu.close();
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
            daten += zeile + "/";
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
    public void speichereDatei(String art, String name, String[] daten) throws IOException
    {        
        String datei = "Error.txt";
        switch(art)
        {
        case "turniername":
            datei = "Allgemein/"+ name +".txt";
            break;
        case "gruppen":
            datei = "Gruppen/" + name + ".txt";
            break;    
        case "laender":
            datei = "Laender/" + name + ".txt";
            break;    
        default:
            break;
        }
        FileWriter fw = new FileWriter(datei);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int x = 0; x < daten.length; x++) {
            bw.write(daten[x]);
            if(x<daten.length){bw.newLine();};
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
        for (int x = 1; x < teile.length; x++) 
        {
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
            try
            {
                java.net.URI uri = new java.net.URI(arg);
                desktop.browse( uri );
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
    public void deleteAlleDaten(String art, String dateiname) throws IOException
    {
        String datei = "Allgemein/turnierName.txt";
        switch(art)
        {
        case "gruppen":
            datei = "Gruppen/" + dateiname +".txt";
            break;
        case "turniername":
            datei = "Allgemein/" + dateiname +".txt";
            break;
        case "laender":
            datei = "Laender/" + dateiname +".txt";
            break;
        default:
            break;
        }
        FileWriter fw = new FileWriter(datei);
        fw.flush();
        fw.close();
    }    
}