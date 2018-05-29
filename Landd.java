import javax.xml.bind.annotation.*;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.05.29
 */
@XmlRootElement(name = "Landd")

public class Landd {
    private String name;
    private int tore;
    private int punkte;

    @XmlAttribute(name = "name")
    private String landname;
    @XmlElement(name = "tore")
    private int landtore;
    @XmlElement(name = "punkte")
    private int landpunkte;

    public Landd(){}

    public Landd(String landname, int landtore, int landpunkte) {
        this.landname = landname;
        this.landtore = landtore;
        this.landpunkte = landpunkte;
    }

    @Override

    public String toString() {
        return "Product{" +

                "\n Name='" + landname + '\'' +

                ",\n Tore='" + landtore + '\'' +

                ",\n Punkte='" + landpunkte + '\'' +

                '}';
    }
}