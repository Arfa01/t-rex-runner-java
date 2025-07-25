import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import components.Ground;
import components.Dino;
import components.Obstacles;

// This class represents the panel where the game will be displayed
class GamePanel extends JPanel implements KeyListener, Runnable {

    // Constants to store the width and height of the game panel
    public static int WIDTH;
    public static int HEIGHT;
    private Thread animator;     // Thread for animation

    // Boolean flags to control game state
    private boolean running = false;
    private boolean gameOver = false;

    // Game component's objects
    Ground ground;
    Dino dino;
    Obstacles obstacles;


    private int score; // Variable to store the score
    private String gameOverMessage = ""; // Field to store game over message

    // Constructor to initialize the game panel
    public GamePanel() {
        // Set width and height based on the UI
        WIDTH = UserInterface.WIDTH;
        HEIGHT = UserInterface.HEIGHT;

        // Initialize game components
        ground = new Ground(HEIGHT);
        dino = new Dino();
        obstacles = new Obstacles((int)(WIDTH * 1.5));

        score = 0;         // Initialize score

        setSize(WIDTH, HEIGHT);         // Set size and visibility of the panel
        setVisible(true);
    }

    // Method to paint components on the panel
    public void paint(Graphics g) {
        super.paint(g);
        // Set font for score display
        g.setFont(new Font("Courier New", Font.BOLD, 25));
        // Display score at the center of the panel
        g.drawString(Integer.toString(score), getWidth()/2 - 5, 100);
        ground.create(g);
        dino.create(g);
        obstacles.create(g);

        // Display game over message if game is over
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString(gameOverMessage, getWidth()/2 - 150, getHeight()/2);
        }
    }

    // Method to run the game loop
    public void run() {
        running = true;

        // Game loop
        while(running) {
            updateGame(); // Update game state
            repaint();    // Repaint the panel
            try {
                Thread.sleep(50); // Delay for smooth animation
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to update game state
    public void updateGame() {
        score += 1;

        ground.update();
        // dino.update();
        obstacles.update();

        if(obstacles.hasCollided()) {
            dino.die(); // Make the dino die
            repaint(); // Repaint the game panel
            running = false; // Set running flag to false
            gameOver = true; // Set game over flag to true
            gameOverMessage = "Game ended. Press Space to restart";
            System.out.println("collide"); // Print collision message
        }
    }


    // Method to reset the game
    public void reset() {
        score = 0;
        System.out.println("reset");
        obstacles.resume();     // Resume the obstacles
        gameOver = false;      // Set game over flag to false
    }

    // Method to handle keyTyped events
    public void keyTyped(KeyEvent e) {
        // Handle spacebar key event
        if(e.getKeyChar() == ' ') {
            if(gameOver) reset(); // Reset the game if it's over
            if (animator == null || !running) { // Start the game if not already running
                System.out.println("Game starts");
                animator = new Thread(this); // Create a new thread
                animator.start(); // Start the thread
                dino.startRunning(); // Make the dino start running
            } else {
                dino.jump(); // Make the dino jump
            }
        }
    }

    // Methods to handle keyPressed events
    // Not used in this implementation
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }
}