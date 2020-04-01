/*
 ********************************************************************************
 ** Copyright (C) 2012 Donald J. Bartley <djbcoffee@gmail.com>
 **
 ** This source file may be used and distributed without restriction provided
 ** that this copyright statement is not removed from the file and that any
 ** derivative work contains the original copyright notice and the associated
 ** disclaimer.
 **
 ** This source file is free software; you can redistribute it and/or modify it
 ** under the terms of the GNU General Public License as published by the Free
 ** Software Foundation; either version 2 of the License, or (at your option) any
 ** later version.
 **
 ** This source file is distributed in the hope that it will be useful, but
 ** WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 ** FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 ** more details.
 **
 ** You should have received a copy of the GNU General Public License along with
 ** this source file.  If not, see <http://www.gnu.org/licenses/> or write to the
 ** Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 ** 02110-1301, USA.
 ********************************************************************************
 ** DJB 03/17/2012 Created.
 ********************************************************************************
 */

package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class that holds static methods and constants used to access the graphic
 * images for this game.  Handles loading the images from the files, merging them
 * together to make specific game tiles, and storing the finished game tile
 * graphics in a array of buffered images for access through the Tile Objects.
 * 
 * @author Donald J Bartley
 * @version 1.0
 */
public class Images {
    /** The value that indicates small tile size. */
    public static final int SMALL_TILE = 0;
    /** The value that indicates medium tile size. */
    public static final int MEDIUM_TILE = 1;
    /** The value that indicates large tile size. */
    public static final int LARGE_TILE = 2;
    
    /**
     * The value that indicates the explosion foreground image over the stone
     * background image tile.
     */
    public static final int EXPLOSION = 0;
    /**
     * The value that indicates the goblin foreground image over the stone
     * background image tile.
     */
    public static final int GOBLIN = 1;
    /**
     * The value that indicates the happy face foreground image over the stone
     * background image tile.
     */
    public static final int HAPPY_FACE = 2;
    /** 
     * The value that indicates the shocked face foreground image over the stone
     * background image tile.
     */
    public static final int SHOCKED_FACE = 3;
    /** 
     * The value that indicates the shrub foreground image over the stone
     * background image tile.
     */
    public static final int SHRUB = 4;
    /** The value that indicates the stone background image tile. */
    public static final int STONE = 5;
    /** 
     * The value that indicates the wall foreground image over the stone
     * background image tile.
     */
    public static final int WALL = 6;
    
    /** An array of file names for the images. */
    private static String[][] fileNames = new String[3][7];
    /** 
     * An array of finished buffered images that the Tile Objects can use to
     * access tile images.
     */
    private static BufferedImage[][] images = new BufferedImage[3][7];

    /**
     * Static method called from the controller in the game MVC architecture that
     * constructs all the game tile images.
     */
    public static void buildGameImages() {
        //Load all the file names in the array.
        fileNames[0][0] = "images/Explosion_16x16.png";
        fileNames[0][1] = "images/Goblin_16x16.png";
        fileNames[0][2] = "images/Happy_Face_16x16.png";
        fileNames[0][3] = "images/Shocked_Face_16x16.png";
        fileNames[0][4] = "images/Shrub_16x16.png";
        fileNames[0][5] = "images/Stone_16x16.png";
        fileNames[0][6] = "images/Wall_16x16.png";
        
        fileNames[1][0] = "images/Explosion_24x24.png";
        fileNames[1][1] = "images/Goblin_24x24.png";
        fileNames[1][2] = "images/Happy_Face_24x24.png";
        fileNames[1][3] = "images/Shocked_Face_24x24.png";
        fileNames[1][4] = "images/Shrub_24x24.png";
        fileNames[1][5] = "images/Stone_24x24.png";
        fileNames[1][6] = "images/Wall_24x24.png";
        
        fileNames[2][0] = "images/Explosion_32x32.png";
        fileNames[2][1] = "images/Goblin_32x32.png";
        fileNames[2][2] = "images/Happy_Face_32x32.png";
        fileNames[2][3] = "images/Shocked_Face_32x32.png";
        fileNames[2][4] = "images/Shrub_32x32.png";
        fileNames[2][5] = "images/Stone_32x32.png";
        fileNames[2][6] = "images/Wall_32x32.png";
        
        //Declare two buffered images that will hold the foreground and
        //background images.
        BufferedImage backgroundImage = null;
        BufferedImage foregroundImage = null;
        
        //Loop through all three image sizes and seven images for each size.
        for(int size = 0; size < 3; size++) {
            for(int image = 0; image < 7; image++) {
                //Load the background image, which is always stone, and the
                //current foreground image.
                try {
                    backgroundImage = ImageIO.read(ClassLoader.getSystemResourceAsStream(fileNames[size][STONE]));
                    foregroundImage = ImageIO.read(ClassLoader.getSystemResourceAsStream(fileNames[size][image]));
                } catch (IOException e) {
                    System.out.println(e.getStackTrace());
                }

                //Declare a buffered image that will hold the combined image of
                //the foreground image over the background image.  Then, based on
                //the current tile size being created, instantiate the buffered
                //image to that tile size using an RGB buffered image.
                BufferedImage combinedImage;
                switch(size) {
                    case 0:
                        combinedImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
                        break;
                    case 1:
                        combinedImage = new BufferedImage(24, 24, BufferedImage.TYPE_INT_RGB);
                        break;
                    default:
                        combinedImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
                        break;
                }

                //Create a graphics object that will be used for drawing the
                //combined image.
                Graphics g = combinedImage.createGraphics();
                
                //Draw the background image first on the combined image and then
                //the foreground image.
                g.drawImage(backgroundImage, 0, 0, null);
                g.drawImage(foregroundImage, 0, 0, null);
                
                //Dispose the graphic object.
                g.dispose();
                
                //Save the new combined tile in the image array.
                images[size][image] = combinedImage;
            }            
        }
    }

    /**
     * Gets a tile image from the image array.
     * 
     * @param size An integer indicating the tile size based on the values of the
     * static constants of this class.
     * @param image An integer indicating the tile image based on the values of
     * the static constants of this class.
     * @return The tile image.
     */
    public static BufferedImage getImage(int size, int image) {
        //Make sure the values passed fit within the rage for both size and
        //image.
        if (size < 3 && image < 7) {
            //The values passed are good, return the proper image from the array.
            return images[size][image];
        } else {
            //One, or both, values were not correct; return a null.
            return null;
        }
    }
}