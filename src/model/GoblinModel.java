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
import java.util.ArrayList;

/**
 * The model in the game MVC architecture.  This class is responsible for
 * managing the state of the game, running the game logic, and maintaining the
 * game grid.
 * 
 * @author Donald J Bartley
 * @version 1.0
 */
public class GoblinModel {
    /** The number of base wall obstacles in a small game grid. */
    private static final int SMALL_GRID_BASE_NUMBER_OF_WALLS = 17;
    /** The number of base wall obstacles in a medium game grid. */
    private static final int MEDIUM_GRID_BASE_NUMBER_OF_WALLS = 38;
    /** The number of base wall obstacles in a large game grid. */
    private static final int LARGE_GRID_BASE_NUMBER_OF_WALLS = 67;
    /** The number of additional wall obstacles per level in a small game
     * grid.
     */
    private static final int SMALL_GRID_ADDITIONAL_WALLS_PER_LEVEL = 3;
    /** The number of additional wall obstacles per level in a medium game
     * grid.
     */
    private static final int MEDIUM_GRID_ADDITIONAL_WALLS_PER_LEVEL = 7;
    /** The number of additional wall obstacles per level in a large game
     * grid.
     */
    private static final int LARGE_GRID_ADDITIONAL_WALLS_PER_LEVEL = 13;
    
    /** The value of the small tile size in pixels. */
    public static final int SMALL_TILE_SIZE = 16;
    /** The value of the medium tile size in pixels. */
    public static final int MEDIUM_TILE_SIZE = 24;
    /** The value of the large tile size in pixels. */
    public static final int LARGE_TILE_SIZE = 32;
    /** 
     * The value of the number of horizontal and vertical tiles in a small game
     * grid.
     */
    public static final int SMALL_GRID_SIZE = 20;
    /** 
     * The value of the number of horizontal and vertical tiles in a medium game
     * grid.
     */
    public static final int MEDIUM_GRID_SIZE = 30;
    /**
     * The value of the number of horizontal and vertical tiles in a large game
     * grid.
     */
    public static final int LARGE_GRID_SIZE = 40;
    
    /**
     * The value that represents that the model is in a state that represents it
     * is ready to build a level.
     */
    public static final int STATE_BUILD_LEVEL = 0;
    /**
     * The value that represents that the model is in a state that represents
     * that the current level is built and it is ready to begin.
     */
    public static final int STATE_BEGIN_LEVEL = 1;
    /** 
     * The value that represents that the model is in a state the represents that
     * the current level is running.
     */
    public static final int STATE_LEVEL_RUNNING = 2;
    /** 
     * The value that represents that the model is in a state that represents the
     * goblin just got a shocked face. */
    public static final int STATE_GOBLIN_GOT_FACE = 3;
    /**
     * The value that represents that the model is in a state that represents
     * that the goblin just got destroyed.
     */
    public static final int STATE_GOBLIN_DESTROYED = 4;
    /**
     * The value that represents that the model is in a state that represents
     * that the current level was cleared.
     */
    public static final int STATE_LEVEL_CLEARED = 5;
    /**
     * The value that represents that the model is in a state that represents
     * that the game is over.
     */
    public static final int STATE_GAME_OVER = 6;
    
    /** The value that represents the 'A' key on the keyboard. */
    public static final int MOVE_LEFT = 65;
    /** The value that represents the 'L' key on the keyboard. */
    public static final int MOVE_RIGHT = 76;
    /** The value that represents the 'Enter' key on the keyboard. */
    public static final int NEW_GAME = 10;
    
    /** Holds the number of rows in the current game grid. */
    private int numberOfRows;
    /** Holds the number of columns in the current game grid. */
    private int numberOfColumns;
    /** Holds the current tile size. */
    private int tileSize;
    /** Holds the column number for the left-hand shrubs. */
    private int leftShrubColumn;
    /** Holds the column number for the right-hand shrubs. */
    private int rightShrubColumn;
    /** Holds the current level number. */
    private int level;
    /** Holds the current game score. */
    private int score;
    /** Holds the value that represents the current state of the model. */
    private int currentState;
    /** Holds the horizontal tile position that the goblin currently occupies. */
    private int currentGoblinX;
    /** Holds the vertical tile position that the goblin currently occupies. */
    private int currentGoblinY;
    /** Holds the horizontal tile position that the goblin occupied last. */
    private int lastGoblinX;
    /** Holds the vertical tile position that the goblin occupied last. */
    private int lastGoblinY;
    /** Holds the value that represents the current image size for the tiles. */
    private int imageSize;
    /** Holds the base/standard number of wall obstacles drawn on each level. */
    private int baseNumberOfWalls;
    /**
     * Holds the addition number of wall obstacles that are drawn in addition to
     * the base number of wall obstacles.  This number is cumulative and is
     * multiplied by the level number.
     */
    private int additionalNumberOfWallsPerLevel;
    /** Two-dimensional array that holds the game grid. */
    private Tile[][] tile;
    /** First-in first-out buffer that holds the key presses. */
    private ArrayList<Integer> keyPresses = new ArrayList<Integer>();
    
    //Constructor.
    /**
     * Sole constructor.
     */
    public GoblinModel() {
        //Start with a medium game grid and medium tile size.
        numberOfRows = MEDIUM_GRID_SIZE;
        numberOfColumns = MEDIUM_GRID_SIZE;
        tileSize = MEDIUM_TILE_SIZE;
        imageSize = Images.MEDIUM_TILE;
        baseNumberOfWalls = MEDIUM_GRID_BASE_NUMBER_OF_WALLS;
        additionalNumberOfWallsPerLevel = MEDIUM_GRID_ADDITIONAL_WALLS_PER_LEVEL;
        
        //Left shrubs are always in column zero.  Right shrubs are always on the
        //far right column which is the number of columns minus one.
        leftShrubColumn = 0;
        rightShrubColumn = numberOfColumns - 1;
        
        //Set level and score to zero.
        level = 0;
        score = 0;
   
        //Set the current state of the model to game over.
        currentState = STATE_GAME_OVER;
        
        //Instruct the Images class to build all the game tile images.
        Images.buildGameImages();
        
        //Instantiate the game grid array to the largest size possible.
        tile = new Tile[LARGE_GRID_SIZE][LARGE_GRID_SIZE];
        
        //Initilize each element of the game grid array with a Tile object.
        for(int row = 0; row < tile.length; row++) {
            for(int col = 0; col < tile[row].length; col++) {
		tile[row][col] = new Tile();
            }
        }
    }
    
    /**
     * Method used to build a blank game grid with just stone tiles on it so the
     * user can see what the game grid size will be like before starting a game.
     */
    public void buildGameGrid() {
        //Show a game grid that is comprised of stone tiles in the size of the
        //current game grid.
        for(int row = 0; row < numberOfRows; row++) {
            for(int col = 0; col < numberOfColumns; col++) {
                tile[row][col].setImageType(imageSize, Images.STONE);
            }
        }

        //This method is called when the game grid size changes.  Recalculate the
        //column number for the right shrubs.  They are always in the right-most
        //column which is the number of columns minus one.
        rightShrubColumn = numberOfColumns - 1;
    }
    
    /**
     * Sets the size of the game grid.
     * 
     * @param size Value of the horizontal and vertical number of game tiles.
     */
    public void setGameGridSize(int size) {
        if(size == SMALL_GRID_SIZE || size == MEDIUM_GRID_SIZE || size == LARGE_GRID_SIZE) {
            //Set the number of rows and columns.
            numberOfRows = size;
            numberOfColumns = size;
        
            //Set the number of base wall obstacles and additonal number of wall
            //obstacles per level based on the grid size.
            if(size == SMALL_GRID_SIZE) {
                baseNumberOfWalls = SMALL_GRID_BASE_NUMBER_OF_WALLS;
                additionalNumberOfWallsPerLevel = SMALL_GRID_ADDITIONAL_WALLS_PER_LEVEL;
            } else if(size == MEDIUM_GRID_SIZE) {
                baseNumberOfWalls = MEDIUM_GRID_BASE_NUMBER_OF_WALLS;
                additionalNumberOfWallsPerLevel = MEDIUM_GRID_ADDITIONAL_WALLS_PER_LEVEL;
            } else {
                baseNumberOfWalls = LARGE_GRID_BASE_NUMBER_OF_WALLS;
                additionalNumberOfWallsPerLevel = LARGE_GRID_ADDITIONAL_WALLS_PER_LEVEL;
            }            
        }
    }
    
    /**
     * Method that builds the game level.  Shrubs, wall obstacles, shocked faces,
     * and the goblin are all placed on the game grid.
     */
    public void buildLevel() {
        //Declarations.
        boolean goblinPlaced = false;
        int row, col, wallCount = 0, faceCount = 0;
        
        //Shrubs go down each side.
        for(row = 0; row < numberOfRows; row++) {
            tile[row][leftShrubColumn].setImageType(imageSize, Images.SHRUB);
            tile[row][rightShrubColumn].setImageType(imageSize, Images.SHRUB);
        }
        
        //Place blank stone tiles on the rest of the screen.
        for(row = 0; row < numberOfRows; row++) {
            for(col = leftShrubColumn + 1; col < rightShrubColumn; col++) {
                tile[row][col].setImageType(imageSize, Images.STONE);
            }
        }
        
        //Randomly place walls in the playing area not including the shrubs.
        //The rules are placement are:
        //1.  A wall can not be placed where a wall already exists.
        //2.  Two (2) walls in a row can not extend from either shrub column.
        //3.  There can not be three (3) walls in a row anywhere.
        while(wallCount < baseNumberOfWalls + additionalNumberOfWallsPerLevel * level) {
            //Get a random row number from zero (0) to number of rows.
            row = (int)(Math.random() * numberOfRows);
            
            //Get a random column number from one (1) to right shrub column minus
            //one (1).
            col = (int)(Math.random() * (rightShrubColumn - 1)) + 1;
            
            //Check the first rule.  If a wall already exists in this spot then
            //skip the rest of the loop and start over.
            if(tile[row][col].getImageType() == Images.WALL)
                continue;
            
            //The space is empty, check rule two.
            //If the wall to be placed is directly next to either shrub then
            //check the space directly next to the proposed wall placement
            //location.
            if(col == leftShrubColumn + 1) {
                //The proposed wall location is up against the left shrub.  Check
                //the space directly to the right of the proposed location in the
                //same row.  If a wall is found then skip the rest of the loop
                //and start over.
                if(tile[row][col + 1].getImageType() == Images.WALL)
                    continue;
            } else if(col == rightShrubColumn - 1){
                //The proposed wall location is up against the right shrubs.
                //Check the space directly to the left of the proposed location
                //in the same row.  If a wall is found then skip the rest of the
                //loop and start over.
                if(tile[row][col - 1].getImageType() == Images.WALL)
                    continue;
            } else if(col == leftShrubColumn + 2) {
                //The proposed wall location is the second column from the left
                //shrub.  Check the space directly next to the left shrub in the
                //same row.  If a wall is found then skip the rest of the loop
                //and start over.
                if(tile[row][leftShrubColumn + 1].getImageType() == Images.WALL)
                    continue;
            } else if(col == rightShrubColumn - 2) {
                //The proposed wall location is the second column from the right
                //shrub.  Check the space directly next to the right shrub in the
                //same row.  If a wall is found then skip the rest of the loop
                //and start over.
                if(tile[row][rightShrubColumn - 1].getImageType() == Images.WALL)
                    continue;
            }
            
            //Rule two passed, check the third and final rule.  It is broken up
            //into three (3) parts:
            //1.  The proposed wall location can be located to the left of two
            //    (2) existing walls.
            //2.  The proposed wall location can be located to the right of two
            //    (2) existing walls.
            //3.  The proposed wall location can be located in the middle of two
            //    (2) exising walls.
            //Since it has already been checked that two walls are not located
            //next to each other adjacent to any shrub we know that:
            //1.  To check if the proposed location is to the left of two
            //    existing walls it has to be in a column that is less than right
            //    shrub column minus three (3).
            //2.  To check if the proposed location is to the right of two
            //    existing walls it has to be in a column that is greater than
            //    left shrub column plus three (3).
            //3.  To check if the proposed location is in between two existing
            //    walls it has to be in a column that is less than right shrub
            //    column minus two (2) AND greater than left shrub column plus
            //    two (2).
            if(col < rightShrubColumn - 3) {
                //The proposed location could be located to the left of two (2)
                //exisiting walls.  Check in the same row and if two (2) walls
                //are found then skip the rest of the loop and start over.
                if(tile[row][col + 1].getImageType() == Images.WALL &&
                        tile[row][col + 2].getImageType() == Images.WALL)
                    continue;
            }
            
            if(col > leftShrubColumn + 3) {
                //The proposed location could be located to the right of two (2)
                //existing walls.  Check in the same row and if two (2) walls are
                //found then skip the rest of the loop and start over.
                if(tile[row][col - 1].getImageType() == Images.WALL &&
                        tile[row][col - 2].getImageType() == Images.WALL)
                    continue;
            }
            
            if(col < rightShrubColumn - 2 && col > leftShrubColumn + 2) {
                //The proposed location could be located in between two (2)
                //existing walls.  Check in the same row and if two (2) walls are
                //found then skip the rest of the loop and start over.
                if(tile[row][col + 1].getImageType() == Images.WALL &&
                        tile[row][col - 1].getImageType() == Images.WALL)
                    continue;
            }
            
            //The proposed location for the wall is acceptable.  Place it and
            //then increment the counter.
            tile[row][col].setImageType(imageSize, Images.WALL);
            wallCount++;
        }
        
        //Place ten (10) shocked faces randomly on the playing field.  The two
        //rules are:
        //1.  A shocked face cannot be placed where there is a wall.
        //2.  A shocked face cannot be placed where there is another shocked
        //    face.
        while(faceCount < 10) {
            //Get a random row number from zero (0) to number of rows.
            row = (int)(Math.random() * numberOfRows);
            
            //Get a random column number from one (1) to right shrub column minus
            //one (1).
            col = (int)(Math.random() * (rightShrubColumn - 1)) + 1;
            
            //Check if there is a wall at the proposed location.  If there is
            //then skip the rest of the loop and start over.  Otherwise, place
            //the face.
            if(tile[row][col].getImageType() == Images.WALL ||
                    tile[row][col].getImageType() == Images.SHOCKED_FACE)
                continue;
            else
                tile[row][col].setImageType(imageSize, Images.SHOCKED_FACE);
            
            //The face has been placed, increment the counter.
            faceCount++;
        }

        //Place the goblin at a random spot on the last row which is the number
        //of rows minus one (1).  The only rule is the goblin can not be placed
        //in the location where either a wall or shocked face exists.
        while(!goblinPlaced) {
            //Get a random column number from one (1) to right shrub column minus
            //one (1).
            col = (int)(Math.random() * (rightShrubColumn - 1)) + 1;
            
            //Check if the proposed location is already occupied.  If it is then
            //skip the rest of the loop and start over. Otherwise, place the
            //goblin.
            if(tile[numberOfRows - 1][col].getImageType() == Images.WALL ||
                    tile[numberOfRows - 1][col].getImageType() == Images.SHOCKED_FACE)
                continue;
            else
                tile[numberOfRows - 1][col].setImageType(imageSize, Images.GOBLIN);
            
            //Mark the goblin's location and indicate that it has been placed.
            currentGoblinX = col;
            currentGoblinY = numberOfRows - 1;
            goblinPlaced = true;
        }
        
        //Game board ready, change the state of the model.
        currentState = STATE_BEGIN_LEVEL;
        
        //Increase the level number.
        level++;
        
        //Clear the key press buffer.
        keyPresses.clear();
    }
    
    /**
     * Method that moves the goblin.  Checks if there are any key presses and
     * checks the tile the goblin is moving to for shrubs, wall obstacles, and
     * shocked faces.
     */
    public void moveGoblin() {
        //Store the current goblin location and change the tile to stone.
        lastGoblinX = currentGoblinX;
        lastGoblinY = currentGoblinY;
        tile[currentGoblinY][currentGoblinX].setImageType(imageSize, Images.STONE);
        
        //Automatically move the goblin up one.  If the end of the rows has been
        //reached, which is equal to zero (0), then restart the goblin at the
        //bottom of the screen which is number of rows minus 1.
        if(currentGoblinY == 0)
            currentGoblinY = numberOfRows - 1;
        else
            currentGoblinY--;
        
        //Now check if there was any left or right movement.
        if(!keyPresses.isEmpty()) {
            //There is at least one movement in the cue.  Adjust the X of the
            //goblin based on the movement direction.
            if(keyPresses.get(0) == MOVE_LEFT)
                currentGoblinX--;
            else
                currentGoblinX++;
            
            //Clear the key press from the buffer.
            keyPresses.remove(0);            
        }
        
        //Before placeing the goblin in the new location check for any shrub,
        //shocked faces, or walls.
        switch(tile[currentGoblinY][currentGoblinX].getImageType()) {
            case Images.SHRUB:
                //Shrub found, don't allow the goblin to move there by changing
                //the current X position to the last one.  Change the model state
                //to level running to indicate nothing of interest happened.
                currentGoblinX = lastGoblinX;
                tile[currentGoblinY][currentGoblinX].setImageType(imageSize, Images.GOBLIN);
                currentState = STATE_LEVEL_RUNNING;
                break;
            case Images.SHOCKED_FACE:
                //Shocked face found.  Increase the score and draw the goblin in
                //the new location.
                score++;
                tile[currentGoblinY][currentGoblinX].setImageType(imageSize, Images.GOBLIN);
                
                //The state will either be that the goblin got a face if there
                //are more faces left on the game grid, or level cleared if there
                //are no faces left.  Taking the score mod 10 will indicate if
                //there are faces left.  If 0 is returned then all the faces have
                //been gotten.
                currentState = (score % 10 == 0) ? STATE_LEVEL_CLEARED : STATE_GOBLIN_GOT_FACE;
                break;
            case Images.WALL:
                //Wall obstacle found.  Draw an explosion in the new location,
                //make all the remaining faces happy, and then change the state
                //of the model to goblin destroyed.
                tile[currentGoblinY][currentGoblinX].setImageType(imageSize, Images.EXPLOSION);
                makeAllFacesHappy();
                currentState = STATE_GOBLIN_DESTROYED;
                break;
            default:
                //Nothing found, move the goblin to the new location and change
                //the state to level running to indicate that nothing interesting
                //happened.
                tile[currentGoblinY][currentGoblinX].setImageType(imageSize, Images.GOBLIN);
                currentState = STATE_LEVEL_RUNNING;
                break;
        }
    }
    
    /**
     * Method that changes the state of the model to ready to build level.
     */
    public void changeStateToBuildLevel() {
        currentState = STATE_BUILD_LEVEL;
    }
    
    /**
     * Method that scans the entire game grid for shocked faces and if found
     * changes them to happy faces.
     */
    public void makeAllFacesHappy() {
        //Go through each tile and if a shocked face is found change it to a
        //happy face tile.
        for(int row = 0; row < numberOfRows; row++) {
            for(int col = 0; col < numberOfColumns; col++) {
                if(tile[row][col].getImageType() == Images.SHOCKED_FACE)
                    tile[row][col].setImageType(imageSize, Images.HAPPY_FACE);
            }
        }
    }
    
    /**
     * Method that clears the explosion left by the goblin destruction off of the
     * screen.
     */
    public void clearExplosion() {
        //The explosion will be in the current goblin X,Y coordinate.  Clear it
        //to a stone tile and change the state of the model to game over.
        tile[currentGoblinY][currentGoblinX].setImageType(imageSize, Images.STONE);
        currentState = STATE_GAME_OVER;
    }
    
    /**
     * Method that resets the model back to a state where it is ready to start a
     * new game.
     */
    public void reset() {
        //Set score and level to zero, and change state to ready to build level.
        score = 0;
        level = 0;
        currentState = STATE_BUILD_LEVEL;
    }
    
    /**
     * Gets the number of rows in the model.
     * 
     * @return Number of rows in the model.
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }
    
    /**
     * Gets the number of columns in the model.
     * 
     * @return Number of columns in the model.
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * Gets the current tile size used by the model.
     * 
     * @return Current tile size used by the model.
     */
    public int getTileSize() {
        return tileSize;
    }
    
    /**
     * Gets the current level the model is in.
     * 
     * @return Current level the model is in.
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Gets the current score of the player.
     * 
     * @return Current score of the player.
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Gets the current state of the model.
     * 
     * @return Current state of the model.
     */
    public int getCurrentState() {
        return currentState;
    }
    
    /**
     * Gets the current X (horizontal) position of the goblin.
     * 
     * @return Current X position of the goblin.
     */
    public int getcurrentGoblinX() {
        return currentGoblinX;
    }
    
    /**
     * Gets the current Y (vertical) position of the goblin.
     * 
     * @return Current Y position of the goblin.
     */
    public int getcurrentGoblinY() {
        return currentGoblinY;
    }
    
    /**
     * Gets the last X (horizontal) position of the goblin.
     * 
     * @return Last X position of the goblin.
     */
    public int getLastGoblinX() {
        return lastGoblinX;
    }
    
    /**
     * Gets the last Y (vertical) position of the goblin.
     * 
     * @return Last Y position of the goblin.
     */
    public int getLastGoblinY() {
        return lastGoblinY;
    }

    /**
     * Gets the tile image at the row and column position.
     * 
     * @param row The row of the tile image to get.
     * @param col The column of the tile image to get.
     * @return Image of the tile at the row and column position.
     */
    public BufferedImage getTile(int row, int col) {
        return tile[row][col].getImage();
    }
    
    /**
     * Sets the tile size of the game grid.
     * 
     * @param tileSize Value that represents the size of the tile in pixels.
     */
    public void setTileSize(int tileSize) {
        //If the tile size is one of the known types then make the change.
        if(tileSize == SMALL_TILE_SIZE || tileSize == MEDIUM_TILE_SIZE || tileSize == LARGE_TILE_SIZE) {
            //Set the new tile size.
            this.tileSize = tileSize;

            //Change the image size to match the new tile size.
            if(tileSize == SMALL_TILE_SIZE) {
                imageSize = Images.SMALL_TILE;
            } else if(tileSize == MEDIUM_TILE_SIZE) {
                imageSize = Images.MEDIUM_TILE;
            } else {
                imageSize = Images.LARGE_TILE;
            }           
        }
    }
    
    /**
     * Adds a new key press to the FIFO buffer.
     * 
     * @param keyPress Key press to be added to the FIFO buffer.
     */
    public void setKeyPress(int keyPress) {
        keyPresses.add(keyPress);
    }
}