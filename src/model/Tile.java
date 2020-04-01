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

import java.awt.image.BufferedImage;

/**
 * Class used to hold information about a game tile.
 * 
 * @author Donald J Bartley
 * @version 1.0
 */
public class Tile {
    /** Holds information about the image type for this tile. */
    private int imageType;
    /** Holds information about the image size for this tile. */
    private int imageSize;
    
    /**
     * Gets the image type for this tile.
     * 
     * @return An integer indicating the image type which corresponds to
     * constants set in the Images Class.
     */
    public int getImageType() {
        return imageType;
    }

    /**
     * Sets the image type and image size for the tile.
     * @param imageSize An integer indicating the image size which corresponds to
     * constants set in the Image Class.
     * @param imageType An integer indicating the image type which corresponds to
     * constants set in the Image Class.
     */
    public void setImageType(int imageSize, int imageType) {
        this.imageSize = imageSize;
        this.imageType = imageType;
    }
    
    /**
     * Gets the image for the tile.
     * 
     * @return Graphic image for the tile.
     */
    public BufferedImage getImage() {
        return Images.getImage(imageSize, imageType);
    }
}