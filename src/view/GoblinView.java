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

package view;

import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import model.GoblinModel;

/**
 * The view in the game MVC architecture.  This class is responsible for
 * displaying the game playing field and intercepting the user commands for the
 * keyboard and mouse.
 * 
 * @author Donald J Bartley
 * @version 1.0
 */
public class GoblinView extends Frame {
    /** The text string for the start game menu item. */
    public static final String START_GAME_MENU_ITEM = "Start";
    /** The text string for the exit game menu item. */
    public static final String EXIT_GAME_MENU_ITEM = "Exit";
    /** The text string for the small tiles menu item. */
    public static final String SMALL_TILES_MENU_ITEM = "Small Tiles (" + GoblinModel.SMALL_TILE_SIZE + " X " + GoblinModel.SMALL_TILE_SIZE + ")";
    /** The text string for the medium tiles menu item. */
    public static final String MEDIUM_TILES_MENU_ITEM = "Medium Tiles (" + GoblinModel.MEDIUM_TILE_SIZE + " X " + GoblinModel.MEDIUM_TILE_SIZE + ")";
    /** The text string for the large tiles menu item. */
    public static final String LARGE_TILES_MENU_ITEM = "Large Tiles (" + GoblinModel.LARGE_TILE_SIZE + " X " + GoblinModel.LARGE_TILE_SIZE + ")";
    /** The text string for the small game grid menu item. */
    public static final String SMALL_GRID_MENU_ITEM = "Small Grid (" + GoblinModel.SMALL_GRID_SIZE + " X " + GoblinModel.SMALL_GRID_SIZE + ")";
    /** The text string for the medium game grid menu item. */
    public static final String MEDIUM_GRID_MENU_ITEM = "Medium Grid (" + GoblinModel.MEDIUM_GRID_SIZE + " X " + GoblinModel.MEDIUM_GRID_SIZE + ")";
    /** The text string for the large game grid menu item. */
    public static final String LARGE_GRID_MENU_ITEM = "Large Grid (" + GoblinModel.LARGE_GRID_SIZE + " X " + GoblinModel.LARGE_GRID_SIZE + ")";
    
    /** Holds a boolean value that indicates if the game grid of the model is
     * ready to be drawn.
     */
    private boolean isReady = false;
    /** Holds a boolean value that indicates if the window size has been set to
     * the current state of the model.
     */
    private boolean sizeSet = false;
    /** Holds the number of rows in the model game grid. */
    private int numberOfRows;
    /** Holds the number of columns in the model game grid. */
    private int numberOfColumns;
    /** Holds the tile size used by the model. */
    private int tileSize;    
    /** Menu bar. */
    private MenuBar gameMenuBar;
    /** Game pull-down menu. */
    private Menu gameMenu;
    /** Options pull-down menu. */
    private Menu optionMenu;
    /** Start menu item in the Game pull-down menu. */
    private MenuItem startGame;
    /** Exit menu item in the Game pull-down menu. */
    private MenuItem exitGame;
    /** Small tile size checkable menu item in the Options pull-down menu. */
    private CheckboxMenuItem smallTile;
    /** Medium tile size checkable menu item in the Options pull-down menu. */
    private CheckboxMenuItem mediumTile;
    /** Large tile size checkable menu item in the Options pull-down menu. */
    private CheckboxMenuItem largeTile;
    /** Small grid size checkable menu item in the Options pull-down menu. */
    private CheckboxMenuItem smallGrid;
    /** Small grid size checkable menu item in the Options pull-down menu. */
    private CheckboxMenuItem mediumGrid;
    /** Medium grid size checkable menu item in the Options pull-down menu. */
    private CheckboxMenuItem largeGrid;
    /** Holds an instance of the model in the game MVC architecture. */
    private GoblinModel model;
    
    /**
     * Sole constructor.
     * 
     * @param model An instance of the model in the game MVC architecture.
     */
    public GoblinView(GoblinModel model) {
        //Call the constructor of the frame and place Goblin in the title bar.
        super("Goblin");
        
        //Set an initial size of 100,100 just to get things going.  The size will
        //be reset to the needed size once the model has the initial game grid
        //ready to be drawn.
        setSize(100, 100);
        
        //Set the background color to black and make it so the window can not be
        //resized by the user.
        setBackground(Color.black);
        setResizable(false);

        //Instantiate the menu bar and place it on the frame.
        gameMenuBar = new MenuBar();
        setMenuBar(gameMenuBar);
        
        //Instantiate both the Game and Options pull-down menus and place them on
        //the menu bar.
        gameMenu = new Menu("Game");
        optionMenu = new Menu("Options");
        gameMenuBar.add(gameMenu);
        gameMenuBar.add(optionMenu);
        
        //Instatiate the Start and Exit menu items and place them in the Game
        //pull-down menu item.
        startGame = new MenuItem(START_GAME_MENU_ITEM);
        exitGame = new MenuItem(EXIT_GAME_MENU_ITEM);
        gameMenu.add(startGame);
        gameMenu.add(exitGame);

        //Instatiate all six of the games options menu items and place them in
        //the Options pull-down menu.  Place a separator between the tile size
        //and grid size items.
        smallTile = new CheckboxMenuItem(SMALL_TILES_MENU_ITEM);
        mediumTile = new CheckboxMenuItem(MEDIUM_TILES_MENU_ITEM);
        largeTile = new CheckboxMenuItem(LARGE_TILES_MENU_ITEM);
        smallGrid = new CheckboxMenuItem(SMALL_GRID_MENU_ITEM);
        mediumGrid = new CheckboxMenuItem(MEDIUM_GRID_MENU_ITEM);
        largeGrid = new CheckboxMenuItem(LARGE_GRID_MENU_ITEM);
        optionMenu.add(smallTile);
        optionMenu.add(mediumTile);
        optionMenu.add(largeTile);
        optionMenu.addSeparator();
        optionMenu.add(smallGrid);
        optionMenu.add(mediumGrid);
        optionMenu.add(largeGrid);

        //Make the frame visible.
        setVisible(true);
        
        //Place the instance of the model into the class instance variable.
        this.model = model;
        
        //Call the methods that are responsible for checking the options that are
        //currently selected in the model.
        setOptionsMenuTileSizeItem();
        setOptionsMenuGridSizeItem();
    }
    
    /**
     * Method used to make sure that the current tile size of the model is
     * checked in the Options pull-down menu and the other tile size options are
     * un-checked.
     */
    public final void setOptionsMenuTileSizeItem() {
        //Set them all unchecked (false) to begin with.
        smallTile.setState(false);
        mediumTile.setState(false);
        largeTile.setState(false);
        
        //Based on the model tile size set one to checked (true).
        if(model.getTileSize() == GoblinModel.SMALL_TILE_SIZE) {
            smallTile.setState(true);
        } else if(model.getTileSize() == GoblinModel.MEDIUM_TILE_SIZE) {
            mediumTile.setState(true);
        } else {
            largeTile.setState(true);
        }
    }
    
    /**
     * Method used to make sure that the current grid size of the model is
     * checked in the Options pull-down menu and the other grid size options are
     * un-checked.
     */
    public final void setOptionsMenuGridSizeItem() {
        //Set them all unchecked (false) to begin with.
        smallGrid.setState(false);
        mediumGrid.setState(false);
        largeGrid.setState(false);
        
        //Based on the model grid size set one to checked (true).
        if(model.getNumberOfRows() == GoblinModel.SMALL_GRID_SIZE) {
            smallGrid.setState(true);
        } else if(model.getNumberOfRows() == GoblinModel.MEDIUM_GRID_SIZE) {
            mediumGrid.setState(true);
        } else {
            largeGrid.setState(true);
        }
    }
    
    /**
     * Method used to update the window title bar with information for the user.
     * The game name, score, level, and game over are all displayed in the title
     * bar.
     */
    public void updateTitle() {
        //If the state of the model is game over than print the game name, score,
        //level and game over.  Otherwise, print all of the information except
        //game over.
        if(model.getCurrentState() == GoblinModel.STATE_GAME_OVER) {
            this.setTitle("Goblin -- Score: " + model.getScore() + "  Level: " + model.getLevel() + " -- GAME OVER");
        } else {
            this.setTitle("Goblin -- Score: " + model.getScore() + "  Level: " + model.getLevel());
        }
    }
    
    /**
     * Method that calculates a specific area of the screen to be redrawn.  It
     * calculates a rectangular area of the game grid and passes that data off to
     * the repaint() method.  The data for which tiles have changed is retrieved
     * from the model.
     */
    public void redrawArea() {
        //Declarations.
        int currentTileX, currentTileY, lastTileX, lastTileY, windowStartX, windowStartY, x, y, width, height;
        
        //There are only two tiles that need to be redrawn.  The last one used
        //and the current one being used.
        currentTileX = model.getcurrentGoblinX();
        currentTileY = model.getcurrentGoblinY();
        lastTileX = model.getLastGoblinX();
        lastTileY = model.getLastGoblinY();
        
        //The repaint() method needs an x,y coordinate for each; the width and
        //height of each tile is already known.  Get the upper left-hand corner
        //pixel coordinate of the window.
        windowStartX = this.getInsets().left;
        windowStartY = this.getInsets().top;
        
        //Get the current number of rows and columns, along with the current tile
        //size, from the model.
        numberOfRows = model.getNumberOfRows();
        numberOfColumns = model.getNumberOfColumns();
        tileSize = model.getTileSize();
        
        //A rectangle needs to be found that will encompase both tiles to be
        //drawn.  The current Y position will always be the top of the rectangle
        //accept for one condition where the last Y was at position zero (0).
        if(lastTileY != 0) {
            //The last Y position was not zero (0).  Get the top of the rectangle
            //from the current Y position by taking the tile number, multiplying
            //it by the tile size, and then adding the inset.  Then set the
            //height to two (2) tiles.
            y = currentTileY * tileSize + windowStartY;
            height = tileSize * 2;
        } else {
            //The last Y position was zero (0) which means the entire length of Y
            //needs to be redrawn.  Set the top of the recetangle equal to the
            //inset and then set the height to the tile size multiplied by the
            //number of rows.
            y = windowStartY;
            height = tileSize * numberOfRows;
        }
        
        //There are three posibilities:
        //1.  The current X position is a lower number then the last position in
        //    which case the current X position will be used for the left-side of
        //    the rectangle and the width will be set to the tile size multiplied
        //    by two (2).
        //2.  The last X position is a lower number then the current position in
        //    which case the last X position will be used for the left-side of
        //    the rectangle and the width will be set to the tile size multiplied
        //    by two (2).
        //3.  The current X position is equal to the last X position in which
        //    case either can be used for the left-side of the rectangle and the
        //    width will be set to the tile size.
        if(currentTileX < lastTileX) {
            x = currentTileX * tileSize + windowStartX;
            width = tileSize * 2;
        } else if(lastTileX < currentTileX) {
            x = lastTileX * tileSize + windowStartX;
            width = tileSize * 2;
        } else {
            x = currentTileX * tileSize + windowStartX;
            width = tileSize;
        }

        //Repaint with the specified rectangle.
        repaint(x, y, width, height);
    }

    /**
     * Overrides the paint() method of the frame.  Calls the update() method.
     * 
     * @param g Graphics container.
     */
    @Override
    public void paint(Graphics g) {
        update(g);
    }

    /**
     * Overrides the update() method of Container.  Collects tile and game grid
     * information from the model and draws it on the screen.  Both full redraws
     * and specific rectangular area redraws are handled in this method.
     * 
     * @param g Graphics container.
     */
    @Override
    public void update(Graphics g) {
        //Declarations.
        int windowStartX, windowStartY, startingRow, startingCol, endingRow, endingCol;
        
        //Get the rectangular area to be drawn.
        Rectangle drawArea = g.getClipBounds();
        
        //Get the upper left-hand corner of the drawing area under the menu bar
        //and inside the left window frame border.
        windowStartX = this.getInsets().left;
        windowStartY = this.getInsets().top;
        
        //Get the current number of rows and columns, along with the current tile
        //size, of the model.
        numberOfRows = model.getNumberOfRows();
        numberOfColumns = model.getNumberOfColumns();
        tileSize = model.getTileSize();

        //If the model is ready to be drawn then draw it.
        if(isReady) {
            //Resize the frame if it has not already been sized.
            if(!sizeSet) {
                setSize(getInsets().left + numberOfColumns * tileSize + getInsets().right, getInsets().top + numberOfRows * tileSize + getInsets().bottom);
                sizeSet = true;
            }
            
            //If the area to be drawn is outside of the game grid area then draw
            //the whole screen.  Otherwise just draw the specific area.
            if(drawArea.x < windowStartX && drawArea.y < windowStartY) {
                //It is outside the game grid area.  Draw the whole screen by
                //looping through all rows and columns of the model.
                for(int row = 0; row < numberOfRows; row++) {
                    for(int col = 0; col < numberOfColumns; col++) {
                        g.drawImage(model.getTile(row, col), col * tileSize + windowStartX, row * tileSize + windowStartY, this);
                    }
                }
            } else {
                //Draw only the specific area dictated by draw area.  Calculate
                //the starting row and column, and the ending row and column that
                //encompasses the rectangular area to be drawn.
                startingRow = (drawArea.y - windowStartY) / tileSize;
                startingCol = (drawArea.x - windowStartX) / tileSize;
                endingRow = (drawArea.y - windowStartY + (drawArea.height - 1)) / tileSize;
                endingCol = (drawArea.x - windowStartX + (drawArea.width - 1)) / tileSize;
            
                //Loop through the starting and ending rows and columns.
                for(int row = startingRow; row <= endingRow; row++) {
                    for(int col = startingCol; col <= endingCol; col++) {
                        g.drawImage(model.getTile(row, col), col * tileSize + windowStartX, row * tileSize + windowStartY, this);
                    }
                }
            }
        }
    }
    
    /**
     * Method that passes key presses from the keyboard listener to the
     * controller in the game MVC architecture.
     * 
     * @param kl Instance of a Class in the controller that handles the keyboard
     * listener.
     */
    public void addKeyboardListener(KeyListener kl) {
        this.addKeyListener(kl);
    }
    
    /**
     * Method that passes window events from the window listener to the
     * controller in the game MVC architecture.
     * 
     * @param wl Instance of a Class in the controller that handles the window
     * listener.
     */
    public void addGameWindowListener(WindowListener wl) {
        this.addWindowListener(wl);
    }
    
    /**
     * Method that passes menu item selections from the action listener to the
     * controller in the game MVC architecture.
     * 
     * @param al Instance of the Class in the controller that handles the action
     * listener.
     */
    public void addGameMenuListener(ActionListener al) {
        startGame.addActionListener(al);
        exitGame.addActionListener(al);
    }
    
    /**
     * Method that passes checkable menu item selections from the item listener
     * to the controller in the game MVC architecture.
     * 
     * @param il Instance of the Class in the controller that handles the item
     * listener.
     */
    public void addOptionsMenuListener(ItemListener il) {
        smallTile.addItemListener(il);
        mediumTile.addItemListener(il);
        largeTile.addItemListener(il);
        smallGrid.addItemListener(il);
        mediumGrid.addItemListener(il);
        largeGrid.addItemListener(il);
    }
    
    /**
     * Sets the instance variable that is used to indicate if the game grid from
     * the model is ready to be drawn.
     * 
     * @param isReady True if the game grid in the model is ready to be drawn,
     * false if not.
     */
    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }
    
    /**
     * Sets the instance variable that is used to indicate if the window has been
     * sized to fit the game grid from the model.
     * 
     * @param sizeSet True if the window size has been set, false if not.
     */
    public void setSizeSet(boolean sizeSet) {
        this.sizeSet = sizeSet;
    }
}