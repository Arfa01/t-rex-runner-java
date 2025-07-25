package utility;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// This utility class helps in loading images from resources
public class Resource {

    // Method to get a resource image by its path
    public BufferedImage getResourceImage(String path) {
        BufferedImage img = null;
        try {
            // Read the image from the specified path using ImageIO
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;  // Return the loaded image or null if loading failed
    }

}