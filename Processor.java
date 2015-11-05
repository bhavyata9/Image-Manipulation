/**
 * Processor - the class that processes images.
 * <p>
 * This class manipulates Java BufferedImages, which are effectively 2d arrays
 * of pixels. 
 * <p>
 * 
 * Contains methods that allow images to manipulated (flipped, rotated,
 * and add other effects)
 * 
 * Code for blur borrowed method from http://www.jhlabs.com/ip/blurring.html
 * Code for sharpen method borrowed from http://www.java2s.com/Code/Java/2D-Graphics-GUI/ImageEffectSharpenblur.htm
 * Some of the code for sepia method borrowed from http://stackoverflow.com/questions/5132015/how-to-convert-image-to-sepia-in-java
 * 
 * @author  Bhavya Shah
 * @version April 2015
 */

import java.awt.image.BufferedImage;
import greenfoot.*;
import java.util.ArrayList;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.lang.ArrayIndexOutOfBoundsException;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Processor  
{
    private static ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
    private static ArrayList<BufferedImage> redoList = new ArrayList<BufferedImage>(); 
    private static boolean undo = false;
    private static int undoCount = 0;

    /**
     * Flips the image horizontally
     * @param bi - the BufferedImage to be flipped horizontally   
     */
    public static void flipHorizontal (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);      

        for (int i = 0; i < xSize; i++)
        {
            for (int j = 0; j< ySize; j++)
            {
                int rgb = bi.getRGB(i,j);
                newBi.setRGB(xSize-i-1,j,rgb); //flip the pixels
            }
        }
        for (int i = 0; i < xSize; i++)
        {
            for (int j = 0; j< ySize; j++)
            {
                bi.setRGB(i,j,newBi.getRGB(i,j)); // set each pixel of the original image 
            }
        }
        redoList.add(deepCopy(bi)); // add the new image to the redo list
    }

    /**
     * Flips the image vertically
     * @param bi - the BufferedImage to be flipped vertically   
     */
    public static void flipVertical (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);    

        for (int i = 0; i < xSize; i++)
        {
            for (int j = 0; j< ySize; j++)
            {
                int rgb = bi.getRGB(i,j);
                newBi.setRGB(i,ySize-j-1, rgb); //flip the pixels
            }
        }
        for (int i = 0; i < xSize; i++)
        {
            for (int j = 0; j< ySize; j++)
            {
                bi.setRGB(i,j,newBi.getRGB(i,j)); // set each pixel of the original image 
            }
        }
        redoList.add(deepCopy(bi)); // add the new image to the redo list
    }

    /**
     * Rotates the image clockwise by 90 degrees
     * @param bi - the BufferedImage to be rotated clockwise
     * @return BufferedImage - the new image rotated
     */

    public static BufferedImage rotateCW (BufferedImage bi)
    {
        imageList.add(deepCopy(bi));  // add the image to the undo list
        int xSize = bi.getWidth();  
        int ySize = bi.getHeight();

        BufferedImage newBi = new BufferedImage (ySize, xSize, 1); // switch the height and width of the new image
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int rgb = bi.getRGB(x,y);                
                newBi.setRGB(ySize-y-1,x,rgb); // rotates the image
            }
        }

        redoList.add(deepCopy(newBi));  // add the image to the redo list
        return newBi; // return the new rotated image

    }

    /**
     * Rotates the image counterclockwise by 90 degrees
     * @param bi - the BufferedImage to be rotated counterclockwise
     * @return BufferedImage - the new image rotated
     */
    public static BufferedImage rotateCCW (BufferedImage bi)
    {
        imageList.add(deepCopy(bi));  // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        BufferedImage newBi = new BufferedImage (ySize, xSize, 1); // switch the height and width of the new image
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int rgb = bi.getRGB(x,y);                
                newBi.setRGB(y,xSize-x-1,rgb); // rotates the image
            }
        }
        redoList.add(deepCopy(newBi));  // add the image to the redo list
        return newBi; // return the new rotated image
    }

    /**
     * Mirrors the image horizontally 
     * @param bi - the BufferedImage to be mirrored
     * @return BufferedImage - the new image with the effect
     */

    public static BufferedImage mirrorHorizontally (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        BufferedImage newBi = new BufferedImage (xSize*2,ySize, 1); // double the width 
        for (int y = 0; y < ySize; y++)
        {
            for (int left = 0, right = xSize*2-1;left < xSize; left++,right-- )
            {
                int rgb = bi.getRGB(left,y);                
                newBi.setRGB(left,y,rgb);
                newBi.setRGB(right,y,rgb);
            }
        }

        redoList.add(deepCopy(newBi));  // add the image to the redo list
        return newBi; // return the new mirrored image

    }

    /**
     * Mirrors the image vertically
     * @param bi - the BufferedImage to be mirrored
     * @return BufferedImage - the new image with the effect
     */

    public static BufferedImage mirrorVertically (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        BufferedImage newBi = new BufferedImage (xSize,ySize*2, 1); // double the height of the image
        for (int x = 0; x < xSize; x++)
        {
            for (int top = ySize-1, bottom = ySize;bottom < ySize*2; top--,bottom++)
            {
                int rgb = bi.getRGB(x,top);                
                newBi.setRGB(x,top,rgb);
                newBi.setRGB(x,bottom,rgb);
            }
        }
        redoList.add(deepCopy(newBi)); // add the image to the redo list
        return newBi; // return the new mirrored image

    }

    /**
     * Returns the original image without any effects added
     * @param bi - the current image
     * @param originalCopy - the original image
     * @return BufferedImage - the original image
     */
    public static BufferedImage reset (BufferedImage bi, BufferedImage originalCopy)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        return originalCopy;        
    }

    /**
     * Warms the image 
     * @param bi - the BufferedImage to be warmed     
     */

    public static void warm (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic warmer by adding red and green 
                if (red <= 230)  
                {
                    red += 5;
                }
                if (green <= 230)
                {
                    green += 5;
                }               

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }

        }
        redoList.add(deepCopy(bi)); // add the image to the redo list
    }

    /**
     * Cools the Image
     * @param bi - the BufferedImage to be cooled   
     */

    public static void cool (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic cooler by adding blue
                if (blue< 229 && blue <247)
                {
                    blue += 8;
                }

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }

        }
        redoList.add(deepCopy(bi)); // add the image to the redo list
    }

    /**
     * Greyscales the image
     * @param bi - the BufferedImage to be greyscaled  
     */

    public static void greyScale (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int weightedAverage = (int)(green*.7+red*.2+blue*.1); // gets the weighted average of the three colours

                int newColour = packagePixel (weightedAverage, weightedAverage, weightedAverage, alpha); // sets all colours as equal
                bi.setRGB (x, y, newColour);
            }

        }
        redoList.add(deepCopy(bi)); // add the image to the redo list
    }

    /**
     * Some code borrowed from: http://stackoverflow.com/questions/5132015/how-to-convert-image-to-sepia-in-java
     * Gives the image a brownish tone 
     * @param bi - the BufferedImage to change  
     * 
     */

    public static void sepia (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int sepiaDepth = 20; 
                int sepiaIntensity = 30; 

                int weightedAverage = (int)(green*.7+red*.2+blue*.1);  // gets the weighted average of the three colours
                red = green = blue = weightedAverage; // sets all colours to be the same
                red = red + (sepiaDepth * 2);
                green = green + sepiaDepth;
                blue-= sepiaIntensity;

                if (red>200)
                {
                    red=200;
                }
                if (green>200)
                {
                    green=200;
                }
                if (blue>220)
                {
                    blue=210;
                }

                // Darken blue color to increase sepia effect
                // normalize if out of bounds
                if (blue<30)
                {
                    blue=10;
                }
               

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
        redoList.add(deepCopy(bi)); // add the image to the redo list
    }

    /**
     * Borrowed from: http://www.jhlabs.com/ip/blurring.html
     * Blurs the image
     * @param bi - the BufferedImage to be blurred     
     */

    public static void blur (BufferedImage bi)
    {
        imageList.add(deepCopy(bi));  // add the image to the undo list

        float[] matrix = {
                0.111f, 0.111f, 0.111f, 
                0.111f, 0.111f, 0.111f, 
                0.111f, 0.111f, 0.111f, 
            };

        BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, matrix) );
        BufferedImage sourceImage = deepCopy(bi);
        op.filter(sourceImage, bi);
        redoList.add(deepCopy(bi)); // add the image to the redo list
    }

    /**
     * Borrowed from: http://www.java2s.com/Code/Java/2D-Graphics-GUI/ImageEffectSharpenblur.htm
     * Sharpens the image
     * @param bi - the BufferedImage to be sharpened     
     */

    public static void sharpen(BufferedImage bi) 
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        float[] sharpKernel = {
                0.0f, -1.0f, 0.0f,
                -1.0f, 5.0f, -1.0f,
                0.0f, -1.0f, 0.0f
            };
        Kernel kernel = new Kernel(3, 3, sharpKernel);
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
                null);
        BufferedImage sourceImage = deepCopy(bi);
        convolve.filter(sourceImage, bi);
        redoList.add(deepCopy(bi)); // add the image to the redo list

    }

    /**
     * Makes the light parts of the image darker and the dark parts lighter
     * @param bi - the BufferedImage to be inversed
     */

    public static void negative (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make light areas dark, and dark areas light
                blue = 255-blue;
                red = 255-red;
                green = 255-green;

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
        redoList.add(deepCopy(bi)); // add the image to the redo list
    }

    /**
     * Brightens the image 
     * @param bi - the BufferedImage to be changed    
     */

    public static void brighten (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // Make the image brighter by adding all colours
                if (red  < 215)
                {
                    red+=10;
                }
                if (green  < 215)
                {
                    green+=10;
                }
                if (blue  < 215)
                {
                    blue+=10;
                }

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
        redoList.add(deepCopy(bi)); // add the image to the redo list
    }

    /**
     * Darkens the image
     * @param bi - the BufferedImage to be darkened   
     */

    public static void darken (BufferedImage bi)
    {
        imageList.add(deepCopy(bi)); // add the image to the undo list
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic darker by subtracting all colours

                if (blue > 30)               
                {
                    blue -= 10;
                }
                if (red > 30)
                {
                    red -= 10;
                }
                if (green > 30)
                {
                    green -= 10;
                }

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
        redoList.add(deepCopy(bi)); // add the image to the redo list
    }

    /**
     * Returns the last edited image
     * @param bi - current BufferedImage
     * @return BufferedImage - the last edited BufferedImage
     */

    public static BufferedImage undo (BufferedImage bi)
    {
        undo = true;
        undoCount++;
        BufferedImage oldImage = bi;
        redoList.add(bi);
        JOptionPane messageBox = new JOptionPane();
        try
        {
            oldImage =  imageList.get(imageList.size()-1); // the last image
            imageList.remove(imageList.size()-1);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {

            // if no more images to undo
            messageBox.showMessageDialog(null,"No more changes to undo!");
        }
        return oldImage;

    }

    /**
     * Displays the image that was edited before this image, but was undoed
     * @param bi - current BufferedImage
     * @return BufferedImage - the BufferedImage with the next latest edit
     */
    public static BufferedImage redo (BufferedImage bi)
    {
        BufferedImage oldImage = bi;
        if (undo)
        {
            imageList.add(bi);
            JOptionPane messageBox = new JOptionPane();
            try
            {
                oldImage =  redoList.get(redoList.size()-1); // the last in the list
                redoList.remove(redoList.size()-1); 
                undoCount --;
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
               // if no more images to redo              
            }
        }
        if (undoCount <=0)
            undo = false;
        return oldImage;
    }

    /**
     * Creates a copy of the current image so that it would not be passed by reference
     * @param bi - current BufferedImage
     * @return BufferedImage - the copied image
     */

    public static BufferedImage deepCopy(BufferedImage bi) 
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultip = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultip, null);
    }

    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }
}
