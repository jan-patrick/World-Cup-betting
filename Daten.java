import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStreamReader;
import java.io.File;
import java.net.URL;
import java.awt.Desktop;

/**
 * Die Klasse Daten übernimmt das Auslesen und Speichern der Daten in .txt Dateien und das Öffnen von Links.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.06.30
 */
public class Daten
{
    /**
     * Konstruktor der Klasse Daten
     */
    public Daten()
    {
        // keine Instanzvariablen
    }
    
    /**
     * Diese Methode lädt die aktuellen Daten des Vorlagenturniers (WM 2018) aus dem Github-Repository des Programms und speichert diese lokal.
     * Dies dient einerseits als Backup, andererseits als einfache Methode um immer den aktuellsten Plan samt Ergebnissen zu sehen, 
     * ohne diese als Nutzer selbst eingeben zu müssen (sofern eine Internetverbindung besteht und die Ergebnisse online sind).
     * 
     * @param art Welche Art Daten wird abgefragt (Allgemein, Gruppen, Länder)
     * @param dateiname Name der auszulesenden Datei
     * @return aktuelledaten Daten, die zur angefragten Datei gehören
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
     * Liest aus einer lokal gespeicherten Textdatei (in einem Ordner im Projektverzeichnis) die Daten einer Datei aus.
     * Die Methode funktioniert praktisch identisch wie ladeVorlage().
     * 
     * @param ordnername Der Ordner der gesuchten Datei (Allgemein, Gruppen, Länder)
     * @param dateiname Der Dateiname der Textdatei
     * 
     * @return daten aller Zeilen getrennt durch "/" in einem String
     */
    public String ladeDatei(String ordnername, String dateiname) throws IOException
    {
        String datei = ordnername + "/" + dateiname + ".txt";
        FileReader fr = new FileReader(datei);
        BufferedReader br = new BufferedReader(fr);
        String aktuelledaten = "";
        String zeile = "";
        while( (zeile = br.readLine()) != null )
        {
            aktuelledaten += zeile + "/";
        }
        br.close();
        return aktuelledaten;
    }
    
    /**
     * Im Prinzip gegenläufig zu den beiden auslesenden Funktionen speichert diese Methode die Daten in die gewünschte Datei.
     * 
     * @param art Art der zu speichernden Daten (turniername, Gruppen, Länder)
     * @param name Dateiname der zu speichernden Textdatei
     * @param daten Daten, die gespeichert werden sollen
     * @throws IOException
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
     * Überschreibt eine bereits vorhandene Gruppen Textdatei.
     * 
     * @param teile Informationen, die gespeichert werden sollen
     * @throws IOException
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
     * Fügt der lokalen Text Datei "Gruppen" eine Gruppe hinzu.
     * Die vorhandenen Einträge bleiben dabei bestehen.
     * 
     * @param daten Information die der Datei angehängt werden soll
     * @throws IOException
     */
    public void gruppeAnhaengen(String aktuelledaten) throws IOException
    {
        FileWriter fw = new FileWriter("Gruppen/Gruppen.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(aktuelledaten);        
        bw.close();
    }
    
    /**
     * Löscht die Text Datei mit dem übergebenen Namen in dem übergebenen Verzeichnis.
     * 
     * @param ordner Der Ordner, entweder "Gruppen", "Laender" oder "Allgemein"
     * @param dateiname Der Dateiname der Textdatei
     * @throws IOException
     */
    public void deleteDatei(String ordner, String name) throws IOException
    {
        String datei = ordner + "/" + name + ".txt";
        Files.deleteIfExists(Paths.get(datei));
    }  
        
    /**
     * Öffnet die übergebenen Links im Standardbrowser.
     * 
     * @param args Die zu öffnenden Links (Webadressen)
     * @see Die zu öffnenden Websiten im Standardbrowser des Systems
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
            System.err.println( "Usage: OpenURI [URI [URI ... ]]" );
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
     * Löscht alle Text Dateien, die in den drei Speicherorten liegen (Allgemein, Gruppen, Länder)
     * 
     * @throws IOException
     */
    public void deleteAlleDaten() throws IOException
    {
        File foldergruppe = new File("Gruppen");
        File[] listOfFilesGruppe = foldergruppe.listFiles();
        for (int i = 0; i < listOfFilesGruppe.length; i++) {
            if (listOfFilesGruppe[i].isFile()) {
                Files.deleteIfExists(Paths.get("Gruppen/"+listOfFilesGruppe[i].getName()));
            }
        }
        File folderallgemein = new File("Allgemein");
        File[] listOfFilesAllgemein = folderallgemein.listFiles();
        for (int i = 0; i < listOfFilesAllgemein.length; i++) {
            if (listOfFilesAllgemein[i].isFile()) {
                Files.deleteIfExists(Paths.get("Allgemein/"+listOfFilesAllgemein[i].getName()));
            }
        } 
        File folderlaender = new File("Laender");
        File[] listOfFilesLaender = folderlaender.listFiles();
        for (int i = 0; i < listOfFilesLaender.length; i++) {
            if (listOfFilesLaender[i].isFile()) {
                Files.deleteIfExists(Paths.get("Laender/"+listOfFilesLaender[i].getName()));
            }
        }
    }    
}