import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

// This class represents the user interface of the game
class UserInterface {
    // Main window frame
    JFrame mainWindow = new JFrame("T-Rex Run");

    // Constants for window width and height
    public static int WIDTH = 800;
    public static int HEIGHT = 500;

    // Method to create and show the GUI
    public void createAndShowGUI() {
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          // Set default close operation for the main window

        Container container = mainWindow.getContentPane();           // Get the content pane of the main window

        GamePanel gamePanel = new GamePanel();      // Create the game panel
        gamePanel.addKeyListener(gamePanel);        // Add key listener to the game panel
        gamePanel.setFocusable(true);               // Set focusable to true to enable key events on the game panel

        container.setLayout(new BorderLayout());           // Set layout of the container to BorderLayout

        container.add(gamePanel, BorderLayout.CENTER);     // Add the game panel to the center of the container

        // Set size, resizable, and visibility of the main window
        mainWindow.setSize(WIDTH, HEIGHT);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
    }

    public static void main(String[] args) {
        // Execute the createAndShowGUI method on the event dispatch thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {  // Use SwingUtilities to ensure the GUI is created on the Event Dispatch Thread
            public void run() {
                new UserInterface().createAndShowGUI();  // Create and show the GUI
            }
        });
    }
}