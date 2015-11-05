import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
/**
 * This World allows the user to choose several transformations,
 * effects, and open files from a drop down list.
 *  
 * Some of the interesting features that were added to this assignment include drop lists, 
 * interesting color effects (sepia, blur, warm, etc), transformatons, and
 * redo and undo button. Furthermore, in the bottom right corner, the 
 * size of the image is also display. If the user tries to open an image,
 * that is too large, they will be warned by the program. Lastly,
 * when the user tries to save an image without typing a name, 
 * the name is automatically set to New Image.
 *  
 * @author Jordan Cohen revised by Bhavya Shah
 * @version April 2015
 */

 
 public class Background extends World
{
    // Constants:
    private final String STARTING_FILE = "rome.jpg";

    // Objects and Variales
    private ImageHolder image; 
    private TextButton undoButton,redoButton;  
    private TextButton openFile;
    private TextButton sizeDisplay;
    private String fileName;
    private ArrayList<String> transformations = new ArrayList<String>();
    private ArrayList<String> colour = new ArrayList<String>();
    private ArrayList<String> file = new ArrayList<String>();
    private ArrayList<String> other = new ArrayList<String>();    
    private DropDownList transformationsList,colourList,fileList,otherList;
    private BufferedImage originalCopy;

    /**
     * Constructor for objects of class Background.
     * 
     */
    public Background()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1000, 600, 1); 

        // Initialize buttons and the image
        image = new ImageHolder(STARTING_FILE);
        originalCopy = deepCopy(image.getBufferedImage());
        undoButton = new TextButton(" Undo ");   
        redoButton = new TextButton(" Redo ");     
        openFile = new TextButton(" [ Open File: " + STARTING_FILE + " ] ",20,Color.RED);
        sizeDisplay = new TextButton(image.getBufferedImage());

        // Add objects to the screen
        addObject (image, 500, 330);       
        addObject (undoButton, 460, 50);
        addObject (redoButton, 540, 50);
        addObject (openFile, 500, 24);
        addObject (sizeDisplay, 960, 590);

        // creates a drop-down list for transformations
        transformations.add("Transformations");        
        transformations.add("Flip Horizontally");
        transformations.add("Flip Vertically");
        transformations.add("Rotate Right 90");
        transformations.add("Rotate Left 90");
        transformations.add("Rotate 180");
        transformationsList = new DropDownList(transformations, 0);
        addObject(transformationsList, 250, 24);

        // creates a drop-down list for colour effects
        colour.add("Colour Effects");
        colour.add("Grey Scale");  
        colour.add("Warmer");
        colour.add("Cooler");
        colour.add("Negative");
        colour.add("Brighten");
        colour.add("Darken");
        colour.add("Sepia");
        colourList = new DropDownList(colour, 0);
        addObject(colourList,720, 24);

        // creates a drop-down list for other effectd
        other.add("Other Effects");
        other.add("Mirror Horizontally");  
        other.add("Mirror Vertically");
        other.add("Blur");
        other.add("Sharpen");               
        otherList = new DropDownList(other, 0);
        addObject(otherList,910, 24);

        // creates a drop-down file menu 
        file.add("File");
        file.add("Open");  
        file.add("Save as JPG");
        file.add("Save as PNG");
        file.add("Reset");
        file.add("Undo");
        file.add("Redo");
        fileList = new DropDownList(file, 0);
        addObject(fileList,75, 24);
    }

    /**
     * Act() method just checks for mouse input... Not going to do anything else here
     */
    public void act ()
    {
        checkMouse();
    }
    
    /**
     * Returns a BufferedImage from the GreenfootImage
     * 
     * @return BufferedImage - the BufferedImage of the image
     */

    public BufferedImage getImage ()
    {
        return image.getBufferedImage();
    }

    /**
     * Check for user clicking on a button
     */
    private void checkMouse ()
    {
        String trans = transformationsList.getSelected();
        String colour = colourList.getSelected();
        String file = fileList.getSelected();
        String other = otherList.getSelected();

        if ("Flip Horizontally".equals(trans))
        {
            Processor.flipHorizontal(image.getBufferedImage());
        }
        else if ("Grey Scale".equals(colour))
        {
            Processor.greyScale(image.getBufferedImage());
        }
        else if ("Flip Vertically".equals(trans))
        {
            Processor.flipVertical(image.getBufferedImage());            
        }
        else if ("Rotate Right 90".equals(trans))
        {            
            createGreenfootImageFromBI(Processor.rotateCW(image.getBufferedImage()));            
        }
        else if ("Rotate Left 90".equals(trans))
        {
            createGreenfootImageFromBI(Processor.rotateCCW(image.getBufferedImage()));            
        }
        else if ("Mirror Vertically".equals(other))
        {
            createGreenfootImageFromBI(Processor.mirrorVertically(image.getBufferedImage()));            
        }
        else if ("Mirror Horizontally".equals(other))
        {
            createGreenfootImageFromBI(Processor.mirrorHorizontally(image.getBufferedImage()));            
        }
        else if ("Blur".equals(other))
        {
            Processor.blur(image.getBufferedImage());            
        }
        else if ("Sharpen".equals(other))
        {
            Processor.sharpen(image.getBufferedImage());            
        }
        else if ("Warmer".equals(colour))
        {
            Processor.warm(image.getBufferedImage());
        }
        else if ("Cooler".equals(colour))
        {
            Processor.cool(image.getBufferedImage());
        }
        else if ("Sepia".equals(colour))
        {
            Processor.sepia(image.getBufferedImage());
        }
        else if ("Negative".equals(colour))
        {
            Processor.negative(image.getBufferedImage());
        }
        else if ("Brighten".equals(colour))
        {
            Processor.brighten(image.getBufferedImage());
        }
        else if ("Darken".equals(colour))
        {
            Processor.darken(image.getBufferedImage());
        }
        else if ("Rotate 180".equals(trans))
        {
            createGreenfootImageFromBI(Processor.rotateCW(image.getBufferedImage())); 
            createGreenfootImageFromBI(Processor.rotateCW(image.getBufferedImage())); 
        }        
        else if (Greenfoot.mouseClicked(undoButton))
        {
            createGreenfootImageFromBI( Processor.undo(image.getBufferedImage()));
        }
         else if (Greenfoot.mouseClicked(redoButton))
        {
             createGreenfootImageFromBI(Processor.redo(image.getBufferedImage())); 
        }
        else if (Greenfoot.mouseClicked(openFile))
        {
            openFile();
        }
        else if ("Open".equals(file))
        {
            openFile();
        }
        else if ("Redo".equals(file))
        {
            createGreenfootImageFromBI(Processor.redo(image.getBufferedImage())); 
        }
         else if ("Undo".equals(file))
        {
            createGreenfootImageFromBI(Processor.undo(image.getBufferedImage())); 
        }
         else if ("Reset".equals(file))
        {
            createGreenfootImageFromBI(Processor.reset(image.getBufferedImage(),originalCopy));
        }
        else if ("Save as PNG".equals(file))
        {
            saveAsPNG();
        }
        else if ("Save as JPG".equals(file))
        {
            saveAsJPG();
        }
        sizeDisplay.updateValue(image.getBufferedImage()); //updates the size of the image

    }

    /**
     * Allows the user to open a new image file.
     */
    private void openFile ()
    {
        // Use a JOptionPane to get file name from user
        String fileName = JOptionPane.showInputDialog("Please input a valid file name(with extension).");

        if (fileName == null)
        {
            JOptionPane.showMessageDialog(null, "No name entered!");
        }       
        else if (image.openFile (fileName))   // If the file opening operation is successful, update the text in the open file button
        {
            originalCopy = deepCopy(getImage());
            String display = " Open File: " + fileName + " ";
            openFile.update (display);
        }
        else  // if the file opening operation is unsuccessful
        {
            JOptionPane.showMessageDialog(null, "File could not be opened!");
        }

    }

    /**
     * Takes in a BufferedImage and coverts it to a GreenfootImage
     * 
     * @param newBi The BufferedImage to convert.
     */
    private void createGreenfootImageFromBI (BufferedImage newBi)
    {
        GreenfootImage returnImage = new GreenfootImage (newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D)backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);

        image.setImage(returnImage);
    }

    /**
     * Saves the current image displayed as a PNG file.
     */
    private void saveAsPNG ()
    {
        // Gets the file name from the user
        String fileName = JOptionPane.showInputDialog("Input file name (no extension)"); 
        if (fileName!= null)
        {
            if (fileName.equals (""))  // if nothing is entered 
            {
                fileName += ("New Image"); // sets file name to "New Image"
                JOptionPane.showMessageDialog (null, "No file name entered, file name set to: New Image");
            }
            fileName += ".png";
            File f = new File (fileName);  
            try 
            {
                ImageIO.write(image.getImage().getAwtImage(), "png", f); 
                JOptionPane.showMessageDialog (null, "Successfully saved as " + fileName + "!");
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog (null, "Save unsuccessful! Try again!");
            }
        }

    }

    /**
     * Saves the current image displayed as a JPG file.
     */
    private void saveAsJPG ()
    {
        // Gets the file name from the user
        String fileName = JOptionPane.showInputDialog("Input file name (no extension)");

        if (fileName!= null)
        {
            if (fileName.equals (""))  // if nothing is entered 
            {
                fileName += ("New Image");  // sets file name to "New Image"
                JOptionPane.showMessageDialog (null, "No file name entered, file name set to: New Image");
            }
            fileName += ".jpg";
            File f = new File (fileName);  

            BufferedImage original = image.getBufferedImage();
            try 
            {
                BufferedImage newImage = new BufferedImage (original.getWidth(),original.getHeight(),original.TYPE_INT_RGB);
                newImage.createGraphics().drawImage(original,0,0,Color.WHITE,null);
                ImageIO.write(newImage, "jpg", f); 
                JOptionPane.showMessageDialog (null, "Successfully saved as " + fileName + "!"); // if the operation was succcessful
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog (null, "Save unsuccessful! Try again!"); // if the operation was unsuccesful
            }
        }

    }
    
    /**
     * Creats a copy of the BufferedImage so that it would not 
     * have the same reference.
     * 
     * @param bi - the BufferedImage to be copied
     * @return BufferedImage - 
     */
    
        public static BufferedImage deepCopy(BufferedImage bi) 
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultip = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultip, null);
    }
}

