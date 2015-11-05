import greenfoot.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 * A generic drop down list to avoid multiple buttons on the screen
 * 
 * @author Bhavya Shah
 * @version April 2015
 */
public class DropDownList extends Actor
{
    // constants
    private final int FONTSIZE = 20;

    // other variables
    private ArrayList<DropDownListItem> listOfItems = new ArrayList<DropDownListItem>();
    private String selected = "";
    private boolean isExpanded = false;

    /**
     * Constructs a drop-down list with a given list of items
     * @param items - An ArrayList with the items to put in the drop list
     * @param defaultValue - the value from the  arraylist, that you want as the title of the list
     */

    public DropDownList(ArrayList<String> items, int defaultValue)
    {
        ArrayList<GreenfootImage> images = new ArrayList<GreenfootImage>();

        // Creates the text images and finds their max height and max width.
        int maxWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < items.size(); i++) 
        {
            GreenfootImage image = new GreenfootImage(items.get(i), FONTSIZE, Color.BLACK, new Color(0,0, 0, 0));
            if (maxWidth < image.getWidth())
            {
                maxWidth = image.getWidth();
            }
            if (maxHeight < image.getHeight()) 
            {
                maxHeight = image.getHeight();
            }
            images.add(image);
        }

        // Creates the images of the list items based on their text images.
        for (int i = 0; i < images.size(); i++)
        {
            GreenfootImage newImage = new GreenfootImage(maxWidth + 10, maxHeight + 10);
            newImage.setColor(Color.WHITE);
            newImage.fill();
            newImage.drawImage(images.get(i), 4, 4);
            newImage.setColor(Color.BLACK);
            newImage.drawRect(0, 0, newImage.getWidth() - 1, newImage.getHeight() - 1);
            images.set(i, newImage);
        }

        // Creates the list items.
        listOfItems = new ArrayList<DropDownListItem>();
        for (int i = 1; i < images.size(); i++)
        {
            DropDownListItem item = new DropDownListItem(this,images.get(i),items.get(i),maxWidth,maxHeight);
            listOfItems.add(item);
        }

        // Sets the current value to the default item.
        setImage(images.get(defaultValue));       
        isExpanded = false;     

    }

    /**
     * Things to do for each act. 
     */
    public void act() 
    {
        // Checks if to expand or collapse the drop-down list.
        checkExpand();
    }

    /**
     * Expands the drop-down list. 
     */
    public void expandList() 
    {
        for (int i = 0; i < listOfItems.size(); i++) 
        {
            getWorld().addObject(listOfItems.get(i), getX(), getY() + (i + 1) * getImage().getHeight());
        }
        isExpanded = true;
    }

    /** 
     * Collapses the drop-down list.
     */
    public void collapseList() 
    {
        getWorld().removeObjects(listOfItems);
        isExpanded = false;
    }

    /**
     * Sets the currently-selected item of the list.
     * 
     * @param value - the name of the currently-selected DropListItem.
     */
    public void setSelected(String value) 
    {
        selected = value;        
    }

    /**
     * Returns the currently selected item.
     * 
     * @return String - the name of the selected item
     */
    public String getSelected() 
    {
        String retVal = new String (selected);
        selected = "";
        return retVal;
    }

    /**
     * Checks if to expand the drop-down list. 
     */
    private void checkExpand() 
    {
        if (Greenfoot.mouseClicked(this)) // if clicked
        {
            if (!isExpanded) 
            {
                expandList();
            } else 
            {
                collapseList();
            }
        }
        if (Greenfoot.mouseClicked(getWorld()))  // if clicked anywhere else

        {
            if (isExpanded)
            {
                collapseList();
            }
        }

    }    
}
