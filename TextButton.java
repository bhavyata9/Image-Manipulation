import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 * A Generic Button to display text that is clickable. Owned by a World, which controls click
 * capturing.
 * 
 * @author Jordan Cohen revised by Bhavya Shah
 * @version April 2015
 */
public class TextButton extends Actor
{
    // Declare private variables
    private GreenfootImage myImage;
    private String buttonText;
    private int textSize;

    /**
     * Construct a TextButton with a given String at the default size
     * 
     * @param text - String value to display
     * 
     */
    public TextButton (String text)
    {       
        this(text, 20);        
    }

    /**
     * Construct a TextButton that simply displays the size of a BufferedImage
     * 
     * @param bi - the BufferedImage to display the size of 
     * 
     */

    public TextButton (BufferedImage bi)
    {       
        updateValue(bi);
    }

    /**
     * Construct a TextButton with a given String and a specified size
     * 
     * @param text -  String value to display
     * @param textSize - size of text, as an integer
     * 
     */
    public TextButton (String text, int textSize)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, textSize, Color.BLACK, Color.WHITE);
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (Color.WHITE);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }

    /**
     * Construct a TextButton with a given String and a specified size
     * 
     * @param text - String value to display
     * @param textSize - size of text, as an integer
     * @param color - the desired color of the font
     * 
     */
    public TextButton (String text,int textSize, Color color)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, textSize, color, Color.WHITE);
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (Color.WHITE);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }

    /**
     * Change the text displayed on this Button 
     * @param text - String to display 
     */
    
    public void update (String text)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, 20, Color.RED, Color.WHITE);
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (Color.WHITE);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }
    
    /**
     * Updates the size of the BufferedImage
     * @param BufferedImage - the BufferedImage to change the size of 
     */
    
    public void updateValue (BufferedImage bi)
    {
        String  text = bi.getWidth()+ " x "+ bi.getHeight();
        GreenfootImage tempTextImage = new GreenfootImage (text, 20, Color.RED, null);
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);      
        myImage.drawImage (tempTextImage, 4, 4);

        setImage(myImage);
    }

}
