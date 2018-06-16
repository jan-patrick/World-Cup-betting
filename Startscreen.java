import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import java.io.*;

/**
 * Beschreiben Sie hier die Klasse Startscreen.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
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
        topPanel.setMaximumSize(new Dimension(450, 0));        

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
            "<p>bei Rainer Hönle</p>";
        pane.setText(text);
        pane.setEditable(false);
        textPanel.add(pane);

        basic.add(textPanel);

        JPanel boxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));

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
}
