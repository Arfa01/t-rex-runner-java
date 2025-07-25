package components;

import utility.Resource;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

// Ground class representing the ground the dino runs on
public class Ground {

    private class GroundImage {
        BufferedImage image;  //image of the ground
        int x;  // X-coordinate of the ground image
    }

    public static int GROUND_Y;   // Y-coordinate of the ground

    private BufferedImage image;  // Image of the ground

    private ArrayList<GroundImage> groundImageSet;  // List of ground images

    // Constructor to initialize the Ground object
    public Ground(int panelHeight) {
        GROUND_Y = (int)(panelHeight - 0.25 * panelHeight);  // Set the ground Y position

        try{
            image = new Resource().getResourceImage("../images/Ground.png"); // Load the ground image
        } catch(Exception e) {e.printStackTrace();}

        groundImageSet = new ArrayList<GroundImage>();

        // set first ground image:
        for(int i=0; i<3; i++) {
            GroundImage obj = new GroundImage();
            obj.image = image;
            obj.x = 0;         // Initial X position of the first ground image
            groundImageSet.add(obj);
        }
    }

    // Method to update the ground position
    public void update() {
        Iterator<GroundImage> looper = groundImageSet.iterator();
        GroundImage first = looper.next();

        first.x -= 10;   // Move first ground image

        int previousX = first.x;
        while(looper.hasNext()) {
            GroundImage next = looper.next();
            next.x = previousX + image.getWidth();  // Set next ground image position
            previousX = next.x;
        }

        // Remove first ground image if out of screen, and add it to the end
        if(first.x < -image.getWidth()) {
            groundImageSet.remove(first);
            first.x = previousX + image.getWidth();
            groundImageSet.add(first);
        }

    }

    // Method to create and draw the ground
    public void create(Graphics g) {
        for(GroundImage img : groundImageSet) {
            g.drawImage(img.image, (int) img.x, GROUND_Y, null);
        }
    }
}