import greenfoot.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
/**
 * Write a description of class DropDownListItem here.
 * 
 * @author Bhavya Shah
 * @version April 2015
 */
public class DropDownListItem extends Actor
{
    private DropDownList parentList = null;
    private int itemOrder = -1;
    private String name;
    private int maxHeight,maxWidth;
    private boolean isOn = false;
    /**
     * Creates a new drop-down list item.
     * 
     * @param parent the parent drop-down list
     * @param order the order of the item in the drop-down list
     * @param text the text of the item, as an image
     */
    public DropDownListItem(DropDownList parent,GreenfootImage text,String inName,int inMaxWidth, int inMaxHeight)
    {
        parentList = parent;
        name = inName;
        //itemOrder = order;
        setImage(text);
        maxHeight = inMaxHeight;
        maxWidth = inMaxWidth;
    }

    /**
     * Things to do for each act. 
     */
    public void act() {
        // Checks if this item is selected.
        checkSelected();
        Color buttonFill = new Color (255,255,255); 
        Color changeFill = new Color (0,102,204);
        if (Greenfoot.mouseMoved(null)) // has mouse moved at all
        {
            if (Greenfoot.mouseMoved(this) && !isOn) // if over this 
            {
                update (changeFill);
                isOn =true;
            }
            if (!Greenfoot.mouseMoved(this) && isOn) // if not over on this 
            {
                update(buttonFill);
                isOn = false;
            }
        }

      
    }

    /**
     * Checks if this item is selected.
     * 
     */
    private void checkSelected() 
    {
        if (Greenfoot.mouseClicked(this))
        {
            parentList.setSelected(name);
            parentList.collapseList();
        }
    }
    /**
     * Updates the background color of the object.
     * @param background - the color that the background will be changed too
     */

    public void update (Color background){
        GreenfootImage tempTextImage = new GreenfootImage (name, 20, Color.BLACK, new Color (0,0,0,0));
        GreenfootImage myImage;
        myImage = new GreenfootImage (maxWidth + 10, maxHeight+10);
        myImage.setColor (background);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,myImage.getWidth() - 1, myImage.getHeight() - 1);
        setImage(myImage);
    }

}
