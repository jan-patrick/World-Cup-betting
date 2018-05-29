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
    private Nationen teilnehmerlaender;
    private Paarungen begegnungen;
    private Landd landd;

    /**
     * Konstruktor für Objekte der Klasse Main
     */
    public Main()
    {
        // Instanzvariable initialisieren
        teilnehmerlaender = new Nationen();
        begegnungen = new Paarungen();
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

    public void addLandd()
    {
        landd= new Landd("Testland", 2, 10);
    }
    
    public void tearDown() {
        landd = null;
    }

    public void testObjectToXml() throws JAXBException, FileNotFoundException {

        JAXBContext jaxbContext = JAXBContext.newInstance(Landd.class);

        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(landd, new File("laender.xml"));

        marshaller.marshal(landd, System.out);

    }

}