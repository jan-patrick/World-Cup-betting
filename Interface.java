import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;      // Abstract Windowing Toolkit
import javax.swing.*;   // Swing Komponenten fuer Oberflaeche
import java.awt.event.*;// Ereignisse wie Klick auf Button

/**
 * Beschreiben Sie hier die Klasse Interface.
 * 
 * @author Jan Schneider, HfG, IoT3
 * @version 2018.06.04
 */
public class Interface
{
    private JFrame frame;
    private JTextField display;
    private JLabel status;

    /**
     * Konstruktor für Objekte der Klasse Interface
     */
    public Interface()
    {
        makeFrame();
        frame.setVisible(true);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png"));
    }

    /**
     * Set the visibility of the interface.
     * @param visible true if the interface is to be made visible, false otherwise.
     */
    public void setVisible(boolean visible)
    {
        frame.setVisible(visible);
    }
    
    /**
     * Make the frame for the user interface.
     */
    private void makeFrame()
    {
        frame = new JFrame("world-cup-betting");
        
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setLayout(new BorderLayout(8, 8));
        contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));

        display = new JTextField(10);
        contentPane.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4));
        addNumberButton(buttonPanel, 7);
        addButton(buttonPanel, "C", () -> redisplay());
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        status = new JLabel("Jan Schneider, HfG, IoT3");
        contentPane.add(status, BorderLayout.SOUTH);
        
        frame.pack();
    }
    
    /**
     * Add a button to the button panel.
     * @param panel The panel to receive the button.
     * @param buttonText The text for the button.
     * @param action Action to be taken by the button.
     */
    private void addButton(Container panel, String buttonText, ButtonAction action)
    {
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> { action.act(); redisplay(); });
        panel.add(button);
    }

    /**
     * Add a number button to the button panel.
     * @param panel The panel to receive the button.
     * @param digit The single digit on the button.
     */
    private void addNumberButton(Container panel, int digit)
    {
        addButton(panel, "" + digit, () -> redisplay());
    }
    
    /**
     * Update the interface display to show the current value of the 
     * calculator.
     */
    private void redisplay()
    {
        display.setText(" test ");
    }
    
    /**
     * Functional interface for button actions.
     */
    @FunctionalInterface
    private interface ButtonAction
    {
        /**
         * Act on a button press.
         */
        public void act();
    }
}
