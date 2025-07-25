package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import utility.Resource;

// This class represents the obstacles in the game
public class Obstacles {
    // Inner class representing individual obstacle
    private class Obstacle {
        BufferedImage image; // Image of the obstacle
        int x; // X-coordinate of the obstacle
        int y; // Y-coordinate of the obstacle

        // Method to get the bounding rectangle of the obstacle
        Rectangle getObstacle() {
            Rectangle obstacle = new Rectangle();
            obstacle.x = x;
            obstacle.y = y;
            obstacle.width = image.getWidth();
            obstacle.height = image.getHeight();

            return obstacle;
        }
    }

    private int firstX; // Initial X-coordinate of the first obstacle
    private int obstacleInterval; // Gap between obstacles
    private int movementSpeed; // Speed at which obstacles move

    private ArrayList<BufferedImage> imageList; // List of obstacle images
    private ArrayList<Obstacle> obList; // List of obstacles

    private Obstacle blockedAt; // Obstacle at which the dino collides


    // Constructor to initialize an Obstacle object
    public Obstacles(int firstPos) {
        obList = new ArrayList<Obstacle>();
        imageList = new ArrayList<BufferedImage>();

        firstX = firstPos; // Set initial X-coordinate of first obstacle
        obstacleInterval = 200; // Set obstacle interval
        movementSpeed = 11; // Set movement speed

        // Load obstacle images
        imageList.add(new Resource().getResourceImage("../images/Cactus-1.png"));
        imageList.add(new Resource().getResourceImage("../images/Cactus-2.png"));
        imageList.add(new Resource().getResourceImage("../images/Cactus-2.png"));
        imageList.add(new Resource().getResourceImage("../images/Cactus-1.png"));
        imageList.add(new Resource().getResourceImage("../images/Cactus-5.png"));

        int x = firstX;

        for(BufferedImage bi : imageList) {

            Obstacle ob = new Obstacle();

            ob.image = bi;
            ob.x = x;
            ob.y = components.Ground.GROUND_Y - bi.getHeight() + 5;
            x += obstacleInterval;

            obList.add(ob);
        }
    }

    // Method to update obstacles
    public void update() {
        Iterator<Obstacle> looper = obList.iterator();

        // Update position of first obstacle
        Obstacle firstOb = looper.next();
        firstOb.x -= movementSpeed;

        // Update positions of remaining obstacles
        while(looper.hasNext()) {
            Obstacle ob = looper.next();
            ob.x -= movementSpeed;
        }

        // Move first obstacle to the end if it goes out of screen
        Obstacle lastOb = obList.get(obList.size() - 1);

        if(firstOb.x < -firstOb.image.getWidth()) {
            obList.remove(firstOb);
            firstOb.x = obList.get(obList.size() - 1).x + obstacleInterval;
            obList.add(firstOb);
        }
    }

    // Method to create and draw the obstacle
    public void create(Graphics g) {
        for(Obstacle ob : obList) {
            g.setColor(Color.black);
            g.drawImage(ob.image, ob.x, ob.y, null);  // Draw the obstacle image
        }
    }

    // Method to check collision with obstacles
    public boolean hasCollided() {
        for(Obstacle ob : obList) {
            if(components.Dino.getDino().intersects(ob.getObstacle())) {
                System.out.println("Dino = " + components.Dino.getDino() + "\nObstacle = " + ob.getObstacle() + "\n\n");
                blockedAt = ob; // Set blocked obstacle
                return true; // Collision detected
            }
        }
        return false;  // No collision detected
    }

    // Method to resume obstacles after game reset
    public void resume() {
        // this.obList = null;
        int x = firstX/2;
        obList = new ArrayList<Obstacle>();

        // Recreate obstacles
        for(BufferedImage bi : imageList) {

            Obstacle ob = new Obstacle();

            ob.image = bi;
            ob.x = x;
            ob.y = Ground.GROUND_Y - bi.getHeight() + 5;
            x += obstacleInterval;

            obList.add(ob);
        }
    }

}