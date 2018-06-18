import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import java.net.URI;
import java.awt.Desktop;

/**
 * Beschreiben Sie hier die Klasse Startscreen.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.06.15
 */
public class Startscreen extends JDialog {

    public Startscreen() {
        initUI();
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    private final void initUI() {

        JPanel basic = new JPanel();
        basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
        add(basic); 

        JPanel topPanel = new JPanel(new BorderLayout(0, 0));
        topPanel.setMaximumSize(new Dimension(600, 0));        

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.gray);

        topPanel.add(separator, BorderLayout.SOUTH);

        basic.add(topPanel);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        JTextPane pane = new JTextPane();

        pane.setContentType("text/html");
        String text = "<p><b>Fussballweltmeisterschaft 2018 Übersichtsplan</b></p>" +
            "<p>Semesterprojekt Programmieren 2 von Jan Schneider, HfG, IoT3</p>" +
            "<p>bei Rainer Hönle, HS Aalen im Sommersemester 2018</p>";
        pane.setText(text);
        pane.setEditable(false);
        textPanel.add(pane);
        basic.add(textPanel);
        
        JButton linkButton = new JButton("Zeige Quellcode auf GitHub");
        basic.add(linkButton);
        linkButton.addActionListener((ActionEvent event) -> {
            String[] daten = {"https://github.com/jan-patrick/World-Cup-betting"};
            openLink(daten);
            // Fenster schließen nachdem der Link geöffnet wurde
            System.exit(0);
        });

        JPanel boxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 0));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        setTitle("world-cup-betting");
        setSize(new Dimension(450, 250));
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public static void main() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Startscreen ex = new Startscreen();
                ex.setVisible(true);
            }
        });
    }
    
    /**
     * Noch zu Beschreiben
     * 
     * @ToDo
     */
    public static void openLink(String[] args)
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
            try{
                java.net.URI uri = new java.net.URI(arg);
                desktop.browse( uri );
            }
            catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
    }
}
