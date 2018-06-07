import java.io.*;

import javax.xml.bind.JAXBContext;

import javax.xml.bind.JAXBException;

import javax.xml.bind.Marshaller;

import java.io.File;

import java.io.FileNotFoundException;

/**
 * Beschreiben Sie hier die Klasse Main.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.28
 */
public class Main
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Interface gui;
    private Nationen teilnehmerlaender;
    private Paarungen begegnungen;
    private Landd landd;

    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main()
    {
        // Instanzvariable initialisieren
        gui = new Interface();
        teilnehmerlaender = new Nationen();
        begegnungen = new Paarungen();
    }
    
    /**
     * In case the window was closed, show it again.
     */
    public void show()
    {
        gui.setVisible(true);
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public void addLand(String name, int tore, int punkte)
    {
        teilnehmerlaender.addLand(name, tore, punkte);
    }
    
    public void addSpiel(int tora, int torb, String landa, String landb, String beschreibung)
    {
        begegnungen.addSpiel(tora, torb, landa, landb, beschreibung);
    }
    
    public void getEveryLandDetails()
    {
        System.out.println("Land-Liste: ");
        for(int i = 0; i < teilnehmerlaender.getLaenderSize(); i++) {
            System.out.println(teilnehmerlaender.getLandDetails(i));
        }
        System.out.println();
    }
    
    public void getEverySpielDetails()
    {
        System.out.println("Spiel-Liste: ");
        for(int i = 0; i < begegnungen.getSpieleSize(); i++) {
            System.out.println(begegnungen.getSpielDetails(i));
        }
        System.out.println();
    }

    public void addLandd(String name, int tore, int punkte)
    {
        landd= new Landd(name, tore, punkte);
    }
    
    public void tearDown() {
        landd = null;
    }
    
    public void listInDatei() {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter("laender.txt"));
            for(int i = 0; i < teilnehmerlaender.getLaenderSize(); i++) {
            printWriter.println(teilnehmerlaender.landtoString(i));
            }    
        } catch (IOException e) {
            e.printStackTrace();
        } 
    } 
}