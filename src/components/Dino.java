package components;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utility.Resource;

public class Dino {
    private static int dinoBaseY, dinoTopY, dinoStartX, dinoEndX;
    private static int dinoTop, dinoBottom, topPoint;

    private static boolean topPointReached;
    private static int jumpFactor = 20;

    public static final int STAND_STILL = 1,
            RUNNING = 2,
            JUMPING = 3,
            DIE = 4;
    private final int LEFT_FOOT = 1,
            RIGHT_FOOT = 2,
            NO_FOOT = 3;

    private static int state;

    private int foot;

    static BufferedImage image;
    BufferedImage leftFootDino;
    BufferedImage rightFootDino;
    BufferedImage deadDino;

    // Constructor to initialize the Dino object
    public Dino() {
        // Loading images
        image = new Resource().getResourceImage("../images/Dino-stand.png");
        leftFootDino = new Resource().getResourceImage("../images/Dino-left-up.png");
        rightFootDino = new Resource().getResourceImage("../images/Dino-right-up.png");
        deadDino = new Resource().getResourceImage("../images/Dino-big-eyes.png");

        dinoBaseY = Ground.GROUND_Y + 5; // Set the base Y position of the dino
        dinoTopY = Ground.GROUND_Y - image.getHeight() + 5; // Set the top Y position of the dino
        dinoStartX = 100; // Set the starting X position of the dino
        dinoEndX = dinoStartX + image.getWidth(); // Set the ending X position of the dino
        topPoint = dinoTopY - 120; // Set the top point for jumping

        state = STAND_STILL; // Set initial state to standing still
        foot = NO_FOOT; // Set initial foot state to no foot
    }

    // Method to create and draw the dino
    public void create(Graphics g) {
        dinoBottom = dinoTop + image.getHeight(); // Calculate the bottom position of the dino

        switch(state) {
            case STAND_STILL:  // If dino is standing still
                System.out.println("stand");
                g.drawImage(image, dinoStartX, dinoTopY, null); // Draw the standing dino image
                break;

            case RUNNING: // If dino is running
                if(foot == NO_FOOT) {
                    foot = LEFT_FOOT; // Set foot to left foot
                    g.drawImage(leftFootDino, dinoStartX, dinoTopY, null); // Draw the left foot dino image
                } else if(foot == LEFT_FOOT) {
                    foot = RIGHT_FOOT; // Set foot to right foot
                    g.drawImage(rightFootDino, dinoStartX, dinoTopY, null); // Draw the right foot dino image
                } else {
                    foot = LEFT_FOOT; // Set foot to left foot
                    g.drawImage(leftFootDino, dinoStartX, dinoTopY, null); // Draw the left foot dino image
                }
                break;

            case JUMPING:   // If dino is jumping Draw the jumping dino image
                if(dinoTop > topPoint && !topPointReached) {
                    dinoTop -= jumpFactor;
                    g.drawImage(image, dinoStartX, dinoTop, null);
                } else if(dinoTop >= topPoint && !topPointReached) {
                    topPointReached = true;
                    dinoTop += jumpFactor;
                    g.drawImage(image, dinoStartX, dinoTop, null);
                } else if(dinoTop > topPoint && topPointReached) {
                    if(dinoTopY == dinoTop && topPointReached) {
                        state = RUNNING;
                        topPointReached = false;
                    } else {
                        dinoTop += jumpFactor;
                        g.drawImage(image, dinoStartX, dinoTop, null);
                    }
                }
                break;

            case DIE:
                g.drawImage(deadDino, dinoStartX, dinoTop, null); // Draw the dead dino image
                break;
        }
    }

    // Method to make the dino die
    public void die() {
        state = DIE;
    }

    // Method to get the bounding box of the dino
    public static Rectangle getDino() {
        Rectangle dino = new Rectangle();
        dino.x = dinoStartX;

        if(state == JUMPING && !topPointReached) dino.y = dinoTop - jumpFactor;
        else if(state == JUMPING && topPointReached) dino.y = dinoTop + jumpFactor;
        else if(state != JUMPING) dino.y = dinoTop;

        dino.width = image.getWidth();
        dino.height = image.getHeight();

        return dino;
    }

    // Method to start running the dino
    public void startRunning() {
        dinoTop = dinoTopY;
        state = RUNNING;
    }

    // Method to handle dino jumping logic
    public void jump() {
        dinoTop = dinoTopY;
        topPointReached = false;
        state = JUMPING;
    }
}
