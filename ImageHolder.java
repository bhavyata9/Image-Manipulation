import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

/**
 * Simple class that serves to be an Actor to display the image.
 * 
 * (Revised 11/14 to avoid crashing if user cancels import operation).
 * 
 * @author Jordan Cohen revised by Bhavya Shah
 * @version April 2015
 */
public class ImageHolder extends Actor
{
    private GreenfootImage imageToDisplay; 

    /**
     * Construct an ImageHolder with a file name. If there is an error, 
     * show a blank GreenfootImage.
     * 
     * @param fileName  Name of image file to be displayed.
     */
    public ImageHolder (String fileName)
    {
        openFile (fileName);
    }

    /**
     * Attempt to open a file and assign it as this Actor's image
     * 
     * @param fileName  Name of the image file to open (must be in this directory)
     * @return boolean  True if operation successful, otherwise false
     */
    public boolean openFile (String fileName)
    {
        boolean pass = true;
        try {
            if (fileName != null)
            {
                imageToDisplay = new GreenfootImage (fileName);
              
                int height = imageToDisplay.getHeight();
                int width = imageToDisplay.getWidth();

                if (width > 970 || height > 510)   // if exceeds the size of the maximum image that can fit on the world
                {
                    pass = false;
                }

                while(!pass)
                {
                    fileName = JOptionPane.showInputDialog("Maximum Size (970x510) exceeded.Try Again!");
                    imageToDisplay = new GreenfootImage(fileName);
                    height = imageToDisplay.getHeight();
                     width = imageToDisplay.getWidth();
                    if (width > 970 || height > 510)
                    {
                        pass = false;
                    }
                }

                setImage(imageToDisplay);

            }
            else
                return false;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Allows access to my awtImage - the backing data underneath the GreenfootImage class.
     * 
     * @return BufferedImage returns the backing image for this Actor as an AwtImage
     */
    public BufferedImage getBufferedImage ()
    {
        return this.getImage().getAwtImage();
    }

}
